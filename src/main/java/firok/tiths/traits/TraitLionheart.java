package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 狮心
public class TraitLionheart extends AbstractTrait
{
	public TraitLionheart()
	{
		super(nameTraitLionheart, colorTraitLionheart);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && damageDealt>0 && !player.world.isRemote)
		{
			PotionEffect pe=player.getActivePotionEffect(MobEffects.RESISTANCE);
			if(pe==null)
			{
				pe=new PotionEffect(MobEffects.RESISTANCE,25,3);
				player.addPotionEffect(pe);
			}
			else
			{
				int level=pe.getAmplifier();
				if(level<3) player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,35,3));
				else if(level==3) player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE,35+pe.getDuration(),3));
			}
		}
	}
}