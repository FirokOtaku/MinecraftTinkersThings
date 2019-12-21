package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitMaiming;
import static firok.tiths.common.Keys.nameTraitMaiming;

// 致残
public class TraitMaiming extends AbstractTrait
{
	public TraitMaiming()
	{
		super(nameTraitMaiming, colorTraitMaiming);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && target.isServerWorld() && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,80,2));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,80,2));
		}
	}
}
