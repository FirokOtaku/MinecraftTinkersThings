package firok.tiths.world;


import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;

import static net.minecraftforge.common.util.Constants.BlockFlags.SEND_TO_CLIENTS;

/**
 * 普通矿物生成器
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public class WorldGenMinable extends AbstractChunkGen
{
	/**
	 * 生成高度和生成数量
	 */
	protected int minY,maxY,size;
	/**
	 * 替换检查器, 用于检查是否可以替换指定方块
	 */
	protected Predicate<IBlockState> selector;

	@Override
	public void init(Map<String,String> raw,IBlockState...states)
	{
		super.init(raw,states);

		boolean flagSize=false,flagMinY=false,flagMaxY=false,flagSelector=false;

		for(Map.Entry<String,String> entry:raw.entrySet())
		{
			String k=entry.getKey(), v=entry.getValue();

			switch (k)
			{
				case "min_y":
					flagMinY=checkParameter(flagMinY,k);
					this.minY=parseInt(k,v);
					break;

				case "max_y":
					flagMaxY=checkParameter(flagMaxY,k);
					this.maxY=parseInt(k,v);
					break;

				case "size":
					flagSize=checkParameter(flagSize,k);
					this.size=parseInt(k,v);
					break;

				case "selector":
					flagSelector=checkParameter(flagSelector,k);
					this.selector= Predicates.getPredicateIBlockState(v,Predicates::isStone);
					break;

//				default: errorInvalidKVP(k); break;
			}
		}

		if(!flagSize) errorMissingKey("size");
		if(!flagMaxY) errorMissingKey("max_y");
		if(!flagMinY) errorMissingKey("min_y");
		if(!flagSelector) this.selector=Predicates::isStone;
		if(size<1) errorLower("size",size,1);
		if(maxY<minY) errorLower("max_y",maxY,minY);
	}

	@Override
	public BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand, int interval)
	{
		final int posX=chunkVertexX+interval+rand.nextInt(16-interval);

		final int temp=maxY>=minY?
				1+maxY-minY:
				world.getHeight()-minY;

		final int posY=minY + rand.nextInt(temp);
		final int posZ=chunkVertexZ+interval+rand.nextInt(16-interval);

		return new BlockPos(posX,posY,posZ);
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();

		final float radius = rand.nextFloat() * 3.1415927F; // 半径

		// 使用真实坐标
		float d0 = ((float)(posX + 8) + MathHelper.sin(radius) * (float)size / 8.0F);
		float d1 = ((float)(posX + 8) - MathHelper.sin(radius) * (float)size / 8.0F);
		float d2 = ((float)(posZ + 8) + MathHelper.cos(radius) * (float)size / 8.0F);
		float d3 = ((float)(posZ + 8) - MathHelper.cos(radius) * (float)size / 8.0F);
		float d4 = (posY + rand.nextInt(3) - 2);
		float d5 = (posY + rand.nextInt(3) - 2);

		IBlockState stateOre=getMainState();
		if(stateOre==null) return ret;

		// 下面的都是原版的代码
		for(int iBlock = 0; iBlock < size; ++iBlock)
		{
			float f1 = (float)iBlock / (float)size;
			double d6 = d0 + (d1 - d0) * (double)f1;
			double d7 = d4 + (d5 - d4) * (double)f1;
			double d8 = d2 + (d3 - d2) * (double)f1;
			double d9 = rand.nextDouble() * (double)size / 16.0D;
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
									IBlockState stateOrigin = AbstractChunkGen.getState(world,blockpos,chunkVertexX,chunkVertexZ);
									if (stateOrigin.getBlock().isReplaceableOreGen(stateOrigin, world, blockpos, selector::test))
									{
										AbstractChunkGen.setState(world, blockpos, stateOre, SEND_TO_CLIENTS,chunkVertexX,chunkVertexZ);
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
