package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import slimeknights.tconstruct.shared.block.GlowBlock;

import java.util.Random;

public class BlockIcelitGlow extends GlowBlock
{
	public BlockIcelitGlow()
	{
		super(Block.Properties.create(Material.AIR).sound(SoundType.CLOTH).setLightLevel(state -> 6).tickRandomly());

//		this.setDefaultState(this.getState().withProperty(FACING, Direction.DOWN));
	}

	@Override
	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		if(worldIn.getBlockState(pos).getBlock()==this)
		{
			worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}


	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		return ItemStack.EMPTY;
	}
}
