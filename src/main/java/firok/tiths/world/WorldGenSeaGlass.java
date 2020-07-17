package firok.tiths.world;

import firok.tiths.block.BlockSeaGrass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.isWater;

/**
 * @author Firok
 */
public class WorldGenSeaGlass extends BaseChunkGen
{
	public WorldGenSeaGlass(Info defaultInfo,String specialKey)
	{
		super(defaultInfo, specialKey);
	}

//	private static IBlockState stateBrokenBedrock=firok.tiths.common.Blocks.oreBrokenBedrock.getDefaultState();

	@Override
	public List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>(genSeaGlassAt(world, posX, posY, posZ, rand));

		if(ret.size()>0)
		{
			System.out.printf("gen sea glass at {%s}\n",ret.get(0));
		}

		return ret;
	}

	public static List<BlockPos> genSeaGlassAt(World world,int posX,int posY,int posZ,Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();

		final BlockPos posCenter=new BlockPos(posX,posY,posZ);
		final BlockPos posTopBlock=world.getTopSolidOrLiquidBlock(posCenter);

		int startY=-1;
		IBlockState stateTempCheckTop=world.getBlockState(posTopBlock);
		if(!isWater(stateTempCheckTop)) return ret; // 如果顶部直接就不是水 那直接返回
		// 顶部是水 向下搜寻不是水的方块
		int tempCheckY;
		for(tempCheckY=posTopBlock.getY()-5;tempCheckY>0;tempCheckY-=5)
		{
			IBlockState stateTempCheck=world.getBlockState(new BlockPos(posX,tempCheckY,posZ));
			if( stateTempCheck.getMaterial() != Material.WATER || stateTempCheck.getBlock() != Blocks.WATER) break;
			// 只要break了说明现在的tempCheckY已经不是水了
		}
		// 向上寻找是水的方块
		for(int tempCheckOffsetY=1;tempCheckOffsetY<=5;tempCheckOffsetY++)
		{
			IBlockState stateTempCheck=world.getBlockState(new BlockPos(posX,tempCheckY+tempCheckOffsetY,posZ));
			if(isWater(stateTempCheck))
			{
				startY=tempCheckOffsetY+tempCheckY;
				break;
			}
		}

		if(startY<=0) return ret;

		BlockSeaGrass blockSeaGrass=firok.tiths.common.Blocks.blockSeaGrass;

		BlockPos posTryGen=new BlockPos(posX,startY,posZ);
		if(blockSeaGrass.canPlaceBlockAt(world,posTryGen))
		{
			world.setBlockState(posTryGen,blockSeaGrass.getDefaultState());

			ret.add(posTryGen);
		}

		return ret;
	}
}