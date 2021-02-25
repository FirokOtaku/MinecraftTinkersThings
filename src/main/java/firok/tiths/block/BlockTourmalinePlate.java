package firok.tiths.block;

import net.minecraft.block.BlockBasePressurePlate;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 电气石压力板 当实体坠落到表面的时候激发红石信号
 */
//public class BlockTourmalinePlate extends BlockPressurePlate
//{
//	public BlockTourmalinePlate(Material materialIn)
//	{
//		super(Material.ROCK, Sensitivity.EVERYTHING);
//	}
//
//	/**
//	 * Called When an Entity Collided with the Block
//	 */
//	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
//	{ }
//
//	void trigger(World world,BlockPos pos)
//	{
//		;
//	}
//	void detrigger(World world,BlockPos pos)
//	{
//		;
//	}
//
//	/**
//	 * Updates the pressure plate when stepped on
//	 */
//	protected void updateState(World worldIn, BlockPos pos, IBlockState state, int oldRedstoneStrength)
//	{
//		int i = this.computeRedstoneStrength(worldIn, pos);
//		boolean flag = oldRedstoneStrength > 0;
//		boolean flag1 = i > 0;
//
//		if (oldRedstoneStrength != i)
//		{
//			state = this.setRedstoneStrength(state, i);
//			worldIn.setBlockState(pos, state, 2);
//			this.updateNeighbors(worldIn, pos);
//			worldIn.markBlockRangeForRenderUpdate(pos, pos);
//		}
//
//		if (!flag1 && flag)
//		{
//			this.playClickOffSound(worldIn, pos);
//		}
//		else if (flag1 && !flag)
//		{
//			this.playClickOnSound(worldIn, pos);
//		}
//
//		if (flag1)
//		{
//			worldIn.scheduleUpdate(new BlockPos(pos), this, this.tickRate(worldIn));
//		}
//	}
//
//	@Override
//	public void onLanded(World worldIn, Entity entityIn)
//	{
//		super.onLanded(worldIn, entityIn);
//		if (!worldIn.isRemote)
//		{
//			int i = this.getRedstoneStrength(state);
//
//			if (i == 0)
//			{
//				this.updateState(worldIn, pos, state, i);
//			}
//		}
//	}
//}
