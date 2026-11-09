package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 墨染 - 屏幕叠加层.
 * <p>
 * 玩家身上带有墨染效果时, 在画面上盖一层带径向透明度的墨色贴图,
 * 用来表现"视线被墨水糊住": 整体变暗, 视野边缘更黑.
 * <p>
 * 叠加层画在 {@code Pre(ALL)} 这个位置, 也就是位于世界画面之上、快捷栏和血量等 HUD 之下,
 * 这样世界会变暗, 而玩家依然能正常读取自己的状态栏.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersThings.MOD_ID)
public final class OverlayInky
{
    private static final ResourceLocation TEXTURE_OVERLAY =
            new ResourceLocation(TinkersThings.MOD_ID, "misc/inky_overlay.png");

    /**
     * 叠加层不透明度. 纹理本身的 alpha 在中间约 0.46 / 边缘约 0.87,
     * 两者相乘后中间约 25% / 边缘约 48%, 这样"变暗"是明确可见的,
     * 同时中心区域仍然能看清场景.
     */
    private static final float ALPHA_BASE = 0.55f;
    private static final float ALPHA_PER_LEVEL = 0.10f;
    private static final float ALPHA_MAX = 0.82f;

    private OverlayInky() {}

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRenderOverlay(RenderGameOverlayEvent.Pre event)
    {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;
        if (!Configs.Client.enable_inky_overlay) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.player;
        if (player == null) return;

        PotionEffect effect = player.getActivePotionEffect(Potions.inky);
        if (effect == null) return;

        float alpha = Math.min(ALPHA_MAX, ALPHA_BASE + ALPHA_PER_LEVEL * effect.getAmplifier());
        renderOverlay(mc, event.getResolution(), alpha);
    }

    /**
     * 这个方法的 GL 状态处理方式完全对齐原版的 {@code renderPumpkinOverlay}:
     * 进入时关掉深度测试和 alpha 测试, 画完再按原版的习惯恢复,
     * 这样就不会打乱 {@link GlStateManager} 里缓存的状态.
     */
    private static void renderOverlay(Minecraft mc, ScaledResolution resolution, float alpha)
    {
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(
                GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.disableAlpha();
        GlStateManager.color(1, 1, 1, alpha);

        mc.getTextureManager().bindTexture(TEXTURE_OVERLAY);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        buffer.pos(0, resolution.getScaledHeight(), -90).tex(0, 1).endVertex();
        buffer.pos(resolution.getScaledWidth(), resolution.getScaledHeight(), -90).tex(1, 1).endVertex();
        buffer.pos(resolution.getScaledWidth(), 0, -90).tex(1, 0).endVertex();
        buffer.pos(0, 0, -90).tex(0, 0).endVertex();
        tessellator.draw();

        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1, 1, 1, 1);
    }
}
