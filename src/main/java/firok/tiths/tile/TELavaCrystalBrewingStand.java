package firok.tiths.tile;

import net.minecraft.block.BlockBrewingStand;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityBrewingStand;

import java.util.Arrays;

public class TELavaCrystalBrewingStand
	extends TileEntityBrewingStand
{
	/**
	 * Like the old updateEntity(), except more generic.
	 */
	public void update()
	{
		super.update();
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid fix 这里没改
	 */
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 3)
		{
			return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidIngredient(stack);
		}
		else
		{
			Item item = stack.getItem();

			if (index == 4)
			{
				return item == Items.BLAZE_POWDER || item == Items.STICK;
			}
			else
			{
				return net.minecraftforge.common.brewing.BrewingRecipeRegistry.isValidInput(stack) && this.getStackInSlot(index).isEmpty();
			}
		}
	}

	/**
	 * Get the name of this object. For players this returns their username
	 */
	public String getName()
	{
		return "container.tiths.lava_crystal_brewing_stand";
	}
}
