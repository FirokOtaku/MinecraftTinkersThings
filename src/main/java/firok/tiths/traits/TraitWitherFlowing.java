package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitWitherFlowing;
import static firok.tiths.common.Keys.nameTraitWitherFlowing;

// 凋零流动
public class TraitWitherFlowing extends AbstractTrait
{
	public TraitWitherFlowing()
	{
		super(nameTraitWitherFlowing, colorTraitWitherFlowing);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && target.isServerWorld() && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,160,2));
			target.addPotionEffect(new PotionEffect(MobEffects.WITHER,160,1));
		}
	}
}