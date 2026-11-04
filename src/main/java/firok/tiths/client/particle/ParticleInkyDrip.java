package firok.tiths.client.particle;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 墨染 - 墨水滴落粒子
 * <p>
 * 从生物身上缓慢滴落的黑色墨滴, 用来配合"墨染"状态效果.
 * 墨汁比水粘稠得多, 所以下坠速度很小, 水平方向也几乎没有漂移,
 * 落地以后立即消失, 不在世界里留下任何东西.
 */
public class ParticleInkyDrip extends ParticleBase
{
    public static final ResourceLocation TEXTURE = TinkersThings.resource("particles/inky_drip");

    public ParticleInkyDrip(World world, double x, double y, double z, ResourceLocation... textures)
    {
        super(world, x, y, z, TEXTURE);

        this.setRBGColorF(1, 1, 1);
        this.setSize(0.02f, 0.02f);

        this.particleScale *= 0.55f + this.rand.nextFloat() * 0.45f;
        this.particleMaxAge = 30 + rand.nextInt(20);

        // 不套用原版的自由落体, 而是给一个很小很稳定的下坠速度
        this.setGravity(false);
        this.motionX = (this.rand.nextDouble() - 0.5) * 0.010;
        this.motionY = -0.012 - this.rand.nextDouble() * 0.012;
        this.motionZ = (this.rand.nextDouble() - 0.5) * 0.010;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();

        // 额外的阻尼, 让墨滴看起来"粘"在身上而不是直接掉下去
        this.motionX *= 0.50;
        this.motionZ *= 0.50;

        if (this.onGround)
        {
            // 墨滴落地就摊开消失, 不留任何残留
            this.setExpired();
        }
    }
}
