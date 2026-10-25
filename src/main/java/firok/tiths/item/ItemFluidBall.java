package firok.tiths.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 液体球
 */
public class ItemFluidBall extends ItemCustom implements IFluid
{
	public ItemFluidBall()
	{
		super();
		this.setMaxStackSize(16);
	}

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
	{
		return new FluidHandlerItemStack(stack, ItemFluidCollector.CAPACITY_PER_BALL);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		FluidStack fluidStack = getFluidStack(stack);

		if(fluidStack == null) return;

		Fluid fluid = fluidStack.getFluid();
		if(fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA) // 原版的液体没有tile名字
		{
			tooltip.add(fluid.getBlock().getLocalizedName());
		}
		else
		{
			tooltip.add(fluid.getLocalizedName(fluidStack));
		}

		tooltip.add(fluidStack.amount + " mL");
	}
}
