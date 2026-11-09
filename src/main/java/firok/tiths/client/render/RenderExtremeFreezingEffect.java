package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 极寒状态效果的客户端表现.
 * <p>
 * 渲染生物主体时切换到极寒着色器, 让生物整体发蓝; 名字牌等 UI 性质的内容会被排除在外.
 * 寒气粒子由 {@link firok.tiths.potion.PotionExtremeFreezing#performEffect} 在效果 tick 时生成.
 * <p>
 * 这里所有渲染回调都必须保证不抛异常:
 * Forge 是在 {@link RenderLivingBase#doRender} 里直接 post 这些事件的,
 * 那个位置没有 try/catch, 抛出去就是整帧渲染崩溃.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersThings.MOD_ID)
public final class RenderExtremeFreezingEffect
{
    /**
     * 极寒浓度
     */
    private static final float FREEZE_AMOUNT = 0.70f;

    /**
     * 着色器不可用时的兜底染色
     */
    private static final float FALLBACK_R = 0.55f;
    private static final float FALLBACK_G = 0.75f;
    private static final float FALLBACK_B = 0.95f;

    private static boolean loggedFirstFrozenEntity;

    private RenderExtremeFreezingEffect() {}

    public static boolean hasExtremeFreezing(EntityLivingBase entity)
    {
        return entity != null && entity.getActivePotionEffect(Potions.extreme_freezing) != null;
    }

    private static boolean entityShaderEnabled()
    {
        return Configs.Client.enable_extreme_freezing_entity_shader;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?> event)
    {
        try
        {
            if (event.isCanceled()) return;
            if (!entityShaderEnabled()) return;

            EntityLivingBase entity = event.getEntity();
            if (!hasExtremeFreezing(entity)) return;

            if (!loggedFirstFrozenEntity)
            {
                loggedFirstFrozenEntity = true;
                TinkersThings.log("[extreme_freezing] rendering frozen entity " + entity.getName()
                        + " (" + entity.getClass().getSimpleName() + ")"
                        + ", shader available: " + ShaderExtremeFreezingEntity.isAvailable());
            }

            if (ShaderExtremeFreezingEntity.isAvailable())
            {
                ShaderExtremeFreezingEntity.begin(FREEZE_AMOUNT, hurtAmount(entity));
            }
            else
            {
                GlStateManager.color(FALLBACK_R, FALLBACK_G, FALLBACK_B, 1f);
            }
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] error in render pre");
            TinkersThings.log(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRenderLivingPost(RenderLivingEvent.Post<?> event)
    {
        try
        {
            if (!entityShaderEnabled()) return;

            EntityLivingBase entity = event.getEntity();
            if (!hasExtremeFreezing(entity)) return;

            if (ShaderExtremeFreezingEntity.isAvailable())
            {
                ShaderExtremeFreezingEntity.end();
            }
            else
            {
                GlStateManager.color(1, 1, 1, 1);
            }
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] error in render post");
            TinkersThings.log(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderSpecialsPre(RenderLivingEvent.Specials.Pre<?> event)
    {
        try
        {
            if (!entityShaderEnabled()) return;
            if (!hasExtremeFreezing(event.getEntity())) return;
            ShaderExtremeFreezingEntity.suspend();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] error in specials pre");
            TinkersThings.log(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderSpecialsPost(RenderLivingEvent.Specials.Post<?> event)
    {
        try
        {
            if (!entityShaderEnabled()) return;
            if (!hasExtremeFreezing(event.getEntity())) return;
            ShaderExtremeFreezingEntity.resume();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] error in specials post");
            TinkersThings.log(e);
        }
    }

    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event)
    {
        try
        {
            ShaderExtremeFreezingEntity.isAvailable();
            ShaderExtremeFreezingEntity.forceReset();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[extreme_freezing] error in render world last");
            TinkersThings.log(e);
        }
    }

    private static float hurtAmount(EntityLivingBase entity)
    {
        if (entity.deathTime > 0) return 0.55f;
        if (entity.hurtTime > 0 && entity.maxHurtTime > 0)
        {
            return entity.hurtTime / (float) entity.maxHurtTime * 0.55f;
        }
        return 0;
    }
}
