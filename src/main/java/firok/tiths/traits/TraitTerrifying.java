package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 恐吓
public class TraitTerrifying extends AbstractTrait
{
	public TraitTerrifying()
	{
		super(nameTraitTerrifying, colorTraitTerrifying);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && !player.world.isRemote && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(MobEffects.NAUSEA,205,0));
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS,205,0));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,205,0));
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,205,0));
		}
	}
}