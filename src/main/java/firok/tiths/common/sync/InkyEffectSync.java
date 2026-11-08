package firok.tiths.common.sync;

import firok.tiths.TinkersThings;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.server.SPacketEntityEffect;
import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.WorldServer;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 墨染效果的客户端同步.
 * <p>
 * 1.12.2 原版只在 {@code EntityPlayerMP} 自身获得/失去药水时发包,
 * 以及别的玩家开始追踪某个实体时把"当时已有"的效果同步过去.
 * 对已经在视野里的怪物后来才挂上的效果, 客户端 {@code activePotionsMap} 里根本没有它,
 * 于是基于 {@code getActivePotionEffect} 的渲染 / 粒子都不会触发.
 * <p>
 * 这里把墨染的添加和移除主动同步给正在追踪该实体的玩家.
 * 玩家自己拿到效果时原版本来就会发给自己, {@code sendToTracking} 不会发给自己,
 * 因此不会重复.
 * <p>
 * 注意: Forge 的 {@link PotionEvent.PotionExpiryEvent} 并不继承
 * {@link PotionEvent.PotionRemoveEvent}, 自然到期和主动移除是两条事件路径, 都要处理.
 */
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public final class InkyEffectSync
{
    private InkyEffectSync() {}

    @SubscribeEvent
    public static void onPotionAdded(PotionEvent.PotionAddedEvent event)
    {
        PotionEffect effect = event.getPotionEffect();
        if (effect == null || effect.getPotion() != Potions.inky) return;
        syncAdd(event.getEntityLiving(), effect);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionRemoved(PotionEvent.PotionRemoveEvent event)
    {
        if (event.isCanceled()) return;
        syncRemove(event.getEntityLiving(), event.getPotion());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPotionExpired(PotionEvent.PotionExpiryEvent event)
    {
        if (event.isCanceled()) return;
        PotionEffect effect = event.getPotionEffect();
        if (effect == null) return;
        syncRemove(event.getEntityLiving(), effect.getPotion());
    }

    private static void syncAdd(EntityLivingBase entity, PotionEffect effect)
    {
        WorldServer world = asServerWorld(entity);
        if (world == null) return;

        world.getEntityTracker().sendToTracking(
                entity,
                new SPacketEntityEffect(entity.getEntityId(), effect)
        );
    }

    private static void syncRemove(EntityLivingBase entity, Potion potion)
    {
        if (potion != Potions.inky) return;
        WorldServer world = asServerWorld(entity);
        if (world == null) return;

        world.getEntityTracker().sendToTracking(
                entity,
                new SPacketRemoveEntityEffect(entity.getEntityId(), potion)
        );
    }

    private static WorldServer asServerWorld(EntityLivingBase entity)
    {
        if (entity == null || entity.world == null || entity.world.isRemote) return null;
        if (!(entity.world instanceof WorldServer)) return null;
        return (WorldServer) entity.world;
    }
}
