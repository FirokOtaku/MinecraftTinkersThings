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
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public class WorldGenTreeRoot extends BaseChunkGen
{
	//Blocks.oreTreeRoot.getDefaultState(),4,0.3f)
	public WorldGenTreeRoot(Info defaultInfo,String specialKey)
	{
		super(defaultInfo, specialKey);
	}

	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();
		BlockPos posCenterTop=world.getTopSolidOrLiquidBlock(new BlockPos(posX,0,posZ));

		TempCategory tempCate=world.getBiome(posCenterTop).getTempCategory();
		if(tempCate== TempCategory.COLD || tempCate==TempCategory.OCEAN) return ret; // 不在寒冷和海洋生物群系生成

//		    world.setBlockState(posCenterTop,Blocks.GOLD_BLOCK.getDefaultState()); // 测试用的金块

		IBlockState stateOre=Info.state(info,null,null);

		for(int ox=-2;ox<=2;ox++)
		{
			for(int oz=-2;oz<=2;oz++)
			{
				ret.addAll(tryGen(world,posCenterTop.getX()+ox,posCenterTop.getY(),posCenterTop.getZ()+oz,rand,stateOre));
			}
		}

		return ret;
	}



	private static List<BlockPos> tryGen(World world,int posX,int posY,int posZ,Random rand,IBlockState stateOre)
	{
		List<BlockPos> ret=new ArrayList<>();
		int times=0;

		boolean canGen=false; // 找到第一块木头之后才开始生成

		final int depth=3+rand.nextInt(3);

		while(times<depth && posY>1)
		{
			BlockPos pos2get=new BlockPos(posX,posY,posZ);
			Block block=world.getBlockState(pos2get).getBlock();

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
					world.setBlockState(pos2get,stateOre);
					ret.add(pos2get);
				}
			}

			posY--;
			times++;
		}

		return ret;
	}
}