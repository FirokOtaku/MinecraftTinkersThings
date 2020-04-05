package firok.tiths.entity.item;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityItemAntiGrav extends EntityItem
{
	public EntityItemAntiGrav(World world, double posX, double posY, double posZ)
	{
		super(world, posX, posY, posZ);
	}

	public EntityItemAntiGrav(World world, double posX, double posY, double posZ, ItemStack stack)
	{
		super(world, posX, posY, posZ, stack);
	}

	public EntityItemAntiGrav(World world)
	{
		super(world);
	}

	@Override
	public void setNoGravity(boolean p_setNoGravity_1_)
	{
		super.setNoGravity(p_setNoGravity_1_);
	}
}
