package firok.tiths.world;

import firok.tiths.common.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canMeteoGen;
import static firok.tiths.util.Predicates.canTrigger;

// 世界生成-云
public class WorldGenCloud implements IChunkGen
{
	public WorldGenCloud()
	{
		;
	}

	@Override
	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
	{
		return false;
//		int posX=chunkX+5+rand.nextInt(11),posZ=chunkZ+5+rand.nextInt(11);
//		return generate(world, world.getTopSolidOrLiquidBlock(new BlockPos(posX,0,posZ)).down(2),stateOre, rateOre, rateChunk);
	}

}
