package firok.tiths.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.block.BlockGlow;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 灯明 光点
 */
public class BlockIcelitGlow extends BlockGlow
{
	public BlockIcelitGlow() {
		this.setLightLevel(0.45F);
		this.setTickRandomly(false);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.DOWN));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		worldIn.scheduleUpdate(pos,this,100);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if(worldIn.getBlockState(pos).getBlock()==this)
		{
			worldIn.setBlockToAir(pos);
		}
	}

	@Override
	public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
		// if unavailable, just return nothing, Minecraft will just not do anything on pick block
		return ItemStack.EMPTY;
	}
}
