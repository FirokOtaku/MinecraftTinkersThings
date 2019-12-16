package firok.tiths.world;

import com.google.common.base.Predicate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.TinkersThings.tell;

public class WorldGenMinableRandom
{
	private final IBlockState oreBlock;
	private final int min,max;
	private final short minY,maxY;
	private final Predicate<IBlockState> predicate;

	public WorldGenMinableRandom(IBlockState state, int min, int max, short minY, short maxY, Predicate<IBlockState> predicate) {
		if(max<=0 || min>max || maxY<=0 || minY>maxY) throw new RuntimeException("cannot create a generator minable with props: min="+min+", max="+max+", minY="+minY+", maxY="+maxY);
		this.oreBlock = state;
		this.min=min;
		this.max=max;
		this.minY=minY;
		this.maxY=maxY;
		this.predicate = predicate;
	}

	public boolean generate(World world, Random rand, int posX,int posZ) {
		int numberOfBlocks=min+ (int)((max-min)*rand.nextFloat());
		short posY=(short)(minY + (maxY-minY)*rand.nextFloat() );
		int countGenerated=0;

//		tell(String.format("starting gen at [%d,%d,%d] with [%d]",posX,posY,posZ,numberOfBlocks));

		float factor = rand.nextFloat() * 3.1415927F;
		double rangeMinX = ((float)(posX + 8) + MathHelper.sin(factor) * (float)numberOfBlocks / 8.0F);
		double rangeMaxX = ((float)(posX + 8) - MathHelper.sin(factor) * (float)numberOfBlocks / 8.0F);
		double rangeMinZ = ((float)(posZ + 8) + MathHelper.cos(factor) * (float)numberOfBlocks / 8.0F);
		double rangeMaxZ = ((float)(posZ + 8) - MathHelper.cos(factor) * (float)numberOfBlocks / 8.0F);
		double rangeMinY = (posY + rand.nextInt(3) - 2);
		double rangeMaxY = (posY + rand.nextInt(3) - 2);


		for(int i = 0; i < numberOfBlocks; ++i) {
			float factorPos = (float)i / (float)numberOfBlocks;
			double randX = rangeMinX + (rangeMaxX - rangeMinX) * (double)factorPos;
			double randY = rangeMinY + (rangeMaxY - rangeMinY) * (double)factorPos;
			double randZ = rangeMinZ + (rangeMaxZ - rangeMinZ) * (double)factorPos;
			double d9 = rand.nextDouble() * (double)numberOfBlocks / 16.0D;
			double d10 = (double)(MathHelper.sin(3.1415927F * factorPos) + 1.0F) * d9 + 1.0D;
			double d11 = (double)(MathHelper.sin(3.1415927F * factorPos) + 1.0F) * d9 + 1.0D;
			int j = MathHelper.floor(randX - d10 / 2.0D);
			int k = MathHelper.floor(randY - d11 / 2.0D);
			int l = MathHelper.floor(randZ - d10 / 2.0D);
			int i1 = MathHelper.floor(randX + d10 / 2.0D);
			int j1 = MathHelper.floor(randY + d11 / 2.0D);
			int k1 = MathHelper.floor(randZ + d10 / 2.0D);

			for(int l1 = j; l1 <= i1; ++l1) {
				double d12 = ((double)l1 + 0.5D - randX) / (d10 / 2.0D);
				if (d12 * d12 < 1.0D) {
					for(int i2 = k; i2 <= j1; ++i2) {
						double d13 = ((double)i2 + 0.5D - randY) / (d11 / 2.0D);
						if (d12 * d12 + d13 * d13 < 1.0D) {
							for(int j2 = l; j2 <= k1; ++j2) {
								double d14 = ((double)j2 + 0.5D - randZ) / (d10 / 2.0D);
								if (d12 * d12 + d13 * d13 + d14 * d14 < 1.0D) {
									BlockPos blockpos = new BlockPos(l1, i2, j2);
									IBlockState state = world.getBlockState(blockpos);
									if (state.getBlock().isReplaceableOreGen(state, world, blockpos, this.predicate)) {
										world.setBlockState(blockpos, this.oreBlock, 2);
										countGenerated++;
//										tell(String.format("gen block[%s] at [%d,%d,%d]",state,blockpos.getX(),blockpos.getY(),blockpos.getZ()));
									}
								}
							}
						}
					}
				}
			}
//			if(countGenerated>0) tell(String.format("gen total:[%d]",countGenerated));
			boolean enableDebugGen= true; // 测试用的 在所有矿物地表生成一根黑曜石柱子
			if(countGenerated>0&& enableDebugGen)
			{
				int x=(int)(rangeMaxX+rangeMinX)/2,z=(int)(rangeMaxZ+rangeMinZ)/2;
				BlockPos posTop=world.getTopSolidOrLiquidBlock(new BlockPos(x,0,z));
				for(int temp=1;temp<=5;temp++)
				{
					world.setBlockState(posTop.up(temp), Blocks.OBSIDIAN.getDefaultState());
				}
			}
		}

		return true;
	}
}
