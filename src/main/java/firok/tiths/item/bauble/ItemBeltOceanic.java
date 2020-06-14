package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.item.ItemStack;

/**
 * 远洋腰带
 */
public class ItemBeltOceanic extends ItemBauble
{
	public ItemBeltOceanic()
	{
		super(BaubleType.BELT);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(player==null) return;
		Entity entityRidden=player.getLowestRidingEntity();
		if(entityRidden instanceof EntityBoat)
		{
			if(Math.abs(entityRidden.motionX) + Math.abs(entityRidden.motionZ) > 0.2)
			{
				entityRidden.motionX *= 1.05;
				entityRidden.motionZ *= 1.05;
			}
		}
	}
}
