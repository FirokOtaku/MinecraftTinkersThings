package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Collection;

import static firok.tiths.common.Keys.colorTraitPiercing;
import static firok.tiths.common.Keys.nameTraitPiercing;

/**
 * 锥心
 */
public class TraitPiercing extends AbstractTrait
{
	public TraitPiercing()
	{
		super(nameTraitPiercing,colorTraitPiercing);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		int count = 0;
		Collection<PotionEffect> pes = target.getActivePotionEffects();
		for(PotionEffect pe : pes) if(pe.getPotion().isBadEffect()) count++;
		return newDamage * ( 1 + count * 0.2f );
	}
}
