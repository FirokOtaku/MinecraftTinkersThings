package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
import firok.tiths.common.Potions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * 墨染状态效果的客户端表现.
 * <p>
 * 包含两部分:
 * <ol>
 *     <li>渲染生物主体时切换到墨染着色器, 让生物整体变黑; 名字牌等 UI 性质的内容会被排除在外</li>
 *     <li>周期性从生物身上抖落黑色墨滴粒子</li>
 * </ol>
 * <p>
 * 这里所有渲染回调都必须保证不抛异常:
 * Forge 是在 {@code RenderLivingBase.doRender} 里直接 post 这些事件的,
 * 那个位置没有 try/catch, 抛出去就是整帧渲染崩溃.
 */
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = TinkersThings.MOD_ID)
public final class RenderInkyEffect
{
    private static final Random RAND = new Random();

    /**
     * 墨水浓度
     */
    private static final float INK_AMOUNT = 0.85f;

    /**
     * 着色器不可用时的兜底染色 (直接乘到顶点色上, 只能整体压暗)
     */
    private static final float FALLBACK_R = 0.26f;
    private static final float FALLBACK_G = 0.23f;
    private static final float FALLBACK_B = 0.32f;

    private RenderInkyEffect() {}

    public static boolean hasInky(EntityLivingBase entity)
    {
        return entity != null && entity.getActivePotionEffect(Potions.inky) != null;
    }

    /**
     * 生物主体渲染之前切到墨染着色器.
     * <p>
     * 用最低优先级, 让别的模组的处理器先跑完:
     * 一旦它们取消了渲染事件 (比如云雾效果会把远处的生物整个跳过),
     * 我们就不该再切换着色器, 否则 shader 会一直挂在那里.
     */
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderLivingPre(RenderLivingEvent.Pre<?> event)
    {
        try
        {
            if (event.isCanceled()) return;

            EntityLivingBase entity = event.getEntity();
            if (!hasInky(entity)) return;

            if (ShaderInkyEntity.isAvailable())
            {
                ShaderInkyEntity.begin(INK_AMOUNT, hurtAmount(entity));
            }
            else
            {
                // 显卡不支持 shader 时的兜底: 直接乘顶点色, 至少能看出变暗
                GlStateManager.color(FALLBACK_R, FALLBACK_G, FALLBACK_B, 1f);
            }
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error in render pre");
            TinkersThings.log(e);
        }
    }

    /**
     * 生物主体渲染之后恢复原本的 shader program / 顶点色
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onRenderLivingPost(RenderLivingEvent.Post<?> event)
    {
        try
        {
            EntityLivingBase entity = event.getEntity();
            if (!hasInky(entity)) return;

            if (ShaderInkyEntity.isAvailable())
            {
                ShaderInkyEntity.end();
            }
            else
            {
                GlStateManager.color(1, 1, 1, 1);
            }
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error in render post");
            TinkersThings.log(e);
        }
    }

    /**
     * 名字牌之类的 UI 性质内容不应该被墨水染色, 渲染这类内容时先挂起着色器
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onRenderSpecialsPre(RenderLivingEvent.Specials.Pre<?> event)
    {
        try
        {
            if (!hasInky(event.getEntity())) return;
            ShaderInkyEntity.suspend();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error in specials pre");
            TinkersThings.log(e);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderSpecialsPost(RenderLivingEvent.Specials.Post<?> event)
    {
        try
        {
            if (!hasInky(event.getEntity())) return;
            ShaderInkyEntity.resume();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error in specials post");
            TinkersThings.log(e);
        }
    }

    /**
     * 兜底: {@link RenderLivingEvent.Post} 在 {@link RenderLivingEvent.Pre} 被取消时不会触发,
     * 那样 begin / end 就配不上对. 每帧世界渲染结束的时候强制复位一次.
     */
    @SubscribeEvent
    public static void onRenderWorldLast(RenderWorldLastEvent event)
    {
        try
        {
            ShaderInkyEntity.forceReset();
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error in render world last");
            TinkersThings.log(e);
        }
    }

    /**
     * 墨滴粒子. 按 tick 生成, 和帧率无关.
     */
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase != TickEvent.Phase.END) return;

        try
        {
            Minecraft mc = Minecraft.getMinecraft();
            if (mc.world == null) return;

            for (Entity entity : mc.world.getLoadedEntityList())
            {
                if (!(entity instanceof EntityLivingBase)) continue;

            }
        }
        catch (Throwable e)
        {
            TinkersThings.log("[inky] error when spawning drip particles");
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
