package firok.tiths.block.paving;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneTorchBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

/**
 * @see net.minecraft.block.RedstoneLampBlock
 */
public abstract class BlockSwitchablePavingStoneBase extends BlockPavingStoneBase
{
	public static final BooleanProperty LIT = RedstoneTorchBlock.LIT;
	public BlockSwitchablePavingStoneBase(AbstractBlock.Properties properties)
	{
		super(properties);
		this.setDefaultState(this.getDefaultState().with(LIT, false));
	}

	public BlockState getStateForPlacement(BlockItemUseContext context)
	{
		return this.getDefaultState().with(LIT, context.getWorld().isBlockPowered(context.getPos()) );
	}

	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		if (!worldIn.isRemote)
		{
			boolean isPoweredOld = state.get(LIT);
			if (isPoweredOld != worldIn.isBlockPowered(pos))
			{
				if (isPoweredOld)
					worldIn.getPendingBlockTicks().scheduleTick(pos, this, 4);
				else
					worldIn.setBlockState(pos, state.cycleValue(LIT), 2);
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
