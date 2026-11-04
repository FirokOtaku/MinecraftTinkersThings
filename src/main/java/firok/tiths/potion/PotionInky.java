package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.client.particle.ParticleBuilder;
import firok.tiths.client.particle.ParticleType;
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
 * 至于"看起来被墨水糊住"的视觉效果, 由客户端渲染部分负责
 * </p>
 *
 * @see firok.tiths.client.render.ShaderInkyEntity
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
        return canTick(tick, 4, 2);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int level)
    {
        if(entity.isServerWorld()) return;
        if (!canTrigger(TinkersThings.randClient, 0.3)) return;

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
