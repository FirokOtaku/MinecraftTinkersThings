package firok.tiths.item;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDynBucket;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 液体球
 */
public class ItemFluidBall extends ItemCustom implements IFluid, ItemMeshDefinition
{
	public ItemFluidBall()
	{
		super();
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag flag)
	{
		super.addInformation(stack, world, tooltip, flag);

		FluidStack fluidStack=getFluidStack(stack);

		if(fluidStack==null) return;

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

	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		return null;
	}
}
