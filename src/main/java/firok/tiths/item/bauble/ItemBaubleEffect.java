package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import static firok.tiths.util.Predicates.canTick;

/**
 * 带效果的饰品
 */
public class ItemBaubleEffect extends ItemBauble
{
	Potion potion;
	public ItemBaubleEffect(BaubleType type, Potion potion)
	{
		super(type);
		this.potion=potion;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player)
	{
		player.addPotionEffect(new PotionEffect(potion,85,0));
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player)
	{
		PotionEffect pe=player.getActivePotionEffect(potion);
		if(pe!=null && pe.getDuration()<=85) player.removePotionEffect(potion);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(player.isServerWorld() && canTick(player.world,80,0))
		{
			player.addPotionEffect(new PotionEffect(potion,85,0));
		}
	}
}
