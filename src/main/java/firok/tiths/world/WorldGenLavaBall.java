package firok.tiths.world;

import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 岩浆球矿
 * @author Firok
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public class WorldGenLavaBall extends AbstractChunkGen
{
	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();

		boolean hasSomeNoneStone=false; // 找找这个区域里有没有不是石头的东西 有的话就不生成了
		FOR_X:for(int ox=-3;ox<=3;ox++)
		{
			FOR_Y:for(int oy=-3;oy<=3;oy++)
			{
				FOR_Z:for(int oz=-3;oz<=3;oz++)
				{
					int distance=(int)( MathHelper.sqrt(ox*ox)+MathHelper.sqrt(oy*oy)+MathHelper.sqrt(oz*oz) );

					if(distance>4) continue FOR_Z;

					BlockPos posTemp=new BlockPos(posX+ox,posY+oy,posZ+oz);

					IBlockState stateTemp= AbstractChunkGen.getState(world,posTemp,chunkVertexX,chunkVertexZ);

					if(!Predicates.isStone(stateTemp) && stateTemp.getBlock() != Blocks.NETHERRACK)
					{
						hasSomeNoneStone=true;
						break FOR_X;
					}
				}
			}
		}

		if(hasSomeNoneStone) return ret; // 有不是石头的东西

		IBlockState blockCentral= getMainState(); // 中间是矿
		IBlockState blockSurrounding= Blocks.LAVA.getDefaultState(); // 岩浆球
		IBlockState blockRandom= Blocks.OBSIDIAN.getDefaultState(); // 夹杂着黑曜石

		FOR_X:for(int ox=-3;ox<=3;ox++)
		{
			FOR_Y:for(int oy=-3;oy<=3;oy++)
			{
				FOR_Z:for(int oz=-3;oz<=3;oz++)
				{
					int distance=(int)( MathHelper.sqrt(ox*ox)+MathHelper.sqrt(oy*oy)+MathHelper.sqrt(oz*oz) );

					if(distance>4) continue FOR_Z;

					boolean isCentral=distance<=1;

					BlockPos posTemp=new BlockPos(posX+ox,posY+oy,posZ+oz);
					AbstractChunkGen.setState(world,posTemp,
							isCentral?
									blockCentral:
									(canTrigger(rand,0.185f)?
											blockRandom:
											blockSurrounding),
							chunkVertexX,chunkVertexZ
					);
					ret.add(posTemp);
				}
			}
		}

		return ret;
	}
}
