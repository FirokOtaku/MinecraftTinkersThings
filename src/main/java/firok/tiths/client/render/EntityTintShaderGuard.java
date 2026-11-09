package firok.tiths.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 实体染色着色器的共享护栏.
 * <p>
 * <b>当前职责 (过渡方案)</b><br>
 * 墨染 / 极寒各自维护 begin/end, 但同一实体可能同时带两种效果.
 * 若两个着色器都切换 program, 后开始的会把先开始的 program 存进 {@code savedProgram},
 * 而 Post 又按注册顺序 FIFO 结束, 最终会把另一个 tint program 留在管线上,
 * 后续整帧 (含 UI) 都被片元着色器染成近乎全黑.
 * 这里集中登记 tint program id, 提供:
 * <ul>
 *   <li>保存 outer program 时把其它 tint program 过滤成 0</li>
 *   <li>每帧世界渲染结束时, 若当前绑定的仍是 tint program 则强制解绑</li>
 * </ul>
 * 渲染侧另用互斥让位 (极寒让墨染) 保证同一实体最多启用一套 program.
 * <p>
 * <b>后续扩展结论 (再加同类"整模染色"效果前请按此改造)</b>
 * <ol>
 *   <li><b>不要</b>继续 "每种效果一套独立 GL program + yieldsToXxx 互斥".
 *       OpenGL 同时只能绑一个 program, 实体主体也只画一遍, 互斥只能防黑屏,
 *       叠不出复合视觉, 效果一多优先级矩阵会组合爆炸.</li>
 *   <li><b>应</b>收成单一实体染色 program (可继续复用 {@code entity_passthrough.vert}):
 *       片元按管线组合各效果, 例如
 *       {@code base = tex*color*lightmap; applyInky; applyFreeze; applyHurt; applyFog}.
 *       Java 侧用一个调度器 (如 {@code EntityTintRenderer}) + 一份 {@code TintState}
 *       在 {@code RenderLivingEvent.Pre} 收集该实体启用的 tint 参数, 只 begin/end 一次;
 *       Post / forceReset 也只由这一处负责恢复.</li>
 *   <li><b>分层</b>: GPU program 与 GL 生命周期必须集中;
 *       药水 / 特性 / 粒子 / 屏幕 overlay 仍按玩法模块拆分, 不必揉成一个类.
 *       Overlay 与粒子不走实体 program, 可继续独立.</li>
 *   <li><b>仍可保留独立 program 的例外</b>: 顶点阶段显著不同、需要多 pass
 *       (描边 / X-ray)、或根本不是"整模染色/滤镜"类效果.</li>
 *   <li><b>迁移顺序建议</b>: 抽调度器与 TintState → 把墨染/极寒收进同一片元 →
 *       删掉互斥与按效果硬编码的 registerXxxProgram → 新效果只加 collector + shader 分支.</li>
 *   <li><b>叠层语义需先定</b>: 优先级覆盖 / 顺序 mix / 按强度加权 —— 这是设计问题,
 *       定下来写进统一片元即可; 与 GL 解绑护栏无关.</li>
 * </ol>
 * <p>
 * 相关入口: {@link ShaderInkyEntity}, {@link ShaderExtremeFreezingEntity},
 * {@link RenderInkyEffect}, {@link RenderExtremeFreezingEffect},
 * 资源 {@code assets/tiths/shaders/entity_passthrough.vert}.
 */
@SideOnly(Side.CLIENT)
public final class EntityTintShaderGuard
{
    private static final int GL_CURRENT_PROGRAM = 0x8B8D;

    private static int inkyProgram;
    private static int freezeProgram;

    private EntityTintShaderGuard() {}

    public static void registerInkyProgram(int program)
    {
        inkyProgram = program;
    }

    public static void registerFreezeProgram(int program)
    {
        freezeProgram = program;
    }

    public static boolean isTintProgram(int program)
    {
        return program != 0 && (program == inkyProgram || program == freezeProgram);
    }

    /**
     * 开始着色前用来清洗 "之前的 program":
     * 自己或其它实体染色 program 都不能当 outer 存下来, 否则 end 解不干净.
     */
    public static int sanitizeOuterProgram(int saved, int selfProgram)
    {
        if (saved == selfProgram || isTintProgram(saved)) return 0;
        return saved;
    }

    /**
     * 世界渲染结束时的兜底: 不管各着色器自己的 depth 是否归零,
     * 只要当前 GL program 仍是实体染色程序, 就切回固定管线.
     */
    public static void forceUnbindIfTintBound()
    {
        try
        {
            int current = GlStateManager.glGetInteger(GL_CURRENT_PROGRAM);
            if (isTintProgram(current))
            {
                OpenGlHelper.glUseProgram(0);
            }
        }
        catch (Throwable ignored) {}
    }
}
