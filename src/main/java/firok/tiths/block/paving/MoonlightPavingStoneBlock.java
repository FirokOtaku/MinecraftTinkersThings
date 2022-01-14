package firok.tiths.block.paving;

import firok.tiths.util.Predicates;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;

/**
 * 月光铺路石
 * 白天允许实体通过
 */
public class MoonlightPavingStoneBlock extends PavingStoneBlockBase
{
	@Override
	public boolean canEntityPassRuneBarrier(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return context != null && Predicates.isWorldDayTime(context.getEntity());
	}

	@Override
	public int provideRuneBarrierTopHeightMax()
	{
		return 2;
	}
}
