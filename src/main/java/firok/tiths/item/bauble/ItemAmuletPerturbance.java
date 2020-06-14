package firok.tiths.item.bauble;

import baubles.api.BaubleType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import static firok.tiths.util.Predicates.canTick;

/**
 * 扰动护符
 */
public class ItemAmuletPerturbance extends ItemBauble
{
	public ItemAmuletPerturbance()
	{
		super(BaubleType.AMULET);
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player)
	{
		if(player.isInWater())
		{
			if(player.isServerWorld() && canTick(player.world,40,0))
			{
				if(Math.abs(player.motionX) + Math.abs(player.motionY) + Math.abs(player.motionZ) < 0.025)
				{
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY,60,0));
				}
			}
			// todo 粒子效果 - 气泡
		}
	}
}
