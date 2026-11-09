package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;

/**
 * 极寒 - 生物实体染色着色器.
 * <p>
 * 结构与 {@link ShaderInkyEntity} 相同: 兼容剖面顶点 + 片元程序,
 * 顶点复刻固定管线, 片元按 贴图 * 顶点色 * 光照 采样后往淡蓝色混合.
 * <p>
 * 按实体本地高度做从下往上的渐变, 需要把模型空间 Y 传进片元着色器;
 * 在当前 1.12.2 实体渲染管线下冲突风险与成本都偏高, 因此改为整体发蓝.
 * <p>
 * 本类同样是过渡实现 (独立 program). 扩展更多整模染色效果前见
 * {@link EntityTintShaderGuard} 的改造结论: 应与墨染合并为单一 program + TintState,
 * 仅在顶点阶段显著不同或多 pass 需求时才保留独立 program.
 * <p>
 * 任何一步失败都只做降级处理, 绝不抛出异常.
 */
@SideOnly(Side.CLIENT)
public final class ShaderExtremeFreezingEntity
{
    private static final ResourceLocation RL_VERT =
            new ResourceLocation(TinkersThings.MOD_ID, "shaders/entity_passthrough.vert");
    private static final ResourceLocation RL_FRAG =
            new ResourceLocation(TinkersThings.MOD_ID, "shaders/extreme_freezing_entity.frag");

    /**
     * 极寒颜色 (淡蓝)
     */
    public static final float FREEZE_R = 0.45f, FREEZE_G = 0.72f, FREEZE_B = 0.95f;

    private static final int GL_CURRENT_PROGRAM = 0x8B8D;

    private static boolean triedInit;
    private static boolean available;

    private static int program;
    private static int locTexture = -1;
    private static int locLightmap = -1;
    private static int locFreezeAmount = -1;
    private static int locFreezeColor = -1;
    private static int locHurt = -1;
    private static int locFogEnabled = -1;
    private static int locFogMode = -1;
    private static int locFogColor = -1;
    private static int locFogStart = -1;
    private static int locFogEnd = -1;
    private static int locFogDensity = -1;

    private static int savedProgram;
    private static int depth;
    private static boolean suspended;
    private static boolean fogDirty = true;

    private static final FloatBuffer BUF1 = BufferUtils.createFloatBuffer(1);
    private static final FloatBuffer BUF3 = BufferUtils.createFloatBuffer(3);
    private static final FloatBuffer BUF_GET = BufferUtils.createFloatBuffer(16);

    private ShaderExtremeFreezingEntity() {}

    public static boolean isAvailable()
    {
        if (!triedInit) init();
        return available;
    }

    private static synchronized void init()
    {
        if (triedInit) return;
        triedInit = true;

        try
        {
            if (!OpenGlHelper.areShadersSupported())
            {
                TinkersThings.log("[extreme_freezing] shaders not supported, entity effect disabled");
                return;
            }

            int vert = compileShader(OpenGlHelper.GL_VERTEX_SHADER, RL_VERT);
            if (vert == 0) return;
            int frag = compileShader(OpenGlHelper.GL_FRAGMENT_SHADER, RL_FRAG);
            if (frag == 0)
            {
                OpenGlHelper.glDeleteShader(vert);
                return;
            }

            int prog = OpenGlHelper.glCreateProgram();
            OpenGlHelper.glAttachShader(prog, vert);
            OpenGlHelper.glAttachShader(prog, frag);
            OpenGlHelper.glLinkProgram(prog);
            OpenGlHelper.glDeleteShader(vert);
            OpenGlHelper.glDeleteShader(frag);

            if (OpenGlHelper.glGetProgrami(prog, OpenGlHelper.GL_LINK_STATUS) == 0)
            {
                TinkersThings.log("[extreme_freezing] failed to link program: "
                        + OpenGlHelper.glGetProgramInfoLog(prog, 32768));
                OpenGlHelper.glDeleteProgram(prog);
                return;
            }

            int prev = GlStateManager.glGetInteger(GL_CURRENT_PROGRAM);
            OpenGlHelper.glUseProgram(prog);

            locTexture = OpenGlHelper.glGetUniformLocation(prog, "uTexture");
            locLightmap = OpenGlHelper.glGetUniformLocation(prog, "uLightmap");
            locFreezeAmount = OpenGlHelper.glGetUniformLocation(prog, "uFreezeAmount");
            locFreezeColor = OpenGlHelper.glGetUniformLocation(prog, "uFreezeColor");
            locHurt = OpenGlHelper.glGetUniformLocation(prog, "uHurt");
            locFogEnabled = OpenGlHelper.glGetUniformLocation(prog, "uFogEnabled");
            locFogMode = OpenGlHelper.glGetUniformLocation(prog, "uFogMode");
            locFogColor = OpenGlHelper.glGetUniformLocation(prog, "uFogColor");
            locFogStart = OpenGlHelper.glGetUniformLocation(prog, "uFogStart");
            locFogEnd = OpenGlHelper.glGetUniformLocation(prog, "uFogEnd");
            locFogDensity = OpenGlHelper.glGetUniformLocation(prog, "uFogDensity");

            OpenGlHelper.glUniform1i(locTexture, 0);
            OpenGlHelper.glUniform1i(locLightmap, 1);
            BUF3.clear();
            BUF3.put(FREEZE_R).put(FREEZE_G).put(FREEZE_B).flip();
            OpenGlHelper.glUniform3(locFreezeColor, BUF3);

            OpenGlHelper.glUseProgram(prev);

            program = prog;
            available = true;
            EntityTintShaderGuard.registerFreezeProgram(program);
            TinkersThings.log("[extreme_freezing] freeze shader ready, program=" + program);
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] failed to initialise freeze shader");
            TinkersThings.log(e);
            program = 0;
            available = false;
        }
    }

    private static int compileShader(int type, ResourceLocation location)
    {
        String source = readShaderSource(location);
        if (source == null) return 0;

        byte[] bytes = source.getBytes(StandardCharsets.UTF_8);
        ByteBuffer sourceBuffer = BufferUtils.createByteBuffer(bytes.length);
        sourceBuffer.put(bytes);
        sourceBuffer.position(0);

        int shader = OpenGlHelper.glCreateShader(type);
        OpenGlHelper.glShaderSource(shader, sourceBuffer);
        OpenGlHelper.glCompileShader(shader);
        if (OpenGlHelper.glGetShaderi(shader, OpenGlHelper.GL_COMPILE_STATUS) == 0)
        {
            TinkersThings.log("[extreme_freezing] failed to compile " + location + ": "
                    + OpenGlHelper.glGetShaderInfoLog(shader, 32768));
            OpenGlHelper.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    private static String readShaderSource(ResourceLocation location)
    {
        try (InputStream is = Minecraft.getMinecraft().getResourceManager()
                .getResource(location).getInputStream())
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] chunk = new byte[4096];
            int len;
            while ((len = is.read(chunk)) > 0) out.write(chunk, 0, len);
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] failed to read shader source " + location);
            TinkersThings.log(e);
            return null;
        }
    }

    /**
     * 开始用极寒着色器渲染实体. 永远不抛异常.
     *
     * @param amount 极寒覆盖程度 (0 - 1)
     * @param hurt   受击闪烁程度 (0 - 1)
     */
    public static void begin(float amount, float hurt)
    {
        if (!isAvailable()) return;

        try
        {
            if (depth == 0)
            {
                savedProgram = EntityTintShaderGuard.sanitizeOuterProgram(
                        GlStateManager.glGetInteger(GL_CURRENT_PROGRAM), program);
                suspended = false;
            }
            depth++;

            if (suspended) return;

            OpenGlHelper.glUseProgram(program);

            BUF1.clear();
            BUF1.put(clamp01(amount)).flip();
            OpenGlHelper.glUniform1(locFreezeAmount, BUF1);

            BUF1.clear();
            BUF1.put(clamp01(hurt)).flip();
            OpenGlHelper.glUniform1(locHurt, BUF1);

            if (fogDirty) uploadFog();
        }
        catch (Throwable e)
        {
            available = false;
            depth = 0;
            suspended = false;
            TinkersThings.log("[extreme_freezing] freeze shader disabled after a render error");
            TinkersThings.log(e);
            try
            {
                OpenGlHelper.glUseProgram(safeRestoreProgram());
            }
            catch (Throwable ignored) {}
        }
    }

    private static void uploadFog()
    {
        fogDirty = false;

        boolean fogEnabled = GL11.glIsEnabled(GL11.GL_FOG);

        BUF1.clear();
        BUF1.put(fogEnabled ? 1f : 0f).flip();
        OpenGlHelper.glUniform1(locFogEnabled, BUF1);

        if (!fogEnabled) return;

        // 同 ShaderInkyEntity: 不可把 16 槽的 glGet 缓冲区直接丢给 glUniform3
        BUF_GET.clear();
        GL11.glGetFloat(GL11.GL_FOG_COLOR, BUF_GET);
        BUF3.clear();
        BUF3.put(BUF_GET.get(0)).put(BUF_GET.get(1)).put(BUF_GET.get(2)).flip();
        OpenGlHelper.glUniform3(locFogColor, BUF3);

        int glMode = GL11.glGetInteger(GL11.GL_FOG_MODE);
        float shaderMode = glMode == GL11.GL_LINEAR ? 0f : (glMode == GL11.GL_EXP ? 1f : 2f);
        BUF1.clear();
        BUF1.put(shaderMode).flip();
        OpenGlHelper.glUniform1(locFogMode, BUF1);

        BUF1.clear();
        BUF1.put(GL11.glGetFloat(GL11.GL_FOG_START)).flip();
        OpenGlHelper.glUniform1(locFogStart, BUF1);

        BUF1.clear();
        BUF1.put(GL11.glGetFloat(GL11.GL_FOG_END)).flip();
        OpenGlHelper.glUniform1(locFogEnd, BUF1);

        BUF1.clear();
        BUF1.put(GL11.glGetFloat(GL11.GL_FOG_DENSITY)).flip();
        OpenGlHelper.glUniform1(locFogDensity, BUF1);
    }

    public static void end()
    {
        if (!available || depth == 0) return;

        try
        {
            if (--depth == 0)
            {
                suspended = false;
                OpenGlHelper.glUseProgram(safeRestoreProgram());
            }
        }
        catch (Throwable e)
        {
            depth = 0;
            suspended = false;
            available = false;
            TinkersThings.log("[extreme_freezing] freeze shader disabled after a render error");
            TinkersThings.log(e);
        }
    }

    public static void suspend()
    {
        if (!available || depth == 0 || suspended) return;
        try
        {
            suspended = true;
            OpenGlHelper.glUseProgram(safeRestoreProgram());
        }
        catch (Throwable e)
        {
            available = false;
            depth = 0;
            suspended = false;
        }
    }

    public static void resume()
    {
        if (!available || depth == 0 || !suspended) return;
        try
        {
            suspended = false;
            OpenGlHelper.glUseProgram(program);
        }
        catch (Throwable e)
        {
            available = false;
            depth = 0;
            suspended = false;
        }
    }

    public static void forceReset()
    {
        fogDirty = true;

        if (!available) return;

        boolean dirty = depth != 0 || suspended;
        depth = 0;
        suspended = false;

        try
        {
            if (dirty)
            {
                OpenGlHelper.glUseProgram(safeRestoreProgram());
            }
            EntityTintShaderGuard.forceUnbindIfTintBound();
        }
        catch (Throwable e)
        {
            available = false;
        }
        savedProgram = 0;
    }

    private static int safeRestoreProgram()
    {
        return EntityTintShaderGuard.sanitizeOuterProgram(savedProgram, program);
    }

    private static float clamp01(float value)
    {
        if (value < 0) return 0;
        if (value > 1) return 1;
        return value;
    }
}
