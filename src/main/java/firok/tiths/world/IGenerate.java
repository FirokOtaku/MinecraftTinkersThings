package firok.tiths.world;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@FunctionalInterface
public interface IGenerate
{
	boolean gen(World world, Random rand, BlockPos pos);
}
