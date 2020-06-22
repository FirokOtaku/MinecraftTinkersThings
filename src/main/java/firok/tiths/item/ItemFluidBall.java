package firok.tiths.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.*;

import java.util.List;

/**
 * 液体球
 */
public class ItemFluidBall extends ItemCustom implements IFluid
{
	public ItemFluidBall()
	{
		super();
		this.setMaxStackSize(1);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		FluidStack fluidStack=getFluidStack(stack);

		if(fluidStack==null) return;

		tooltip.add(fluidStack.getUnlocalizedName());
		tooltip.add(fluidStack.amount + " mL");
	}
}
