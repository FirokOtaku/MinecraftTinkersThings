package firok.tiths.world;

import firok.tiths.common.Blocks;
import firok.tiths.common.ConfigJson;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import firok.tiths.util.conf.OreGenInfo;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

// 世界生成-云
public class WorldGenCloud extends BaseChunkGen
{
	public WorldGenCloud(Info defaultInfo,String specialKey)
	{
		super(Info.build(defaultInfo,ConfigJson.getOre(specialKey)));
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, final int posX, final int posY, final int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		// 一种二次曲面，是椭圆在三维空间的推广。
		// 椭球在xyz-笛卡尔坐标系中的方程是：x2 / a2+y2 / b2+z2 / c2=1。

		List<BlockPos> ret=new ArrayList<>();
		final int rx=3+rand.nextInt(4),ry=1+rand.nextInt(2),rz=3+rand.nextInt(4);
		final float a2=rx*rx,b2=ry*ry,c2=rz*rz;

		IBlockState cloud=Blocks.blockCloud.getDefaultState();
		IBlockState ore=Info.state(info,null,cloud);

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
						BlockPos posTemp=new BlockPos(posX+ox,posY+oy,posZ+oz);
						if(IChunkGen.isAirBlock(world,posTemp,chunkVertexX,chunkVertexZ))
						{
							boolean isOre=canTrigger(rand,0.00225);
							IChunkGen.setState(world, posTemp, isOre?ore:cloud, chunkVertexX,chunkVertexZ);
							ret.add(posTemp);
						}
					}
				}
			}
		}
		return ret;
	}
}
