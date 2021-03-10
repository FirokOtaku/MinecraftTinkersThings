package firok.tiths.client.particle;
;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class ParticleFactory
{
	/** Static particle factory map */
	private static final Map<ResourceLocation, IParticleFactory> factories = new HashMap<>();

	static void addParticleFactory(ParticleType type, IParticleFactory factory)
	{
		factories.put(type.rlMain, factory);
	}

	public static ParticleBase createParticle(ResourceLocation type, World world, double x, double y, double z)
	{
		IParticleFactory factory = factories.get(type);
		return factory == null ? null : factory.createParticle(world, x, y, z);
	}
}
