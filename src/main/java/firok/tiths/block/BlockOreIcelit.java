package firok.tiths.block;

import firok.tiths.common.Items;
import firok.tiths.util.Actions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * 冰明玉矿
 */
public class BlockOreIcelit extends BlockOre
{
	public BlockOreIcelit()
	{
		super(Items.icelit, 1, 1, 1, 3,5);
		this.setLightLevel(0.1f);
		this.setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		Actions.CauseLiquidFroze(world,pos,4,0.05f);
	}
}
