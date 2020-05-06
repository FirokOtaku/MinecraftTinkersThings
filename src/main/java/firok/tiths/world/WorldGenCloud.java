package firok.tiths.world;

import firok.tiths.common.Blocks;
import firok.tiths.common.ConfigJson;
import firok.tiths.util.Values;
import firok.tiths.util.conf.OreGenInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

// 世界生成-云
public class WorldGenCloud implements IChunkGen
{
	protected int[] dimsAllowed;
	protected float rateChunk;
	protected int minY, interval;
	public WorldGenCloud()
	{
		OreGenInfo info=ConfigJson.getOre("CLOUD");
		if(info==null)
		{
			this.dimsAllowed=new int[]{ 0, };
			this.rateChunk=0.06f;
			this.minY=150;
			this.interval =180;
		}
		else
		{
			this.dimsAllowed= info.dims!=null?Values.arr(info.dims):new int[]{ 0, };
			this.rateChunk=info.timeRate!=null?info.timeRate:0.06f;
			this.minY=info.minY!=null?info.minY:150;
			this.interval=info.maxY!=null?info.maxY-this.minY:30;
			if(this.interval<=0) this.interval=1;
		}
	}
//	public WorldGenCloud(int[] dimsAllowed)
//	{
//		this.dimsAllowed =dimsAllowed;
//	}

	@Override
	public boolean canGenAtDim(int targetDimId)
	{
		for(int temp:dimsAllowed)
		{
			if(temp==targetDimId)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
	{
		if(!canTrigger(rand,rateChunk)) return true;

		int posX=chunkX+5+rand.nextInt(11),posY= rand.nextInt(interval) + minY,posZ= chunkZ + 5 + rand.nextInt(11);

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
