package firok.tiths.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * @Description
 * @Author gyy
 * @Date 2020/01/20 19:53
 */
public class WorldGenMinableBedrock implements IChunkGen
{
	public IBlockState stateOre;
	public int timesChunk;
	public float rateChunk;
	public float ratePerTime;

	public int countMax;

	public WorldGenMinableBedrock(IBlockState stateOre, int timesChunk, float rateChunk, float ratePerTime, int countMax)
	{
		this.stateOre = stateOre;
		this.timesChunk = timesChunk;
		this.rateChunk = rateChunk;
		this.ratePerTime = ratePerTime;
		this.countMax = countMax;
	}

	@Override
	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
	{
		if(!canTrigger(rand,rateChunk)) return true; // 跳过区块

		FOR_TIMES_CHUNK_TRY:for(int timeChunk=0;timeChunk<timesChunk;timeChunk++) // 区块内生成指定次数
		{
			// 把区块顶点x z坐标计算成真实x z坐标
			final int posX=chunkX+4+rand.nextInt(12);
			final int posZ=chunkZ+4+rand.nextInt(12);

			// 小小地应用一下动态规划算法
			int[][] ys=new int[][]{ // 记录偏移坐标的位置多高是可以生成矿物的y坐标 (其实是上次循环尝试生成的y坐标)(所以用这个坐标生成矿物的时候需要自增1)
						{-1,-1,-1},
						{-1,-1,-1},
						{-1,-1,-1},
					}; // -1 代表还未寻找 -2表示已经不能生成了

			FOR_TIMES_GEN_ORE:for(int time=0;time<countMax;time++) // 每次生成指定块矿物
			{
				if(!canTrigger(rand,ratePerTime)) continue FOR_TIMES_GEN_ORE;

				int ox=rand.nextInt(3),oz=rand.nextInt(3); // 随机生成对本次生成中心位置的偏移x z坐标
				final int tempTargetX=posX-1+ox,tempTargetZ=posZ-1+oz; // 本次要尝试生成矿物的真实x z坐标
				boolean isThisTimeFound=false; // 本次要进行生成的y坐标是不是本次循环内找到的 如果是的话可以直接生成矿物

				if(ys[ox][oz]==-2) // 指定偏移坐标不能再生成了
				{
					continue FOR_TIMES_GEN_ORE;
				}
				else if(ys[ox][oz]==-1) // 指定偏移坐标还未生成过
				{
					FOR_FIND_Y:for(int tempY=1;tempY<6;tempY++) // 开始从下往上寻找一个能生成矿物的y坐标
					{
						BlockPos posFindBedrockTop=new BlockPos(tempTargetX,tempY,tempTargetZ);
						Block block=world.getBlockState(posFindBedrockTop).getBlock(); // 获取目标坐标方块类型

						if(block==Blocks.BEDROCK) // 找到一块基岩
						{
							continue FOR_FIND_Y; // 没啥需要干的 继续向上找
						}
						else if(block!=Blocks.STONE&&block!=Blocks.COBBLESTONE) // 找到一块不是石头的东西
						{
							ys[ox][oz]=-2;
							continue FOR_TIMES_GEN_ORE; // 这个坐标不能再生成矿物了
						}
						else // 找到一块石头
						{
							ys[ox][oz]=tempY-1; // 可以在这里生成 记录这个位置下方一格的坐标
							isThisTimeFound=true;
							break FOR_FIND_Y; // 已经找到可以生成的y坐标
						}
					}
				}

				if(ys[ox][oz]>0) // 真正开始生成矿物
				{
					++ys[ox][oz];
					BlockPos posTryGenOre=new BlockPos(tempTargetX,ys[ox][oz],tempTargetZ);
					if(isThisTimeFound) // 如果这个y坐标是这一次找到的 那说明这个y坐标肯定是石头 不需要额外判断了
					{
						world.setBlockState(posTryGenOre,stateOre); // 直接生成矿物
					}
					else // 这个坐标是上次生成矿物的时候计算的 上面可能已经不是可以用于生成矿物的石头了
					{
						Block block=world.getBlockState(posTryGenOre).getBlock();
						if(block==Blocks.STONE || block==Blocks.COBBLESTONE) // 判断方块类型是不是石头
						{
							world.setBlockState(posTryGenOre,stateOre); // 可以生成矿物
						}
						else // 不是石头的话
						{
							ys[ox][oz]=-2; // 说明这个偏移坐标已经不能生成矿物了
						}
					}

				}
			}
		}

		return true;
	}
}
