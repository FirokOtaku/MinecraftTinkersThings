package firok.tiths.potion;

import firok.tiths.TinkersThings;
import firok.tiths.common.sync.ClientPotionEffectSync;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.EnumParticleTypes;

import static firok.tiths.common.Keys.colorPotionExtremeFreezing;
import static firok.tiths.common.Keys.uuidPotionExtremeFreezingSpeed;
import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 极寒
 * <p>
 * 减速由 Attribute Modifier 提供 (倍率与原版缓慢一致: 每级 -15%);
 * 客户端视觉方面: 淡蓝寒气粒子在这里生成 (仅当实体确实带有本效果时 {@code performEffect} 才会被调用);
 * 整体发蓝则由渲染着色器负责.
 * </p>
 *
 * @see firok.tiths.client.render.ShaderExtremeFreezingEntity
 * @see firok.tiths.client.render.RenderExtremeFreezingEffect
 * @see ClientPotionEffectSync
 */
public class PotionExtremeFreezing extends BasePotion
{
    /**
     * 寒气粒子颜色 (淡蓝)
     */
    private static final float PARTICLE_R = 0.55f;
    private static final float PARTICLE_G = 0.78f;
    private static final float PARTICLE_B = 1.00f;

    public PotionExtremeFreezing()
    {
        super(icon("extreme_freezing"), true, colorPotionExtremeFreezing);
    }

    /**
     * @see firok.tiths.common.Potions
     */
    @Override
    public void postInit()
    {
        // operation 2: 最终速度 *= (1 + amount * (amplifier + 1)), 与原版缓慢一致
        registerPotionAttributeModifier(
                SharedMonsterAttributes.MOVEMENT_SPEED,
                uuidPotionExtremeFreezingSpeed,
                -0.15,
                2
        );
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
        if (!canTrigger(TinkersThings.randClient, 0.40)) return;

        spawnFrostParticle(entity);
    }

    /**
     * 复用原版 CLOUD 粒子并调成淡蓝色, 让寒气从实体身上缓慢向下飘落.
     * 仅应在客户端调用.
     */
    private static void spawnFrostParticle(EntityLivingBase entity)
    {
        double x = entity.posX + (TinkersThings.randClient.nextDouble() - 0.5) * entity.width * 0.9;
        double y = entity.posY + entity.height * (0.35 + TinkersThings.randClient.nextDouble() * 0.55);
        double z = entity.posZ + (TinkersThings.randClient.nextDouble() - 0.5) * entity.width * 0.9;

        double vx = (TinkersThings.randClient.nextDouble() - 0.5) * 0.02;
        double vy = -0.05 - TinkersThings.randClient.nextDouble() * 0.04;
        double vz = (TinkersThings.randClient.nextDouble() - 0.5) * 0.02;

        Particle particle = Minecraft.getMinecraft().effectRenderer.spawnEffectParticle(
                EnumParticleTypes.CLOUD.getParticleID(),
                x, y, z,
                vx, vy, vz
        );
        if (particle != null)
        {
            particle.setRBGColorF(PARTICLE_R, PARTICLE_G, PARTICLE_B);
        }
    }
}
