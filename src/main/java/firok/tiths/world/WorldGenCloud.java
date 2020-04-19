package firok.tiths.world;

import firok.tiths.common.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

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
		if(!canTrigger(rand,0.06f)) return true;

		int posX=chunkX+5+rand.nextInt(11),posY=rand.nextInt(30)+150,posZ=chunkZ+5+rand.nextInt(11);

		return generate(world, new BlockPos(posX,posY,posZ));
	}

	public static boolean generate(World world, BlockPos pos)
	{
		Random rand=world.rand;

		// 一种二次曲面，是椭圆在三维空间的推广。
		// 椭球在xyz-笛卡尔坐标系中的方程是：x2 / a2+y2 / b2+z2 / c2=1。

//		final int cx=pos.getX(),cy=pos.getY(),cz=pos.getZ();
		final int rx=5+rand.nextInt(8),ry=2+rand.nextInt(3),rz=5+rand.nextInt(8);
		final float a2=rx*rx,b2=ry*ry,c2=rz*rz;

		IBlockState cloud=Blocks.blockCloud.getDefaultState();

//		int countReplace=0,countInside=0;

		FOR_X: for(int ox=-rx; ox<=rx; ox++)
		{
			int ox2=ox*ox;
			FOR_Z: for(int oz=-rz; oz<=rz; oz++)
			{
				int oz2=oz*oz;
				FOR_Y: for(int oy=-ry; oy<=ry; oy++)
				{
					int oy2=oy*oy;
					final boolean inside=ox2/a2+oy2/b2+oz2/c2<1;

					if(inside)
					{
						BlockPos posTemp=pos.add(ox,oy,oz);
//						countInside++;
						if(world.isAirBlock(posTemp))
						{
//							countReplace++;
							world.setBlockState(posTemp, cloud);
						}
					}
				}
			}
		}

//		System.out.println(String.format("内部,替换 = %d,%d", countInside, countReplace));

		return true;
	}
}
