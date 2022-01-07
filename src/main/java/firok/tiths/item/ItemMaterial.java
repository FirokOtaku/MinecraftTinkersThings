package firok.tiths.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class ItemMaterial extends Item
{
	public ItemMaterial(Properties properties)
	{
		super(properties);
	}
	public ItemMaterial()
	{
		super(new Properties().group(ItemGroup.MATERIALS));
	}
}
