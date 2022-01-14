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
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

/**
 * this pedestal is something like a single-slot chest. <br>
 * could store one stack of item.
 */
public class TinkerPedestalBlock extends PedestalBlockBase
{
	public TinkerPedestalBlock()
	{
		super(Properties.create(Material.ROCK)
				.notSolid()
				.setRequiresTool()
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(HarvestLevels.WOOD)
				.hardnessAndResistance(2,50));
		this.setShouldDropWhenHarvested(true);
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
