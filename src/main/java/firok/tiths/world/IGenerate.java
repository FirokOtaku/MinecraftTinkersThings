package firok.tiths.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@FunctionalInterface
public interface IGenerate
{
	boolean gen(World world, BlockPos pos);
}
