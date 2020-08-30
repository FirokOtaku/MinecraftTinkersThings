package firok.tiths.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public class WorldGenMinableBedrock extends AbstractChunkGen
{
//	private static IBlockState stateBrokenBedrock=firok.tiths.common.Blocks.oreBrokenBedrock.getDefaultState();

	/**
	 * 生成多少矿物
	 */
	protected int size;

	/**
	 * 单次成功概率
	 */
	protected float rate;

	@Override
	public void init(Map<String, String> raw, IBlockState... states)
	{
		super.init(raw, states);

		String strSize=raw.get("size"), strRate=raw.get("rate");
		if(strSize==null) errorMissingKey("size");
		this.size=parseInt("size",strSize);
		this.rate=strRate!=null?parseFloat("rate",strRate,0,1):0.8f;
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVX, int chunkVZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();
		IBlockState stateOre=getMainState();
		// 小小地应用一下动态规划算法
		int[][] ys=new int[][]{ // 记录偏移坐标的位置多高是可以生成矿物的y坐标 (其实是上次循环尝试生成的y坐标)(所以用这个坐标生成矿物的时候需要自增1)
				{-1,-1,-1},
				{-1,-1,-1},
				{-1,-1,-1},
		}; // -1 代表还未寻找 -2表示已经不能生成了

		int countMax=this.size;

		FOR_TIMES_GEN_ORE:for(int time=0;time<countMax;time++) // 每次生成指定块矿物
		{
			if(!canTrigger(rand,rate)) continue FOR_TIMES_GEN_ORE;

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
					Block block= AbstractChunkGen.getState(world,posFindBedrockTop,chunkVX,chunkVZ).getBlock(); // 获取目标坐标方块类型

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
					AbstractChunkGen.setState(world,posTryGenOre,stateOre,chunkVX,chunkVZ); // 直接生成矿物
					ret.add(posTryGenOre);
				}
				else // 这个坐标是上次生成矿物的时候计算的 上面可能已经不是可以用于生成矿物的石头了
				{
					Block block= AbstractChunkGen.getState(world,posTryGenOre,chunkVX,chunkVZ).getBlock();
					if(block==Blocks.STONE || block==Blocks.COBBLESTONE) // 判断方块类型是不是石头
					{
						AbstractChunkGen.setState(world,posTryGenOre,stateOre,chunkVX,chunkVZ); // 可以生成矿物
						ret.add(posTryGenOre);
					}
					else // 不是石头的话
					{
						ys[ox][oz]=-2; // 说明这个偏移坐标已经不能生成矿物了
					}
				}

			}
		}

//		System.out.printf("gen at [%d,%d,%d] with [%d]\n",posX,posY,posZ,ret.size());

		return ret;
	}
}
