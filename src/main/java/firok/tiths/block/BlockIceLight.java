package firok.tiths.block;

import firok.tiths.util.Actions;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 冻灯
 */
public class BlockIceLight extends BlockCompressed
{
	public BlockIceLight()
	{
		super(Material.IRON);
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		Actions.CauseLiquidFroze(world,pos,4,0.1f);
	}
}
