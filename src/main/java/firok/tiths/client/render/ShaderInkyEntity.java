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
 * 墨染 - 生物实体染色着色器.
 * <p>
 * 使用兼容剖面的顶点 + 片元程序: 顶点阶段用 {@code entity_passthrough.vert}
 * 复刻固定管线的变换 / 色 / 纹理坐标 / 雾坐标, 这样仍然可以直接吃原版与匠魂
 * 提交的顶点数组 / 显示列表, 不必自己绑属性.
 * 只挂片元阶段在部分驱动上虽能 link, 但绘制时会抛 {@code GL_INVALID_OPERATION},
 * 并把后续整帧 (含 UI) 弄黑.
 * <p>
 * 片元着色器做的事: 按 贴图 * 顶点色 * 光照 采样出颜色以后,
 * 往墨色混合并整体压暗, 让生物看起来像被整片墨汁浸透.
 * 因为着色器接管了片元阶段, 原版用固定管线做的雾和受击红闪需要在这里手动补上.
 * <p>
 * 本类是"每种效果一个 program"的过渡实现; 与极寒等同类染色并存时依赖
 * {@link EntityTintShaderGuard} 与渲染侧互斥. 再扩展更多整模染色效果前,
 * 应按该护栏类注释中的结论合并为单一实体染色 program, 而不是再复制本类.
 * <p>
 * 任何一步失败 (显卡不支持 / 编译失败 / 运行期异常) 都只做降级处理, 绝不抛出异常:
 * 实体的渲染事件是从 {@code RenderLivingBase.doRender} 里直接 post 的,
 * 那里没有 try/catch, 一旦抛出去就是整帧崩溃.
 */
@SideOnly(Side.CLIENT)
public final class ShaderInkyEntity
{
    private static final ResourceLocation RL_VERT =
            new ResourceLocation(TinkersThings.MOD_ID, "shaders/entity_passthrough.vert");
    private static final ResourceLocation RL_FRAG =
            new ResourceLocation(TinkersThings.MOD_ID, "shaders/inky_entity.frag");

    /**
     * 墨水颜色 (冷调的黑)
     */
    public static final float INK_R = 0.10f, INK_G = 0.09f, INK_B = 0.16f;

    private static final int GL_CURRENT_PROGRAM = 0x8B8D;

    private static boolean triedInit;
    private static boolean available;

    private static int program;
    private static int locTexture = -1;
    private static int locLightmap = -1;
    private static int locInkAmount = -1;
    private static int locInkColor = -1;
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
    /**
     * {@code GL11.glGetFloat} 会强制校验缓冲区容量至少 16 个元素
     * (不论这次实际返回几个), 所以必须用这么大的缓冲区.
     */
    private static final FloatBuffer BUF_GET = BufferUtils.createFloatBuffer(16);

    private ShaderInkyEntity() {}

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
                TinkersThings.log("[inky] shaders not supported, ink entity effect disabled");
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
            // 链接完成后着色器对象即可释放
            OpenGlHelper.glDeleteShader(vert);
            OpenGlHelper.glDeleteShader(frag);

            if (OpenGlHelper.glGetProgrami(prog, OpenGlHelper.GL_LINK_STATUS) == 0)
            {
                TinkersThings.log("[inky] failed to link program: "
                        + OpenGlHelper.glGetProgramInfoLog(prog, 32768));
                OpenGlHelper.glDeleteProgram(prog);
                return;
            }

            int prev = GlStateManager.glGetInteger(GL_CURRENT_PROGRAM);
            OpenGlHelper.glUseProgram(prog);

            locTexture = OpenGlHelper.glGetUniformLocation(prog, "uTexture");
            locLightmap = OpenGlHelper.glGetUniformLocation(prog, "uLightmap");
            locInkAmount = OpenGlHelper.glGetUniformLocation(prog, "uInkAmount");
            locInkColor = OpenGlHelper.glGetUniformLocation(prog, "uInkColor");
            locHurt = OpenGlHelper.glGetUniformLocation(prog, "uHurt");
            locFogEnabled = OpenGlHelper.glGetUniformLocation(prog, "uFogEnabled");
            locFogMode = OpenGlHelper.glGetUniformLocation(prog, "uFogMode");
            locFogColor = OpenGlHelper.glGetUniformLocation(prog, "uFogColor");
            locFogStart = OpenGlHelper.glGetUniformLocation(prog, "uFogStart");
            locFogEnd = OpenGlHelper.glGetUniformLocation(prog, "uFogEnd");
            locFogDensity = OpenGlHelper.glGetUniformLocation(prog, "uFogDensity");

            // 常量 uniform 只设置一次
            OpenGlHelper.glUniform1i(locTexture, 0);
            OpenGlHelper.glUniform1i(locLightmap, 1);
            BUF3.clear();
            BUF3.put(INK_R).put(INK_G).put(INK_B).flip();
            OpenGlHelper.glUniform3(locInkColor, BUF3);

            OpenGlHelper.glUseProgram(prev);

            program = prog;
            available = true;
            EntityTintShaderGuard.registerInkyProgram(program);
            TinkersThings.log("[inky] ink shader ready, program=" + program);
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] failed to initialise ink shader");
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
            TinkersThings.log("[inky] failed to compile " + location + ": "
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
            TinkersThings.log("[inky] failed to read shader source " + location);
            TinkersThings.log(e);
            return null;
        }
    }

    /**
     * 开始用墨染着色器渲染实体. 永远不抛异常.
     *
     * @param amount 墨水覆盖程度 (0 - 1)
     * @param hurt   受击闪烁程度 (0 - 1)
     */
    public static void begin(float amount, float hurt)
    {
        if (!isAvailable()) return;

        try
        {
            if (depth == 0)
            {
                // 不能把本程序或其它实体染色程序记成 outer, 否则双重 debuff 时 end 会解不干净
                savedProgram = EntityTintShaderGuard.sanitizeOuterProgram(
                        GlStateManager.glGetInteger(GL_CURRENT_PROGRAM), program);
                suspended = false;
            }
            depth++;

            if (suspended) return;

            OpenGlHelper.glUseProgram(program);

            BUF1.clear();
            BUF1.put(clamp01(amount)).flip();
            OpenGlHelper.glUniform1(locInkAmount, BUF1);

            BUF1.clear();
            BUF1.put(clamp01(hurt)).flip();
            OpenGlHelper.glUniform1(locHurt, BUF1);

            if (fogDirty) uploadFog();
        }
        catch (Throwable e)
        {
            // 渲染期出错就退回普通渲染, 不要让异常逃出事件总线
            available = false;
            depth = 0;
            suspended = false;
            TinkersThings.log("[inky] ink shader disabled after a render error");
            TinkersThings.log(e);
            try
            {
                OpenGlHelper.glUseProgram(safeRestoreProgram());
            }
            catch (Throwable ignored) {}
        }
    }

    /**
     * 把当前固定管线的雾参数同步给着色器.
     * 着色器接管片元阶段以后, 原版通过固定管线做的雾就失效了.
     */
    private static void uploadFog()
    {
        fogDirty = false;

        boolean fogEnabled = GL11.glIsEnabled(GL11.GL_FOG);

        BUF1.clear();
        BUF1.put(fogEnabled ? 1f : 0f).flip();
        OpenGlHelper.glUniform1(locFogEnabled, BUF1);

        if (!fogEnabled) return;

        // glGetFloat(GL_FOG_COLOR) 要求缓冲区至少 16 个元素, 但 LWJGL 的
        // glUniform3(FloatBuffer) 会按 remaining()/3 当作数组长度上传.
        // 若直接把 16 槽的 BUF_GET 丢进去, count 变成 5, 对非数组 vec3 uniform
        // 会触发 GL_INVALID_OPERATION (1282), 严重时整帧发黑.
        BUF_GET.clear();
        GL11.glGetFloat(GL11.GL_FOG_COLOR, BUF_GET);
        BUF3.clear();
        BUF3.put(BUF_GET.get(0)).put(BUF_GET.get(1)).put(BUF_GET.get(2)).flip();
        OpenGlHelper.glUniform3(locFogColor, BUF3);

        // GL_LINEAR / GL_EXP / GL_EXP2 映射成 0 / 1 / 2
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

    /**
     * 结束墨染着色器渲染, 恢复到之前使用的 program. 永远不抛异常.
     */
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
            TinkersThings.log("[inky] ink shader disabled after a render error");
            TinkersThings.log(e);
        }
    }

    /**
     * 临时停用着色器. 名字牌之类的 UI 性质内容不应该被墨水染色,
     * 渲染这类内容前先挂起, 渲染完再 {@link #resume()} 恢复.
     */
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

    /**
     * 恢复被 {@link #suspend()} 停用的着色器.
     */
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

    /**
     * 强制复位.
     * <p>
     * {@code RenderLivingEvent.Post} 在 {@code RenderLivingEvent.Pre} 被取消时不会触发,
     * 那样 {@link #begin} 和 {@link #end} 就配不上对, 着色器会一直挂在当前 program 上.
     * 每帧世界渲染结束时调用一次兜底.
     */
    public static void forceReset()
    {
        // 雾参数每帧都会变 (跟着视角 / 液体 / 药水效果), 下一帧重新采样一次
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
            // 双重 debuff 时可能出现 depth 已归零但当前仍绑着另一个 tint program;
            // 统一交给护栏做最终解绑
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
