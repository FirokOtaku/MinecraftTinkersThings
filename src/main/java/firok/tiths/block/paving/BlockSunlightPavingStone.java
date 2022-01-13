package firok.tiths.block.paving;

import firok.tiths.util.Predicates;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;

/**
 * 日光铺路石
 * 晚上允许实体通过
 */
public class BlockSunlightPavingStone extends BlockPavingStoneBase
{
	@Override
	protected boolean canEntityPass(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return context != null && Predicates.isWorldNightTime(context.getEntity());
	}
}
