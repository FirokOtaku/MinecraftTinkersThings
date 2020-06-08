package firok.tiths.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemSoul extends EntityItem
{
	public EntityItemSoul(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityItemSoul(World worldIn, double x, double y, double z, ItemStack stack)
	{
		super(worldIn, x, y, z, stack);
	}

	public EntityItemSoul(World worldIn)
	{
		super(worldIn);
	}
}
