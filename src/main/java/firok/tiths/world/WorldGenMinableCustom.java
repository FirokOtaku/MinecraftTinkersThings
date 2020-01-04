package firok.tiths.world;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public class WorldGenMinableCustom implements IChunkGen
{
	IBlockState stateGen;
	int times;
	int size;
	int maxY,minY;
	float timeRate;
	Predicate<IBlockState> canReplace;
	public WorldGenMinableCustom(IBlockState state, int times, float timeRate, int size, int minY, int maxY, Predicate<IBlockState> predicate)
	{
		super();
		this.stateGen=state;
		this.times=times;
		this.timeRate=timeRate;
		this.size=size;
		this.minY=minY;
		this.maxY=maxY;
		this.canReplace=predicate;
	}

	@Override
	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
	{
		int numberOfBlocks=size;

		for(int time=0;time<times;time++)
		{
			if(timeRate<=0 || timeRate>1 || !canTrigger(rand,timeRate)) continue;

			// 把区块顶点计算成真实坐标
			final int posX=chunkX+4+rand.nextInt(12);
//			final int posY=16+rand.nextInt(16); // fixme 取最大最小值 minY maxY
			final int posY=minY+ ((maxY>minY)?(int)(rand.nextFloat()*(maxY-minY)):0);
			final int posZ=chunkZ+4+rand.nextInt(12);

			// 生成测试用的黑曜石
			world.setBlockState(world.getTopSolidOrLiquidBlock(new BlockPos(posX,posY,posZ)),Blocks.OBSIDIAN.getDefaultState());

			final float radius = rand.nextFloat() * 3.1415927F; // 半径

			// 使用真实坐标
			float d0 = ((float)(posX + 8) + MathHelper.sin(radius) * (float)numberOfBlocks / 8.0F);
			float d1 = ((float)(posX + 8) - MathHelper.sin(radius) * (float)numberOfBlocks / 8.0F);
			float d2 = ((float)(posZ + 8) + MathHelper.cos(radius) * (float)numberOfBlocks / 8.0F);
			float d3 = ((float)(posZ + 8) - MathHelper.cos(radius) * (float)numberOfBlocks / 8.0F);
			float d4 = (posY + rand.nextInt(3) - 2);
			float d5 = (posY + rand.nextInt(3) - 2);

			// 下面的都是原版的代码
			for(int iBlock = 0; iBlock < numberOfBlocks; ++iBlock)
			{
				float f1 = (float)iBlock / (float)numberOfBlocks;
				double d6 = d0 + (d1 - d0) * (double)f1;
				double d7 = d4 + (d5 - d4) * (double)f1;
				double d8 = d2 + (d3 - d2) * (double)f1;
				double d9 = rand.nextDouble() * (double)numberOfBlocks / 16.0D;
				double d10 = (double)(MathHelper.sin(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
				double d11 = (double)(MathHelper.sin(3.1415927F * f1) + 1.0F) * d9 + 1.0D;
				int j = MathHelper.floor(d6 - d10 / 2.0D);
				int k = MathHelper.floor(d7 - d11 / 2.0D);
				int l = MathHelper.floor(d8 - d10 / 2.0D);
				int i1 = MathHelper.floor(d6 + d10 / 2.0D);
				int j1 = MathHelper.floor(d7 + d11 / 2.0D);
				int k1 = MathHelper.floor(d8 + d10 / 2.0D);

				for(int l1 = j; l1 <= i1; ++l1)
				{
					double d12 = ((double)l1 + 0.5D - d6) / (d10 / 2.0D);
					if (d12 * d12 < 1.0D)
					{
						for(int i2 = k; i2 <= j1; ++i2)
						{
							double d13 = ((double)i2 + 0.5D - d7) / (d11 / 2.0D);
							if (d12 * d12 + d13 * d13 < 1.0D)
							{
								for(int j2 = l; j2 <= k1; ++j2)
								{
									double d14 = ((double)j2 + 0.5D - d8) / (d10 / 2.0D);
									if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D)
									{
										BlockPos blockpos = new BlockPos(l1, i2, j2);
										IBlockState stateOrigin = world.getBlockState(blockpos);
										if (stateOrigin.getBlock().isReplaceableOreGen(stateOrigin, world, blockpos, canReplace))
										{
											world.setBlockState(blockpos, this.stateGen, 2);
										}
									}
								}
							}
						}
					}
				}
			}
		}

		return true;
	}
}
