package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitAntiGrav;
import static firok.tiths.common.Keys.nameTraitAntiGrav;

/**
 * 反重力
 */
public class TraitAntiGrav extends AbstractTrait
{
	public TraitAntiGrav()
	{
		super(nameTraitAntiGrav, colorTraitAntiGrav);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 80));
		}
	}
}
