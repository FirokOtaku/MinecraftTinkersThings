package firok.tiths.world;

import firok.tiths.common.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Random;

public class GenTreeHura
{
	final static int maxHeight=8;
	final static int minHeight=4;
	final static IBlockState stateLog= Blocks.blockLogHura.getDefaultState();
	final static IBlockState stateLeaf= Blocks.blockLeafHura.getDefaultState();
	public static boolean generate(World world, Random rand, BlockPos pos)
	{
		int posX=pos.getX(),posY=pos.getY(),posZ=pos.getZ();
		int height=MathHelper.getInt(rand,minHeight,maxHeight);

		world.setBlockState(new BlockPos(posX,posY+height,posZ),stateLeaf);
		for(int i=height-1;i>=0;i--)
		{
			BlockPos posLog=new BlockPos(posX,posY+i,posZ);
			world.setBlockState(posLog,stateLog);
			int dir=i%4;
			switch(dir)
			{
				case 0: world.setBlockState(posLog.north(),stateLeaf); break;
				case 1: world.setBlockState(posLog.west(),stateLeaf); break;
				case 2: world.setBlockState(posLog.south(),stateLeaf); break;
				case 3: world.setBlockState(posLog.east(),stateLeaf); break;
			}
		}

		return true;
	}
}
