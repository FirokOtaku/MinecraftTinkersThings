package firok.tiths.block;

import firok.tiths.common.Blocks;
import firok.tiths.common.Items;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockOreBrumeJade extends BlockOre
{
	public BlockOreBrumeJade()
	{
		new BlockOre(Items.brumeJade, 1, 1, 1, 4, 6);
		setRareOre();
		enableTransparent();
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		Block block=world.getBlockState(pos.offset(side)).getBlock();
//		return block != Blocks.oreBrumeJade && block != this;
		return block != Blocks.blockCloud && block != this;
	}
}
