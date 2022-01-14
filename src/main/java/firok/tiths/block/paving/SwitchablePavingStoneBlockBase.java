package firok.tiths.block.paving;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

/**
 * @see net.minecraft.block.RedstoneLampBlock
 */
public abstract class SwitchablePavingStoneBlockBase extends PavingStoneBlockBase
{
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
	public SwitchablePavingStoneBlockBase(AbstractBlock.Properties properties)
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(LIT, false));
	}

	protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
		builder.add(LIT);
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(LIT, context.getWorld().isBlockPowered(context.getPos()) );
	}

	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		if (!world.isRemote)
		{
			boolean isPoweredOld = state.get(LIT);
			if (isPoweredOld != world.isBlockPowered(pos))
			{
				if (isPoweredOld)
					world.getPendingBlockTicks().scheduleTick(pos, this, 4);
				else
					world.setBlockState(pos, state.cycleValue(LIT), 2);
			}
		}
	}

	public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
	{
		if (state.get(LIT) && !worldIn.isBlockPowered(pos))
		{
			worldIn.setBlockState(pos, state.cycleValue(LIT), 2);
		}
	}
}
