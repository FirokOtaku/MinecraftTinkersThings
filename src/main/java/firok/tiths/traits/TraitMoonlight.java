package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.*;

// 月色
public class TraitMoonlight extends AbstractTrait
{
	public TraitMoonlight()
	{
		super(nameTraitMoonlight, colorTraitMoonlight);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
//		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && !player.world.isRemote)
		{
			PotionEffect po=player.getActivePotionEffect(MobEffects.NIGHT_VISION);
			player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION,po==null?105:po.getDuration()+105,0));
		}
	}
}