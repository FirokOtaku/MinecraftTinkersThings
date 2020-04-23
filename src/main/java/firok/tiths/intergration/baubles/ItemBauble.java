package firok.tiths.intergration.baubles;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBauble extends Item implements IBauble
{
	private final BaubleType type;

	public ItemBauble(BaubleType type)
	{
		this.type=type;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack)
	{
		return this.type;
	}
}
