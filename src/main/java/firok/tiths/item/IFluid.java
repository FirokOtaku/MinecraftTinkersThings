package firok.tiths.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

/**
 * 可以储存液体的物品
 */
public interface IFluid
{
	default void setFluidStack(ItemStack itemStack, FluidStack fluidStack)
	{
		NBTTagCompound nbt=itemStack.hasTagCompound()?itemStack.getTagCompound():new NBTTagCompound();
		assert nbt!=null;
		fluidStack.writeToNBT(nbt);
		itemStack.setTagCompound(nbt);
	}
	default FluidStack getFluidStack(ItemStack itemStack)
	{
		if(itemStack==null) return null;
		return FluidStack.loadFluidStackFromNBT(itemStack.getTagCompound());
	}
}
