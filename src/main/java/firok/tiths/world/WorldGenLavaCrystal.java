package firok.tiths.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Predicates.isStone;

// 生成岩浆水晶
public class WorldGenLavaCrystal implements IChunkGen
{
	@Override
	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
	{
		if(!canTrigger(rand,0.15f)) return true;
		int posX=chunkX+5+rand.nextInt(11),posY=rand.nextInt(55)+10,posZ=chunkZ+5+rand.nextInt(11);
		return generate(world, new BlockPos(posX,posY,posZ));
	}

	public static boolean generate(World world, BlockPos pos)
	{
		Random rand=world.rand;

		boolean hasSomeNoneStone=false; // 找找这个区域里有没有不是石头的东西 有的话就不生成了
		FOR_X:for(int ox=-3;ox<=3;ox++)
		{
			FOR_Y:for(int oy=-3;oy<=3;oy++)
			{
				FOR_Z:for(int oz=-3;oz<=3;oz++)
				{
					int distance=(int)( MathHelper.sqrt(ox*ox)+MathHelper.sqrt(oy*oy)+MathHelper.sqrt(oz*oz) );

					if(distance>4) continue FOR_Z;

					BlockPos posTemp=pos.add(ox,oy,oz);

					if(!isStone(world.getBlockState(posTemp)))
					{
						hasSomeNoneStone=true;
						break FOR_X;
					}
				}
			}
		}

		if(hasSomeNoneStone) return true; // 有不是石头的东西

		IBlockState blockCentral= firok.tiths.common.Blocks.oreLavaCrystal.getDefaultState(); // 中间是矿
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

					BlockPos posTemp=pos.add(ox,oy,oz);
					world.setBlockState(posTemp,
							isCentral?
									blockCentral:
									(canTrigger(rand,0.185f)?
											blockRandom:
											blockSurrounding)
					);
				}
			}
		}
		return true;
	}
}
