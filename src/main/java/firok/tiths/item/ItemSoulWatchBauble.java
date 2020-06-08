package firok.tiths.item;

import baubles.api.BaubleType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSoulWatchBauble extends ItemBauble implements ISoulWatch
{
	public ItemSoulWatchBauble(BaubleType type)
	{
		super(type);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, list, flagIn);
		addWatchInformation(stack, worldIn, list, flagIn);
	}
}
