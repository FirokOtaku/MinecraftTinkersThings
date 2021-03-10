package firok.tiths.client.particle;

import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Simple particle factory interface which takes a world and a position and returns a particle. Used (via method
 * references) in the client proxy to link particle enum types to actual particle classes. */
@SideOnly(Side.CLIENT)
@FunctionalInterface
public interface IParticleFactory
{
	ParticleBase createParticle(World world, double x, double y, double z);
}
