package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

import static firok.tiths.common.Keys.colorTraitCapacitor;
import static firok.tiths.common.Keys.nameTraitCapacitor;

/**
 * 电容
 */
public class TraitArmorCapacitor extends AbstractArmorTrait
{
	public TraitArmorCapacitor()
	{
		super(nameTraitCapacitor,colorTraitCapacitor);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(target.isEntityAlive())
			target.addPotionEffect(new PotionEffect(Potions.paralysed, Configs.ArmorTraits.capacitor_duration, 0,false,false));
	}
}
