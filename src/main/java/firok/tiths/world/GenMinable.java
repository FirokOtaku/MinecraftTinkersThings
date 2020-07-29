package firok.tiths.world;


import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static net.minecraftforge.common.util.Constants.BlockFlags.SEND_TO_CLIENTS;

public class GenMinable extends BaseChunkGen
{
	public GenMinable(Info info)
	{
		super(info);
	}
	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();

		int numberOfBlocks=Info.size(info,null,0);

		final float radius = rand.nextFloat() * 3.1415927F; // 半径

		// 使用真实坐标
		float d0 = ((float)(posX + 8) + MathHelper.sin(radius) * (float)numberOfBlocks / 8.0F);
		float d1 = ((float)(posX + 8) - MathHelper.sin(radius) * (float)numberOfBlocks / 8.0F);
		float d2 = ((float)(posZ + 8) + MathHelper.cos(radius) * (float)numberOfBlocks / 8.0F);
		float d3 = ((float)(posZ + 8) - MathHelper.cos(radius) * (float)numberOfBlocks / 8.0F);
		float d4 = (posY + rand.nextInt(3) - 2);
		float d5 = (posY + rand.nextInt(3) - 2);

		IBlockState stateOre=Info.state(info,null,null);
		Predicate<IBlockState> selector=Info.selector(info,null,null);

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
									IBlockState stateOrigin = IChunkGen.getState(world,blockpos,chunkVertexX,chunkVertexZ);
									if (stateOrigin.getBlock().isReplaceableOreGen(stateOrigin, world, blockpos, selector::test))
									{
										IChunkGen.setState(world, blockpos, stateOre, SEND_TO_CLIENTS,chunkVertexX,chunkVertexZ);
										ret.add(blockpos);
									}
								}
							}
						}
					}
				}
			}
		}

		return ret;
	}
}
