package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
import firok.tiths.common.sync.ClientPotionEffectSync;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;

import static firok.tiths.common.Keys.colorPotionInky;
import static firok.tiths.common.Keys.uuidPotionInkyAttackSpeed;
import static firok.tiths.common.Keys.uuidPotionInkyFollowRange;
import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 墨染
 * <p>
 * 降低被墨水浸透的生物的索敌范围 (75%) 和攻击速度 (25%);
 * 客户端视觉方面: 墨滴粒子在这里生成 (仅当实体确实带有本效果时 {@code performEffect} 才会被调用);
 * 整体染黑则由渲染着色器负责.
 * </p>
 *
 * @see firok.tiths.client.render.ShaderInkyEntity
 * @see firok.tiths.client.render.RenderInkyEffect
 * @see ClientPotionEffectSync
 */
public class PotionInky extends BasePotion
{
    public PotionInky()
    {
        super(icon("inky"), true, colorPotionInky);
    }

    @Override
    public boolean isReady(int tick, int level)
    {
        return canTick(tick, 4, 0);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int level)
    {
        // 只在客户端生成粒子; 服务端早退, 避免无意义开销
        if (!entity.world.isRemote) return;
        if (!canTrigger(TinkersThings.randClient, 0.35)) return;

        double x = entity.posX + (TinkersThings.randClient.nextDouble() - 0.5) * entity.width * 0.9;
        double y = entity.posY + entity.height * (0.20 + TinkersThings.randClient.nextDouble() * 0.75);
        double z = entity.posZ + (TinkersThings.randClient.nextDouble() - 0.5) * entity.width * 0.9;

        ParticleBuilder.create(ParticleType.INKY_DRIP)
                .pos(x, y, z)
                .collide(true)
                .shaded(true)
                .spawn(entity.world);
    }

    /**
     * @see firok.tiths.common.Potions
     */
    @Override
    public void postInit()
    {
        // operation 2: 最终值 *= (1 + amount)
        registerPotionAttributeModifier(
                SharedMonsterAttributes.FOLLOW_RANGE,
                uuidPotionInkyFollowRange,
                -0.75,
                2
        ).registerPotionAttributeModifier(
                SharedMonsterAttributes.ATTACK_SPEED,
                uuidPotionInkyAttackSpeed,
                -0.25,
                2
        );
    }
}
