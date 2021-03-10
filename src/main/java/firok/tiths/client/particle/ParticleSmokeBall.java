package firok.tiths.client.particle;

import firok.tiths.TinkersThings;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class ParticleSmokeBall extends ParticleBase
{
	public static final ResourceLocation TEXTURE = TinkersThings.resource("particles/smoke_ball");

	public ParticleSmokeBall(World world, double x, double y, double z, ResourceLocation... textures)
	{
		super(world, x, y, z, TEXTURE);

		this.setRBGColorF(255,255,255);
		this.setSize(0.01f,0.01f);

		this.particleScale *= this.rand.nextFloat() + 0.1f;
		this.particleMaxAge = 35 + rand.nextInt(25);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		this.particleAngle = ( world.getTotalWorldTime() % 4 ) * (1f/4);

	}
}
