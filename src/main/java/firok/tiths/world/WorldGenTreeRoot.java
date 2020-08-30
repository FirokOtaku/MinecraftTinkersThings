package firok.tiths.world;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome.TempCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 树根矿物生成器
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public class WorldGenTreeRoot extends AbstractChunkGen
{
	protected int depthBase;
	protected int depthExtra;

	@Override
	public void init(Map<String, String> raw, IBlockState... states)
	{
		super.init(raw, states);

		String strDepthBase=raw.get("depth_base"), strDepthExtra=raw.get("depth_extra");
		depthBase = strDepthBase!=null ? parseInt("depth_base",strDepthBase) : 3;
		depthExtra = strDepthExtra!=null ? parseInt("depth_extra",strDepthExtra) : 3;
		if(depthBase<1) errorLower("depth_base",depthBase,1);
		if(depthExtra<1) errorLower("depth_extra",depthExtra,1);
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVX, int chunkVZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();
		BlockPos posCenterTop=world.getTopSolidOrLiquidBlock(new BlockPos(posX,0,posZ)); // fixme 这个很可能需要包装一层 用来限制跨区块生成

		TempCategory tempCate=world.getBiome(posCenterTop).getTempCategory();
		if(tempCate== TempCategory.COLD || tempCate==TempCategory.OCEAN) return ret; // 不在寒冷和海洋生物群系生成

//		    world.setBlockState(posCenterTop,Blocks.GOLD_BLOCK.getDefaultState()); // 测试用的金块

		IBlockState stateOre=getMainState();

		for(int ox=-2;ox<=2;ox++)
		{
			for(int oz=-2;oz<=2;oz++)
			{
				ret.addAll(tryGen(world,posCenterTop.getX()+ox,posCenterTop.getY(),posCenterTop.getZ()+oz,rand,stateOre,chunkVX,chunkVZ));
			}
		}

		return ret;
	}

	private List<BlockPos> tryGen(World world,int posX,int posY,int posZ,Random rand,IBlockState stateOre, int chunkVX,int chunkVZ)
	{
		List<BlockPos> ret=new ArrayList<>();
		int times=0;

		boolean canGen=false; // 找到第一块木头之后才开始生成

		final int depth=depthBase+rand.nextInt(depthExtra);

		while(times<depth && posY>1)
		{
			BlockPos pos2get=new BlockPos(posX,posY,posZ);
			Block block= AbstractChunkGen.getState(world,pos2get,chunkVX,chunkVZ).getBlock();

			if(block instanceof BlockOre) return ret; // 已经生成过一次 不再继续生成

			boolean isWood=block.isWood(world,pos2get);
			if(isWood)
			{
				canGen=true; // 找到第一块木头
			}
			else
			{
				if(canGen && (block== Blocks.DIRT || block==Blocks.STONE || block==Blocks.COBBLESTONE))
				{
					AbstractChunkGen.setState(world,pos2get,stateOre,chunkVX,chunkVZ);
					ret.add(pos2get);
				}
			}

			posY--;
			times++;
		}

		return ret;
	}
}