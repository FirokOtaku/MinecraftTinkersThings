package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import firok.tiths.item.ItemCustom;
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
