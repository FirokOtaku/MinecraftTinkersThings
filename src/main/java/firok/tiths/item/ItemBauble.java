package firok.tiths.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBauble extends ItemCustom implements IBauble
{
	private final BaubleType type;

	public ItemBauble(BaubleType type)
	{
		this.type=type;
		this.setMaxStackSize(1);
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return this.type;
	}
}
