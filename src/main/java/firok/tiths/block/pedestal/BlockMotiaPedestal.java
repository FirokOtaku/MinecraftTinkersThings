package firok.tiths.block.pedestal;

import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * made with motia crystal. <br>
 * would "take photo" of the stack at player's main hand. <br>
 * it's display-use only. no need one stack after photo taken.
 */
public class BlockMotiaPedestal extends BlockPedestalBase
{
	public BlockMotiaPedestal()
	{
		super(Properties.create(Material.GOURD)
				.doesNotBlockMovement()
				.hardnessAndResistance(10,1));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		if(player.isSneaking())
		{
			ItemStack stackHeld = player.getHeldItemMainhand();
			TilePedestalBase te = getTilePedestalAt(world, pos);
			te.setStackPedestal(stackHeld);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TilePedestalBase createTileEntity(BlockState state, IBlockReader world)
	{
		return new TilePedestalBase();
	}
}
