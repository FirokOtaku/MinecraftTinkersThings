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
import net.minecraft.world.World;

/**
 * this pedestal is something like a single-slot chest. <br>
 * could store one stack of item.
 */
public class BlockTinkerPedestal extends BlockPedestalBase
{
	public BlockTinkerPedestal()
	{
		super(Properties.create(Material.GOURD)
				.doesNotBlockMovement()
				.hardnessAndResistance(10,1));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		TilePedestalBase te = getTilePedestalAt(world, pos);
		ItemStack stackPedestal = te.getStackPedestal();
		ItemStack stackHeld = player.getHeldItemMainhand();

		if(!stackPedestal.isEmpty())
		{
			te.setStackPedestal(ItemStack.EMPTY);
			player.addItemStackToInventory(stackPedestal.copy());
			return ActionResultType.SUCCESS;
		}
		else if(!stackHeld.isEmpty()) // pedestal is empty but player holding something
		{
			te.setStackPedestal(stackHeld);
			player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
			return ActionResultType.SUCCESS;
		}
		else return ActionResultType.PASS;
	}
}
