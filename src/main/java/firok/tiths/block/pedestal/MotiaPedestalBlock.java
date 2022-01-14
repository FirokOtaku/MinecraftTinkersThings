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
 * made with motia crystal. <br>
 * would "take photo" of the stack at player's main hand. <br>
 * it's display-use only. no need one stack after photo taken.
 */
public class MotiaPedestalBlock extends PedestalBlockBase
{
	public MotiaPedestalBlock()
	{
		super(Properties.create(Material.ROCK)
				.notSolid()
				.setRequiresTool()
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(HarvestLevels.WOOD)
				.hardnessAndResistance(2,50));
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		ItemStack stackHeld = player.getHeldItemMainhand();
		TilePedestalBase te = getTilePedestalAt(world, pos);
		ItemStack stackPedestal = te.getStackPedestal();
		if(!stackPedestal.isEmpty())
		{
			player.addItemStackToInventory(stackPedestal);
			te.setStackPedestal(ItemStack.EMPTY);
		}
		else if(!stackHeld.isEmpty())
		{
			player.setHeldItem(Hand.MAIN_HAND, ItemStack.EMPTY);
			te.setStackPedestal(stackHeld);
		}
		return ActionResultType.SUCCESS;
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}
}
