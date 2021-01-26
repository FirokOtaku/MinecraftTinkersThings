package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitAngerOfFarmer;
import static firok.tiths.common.Keys.nameTraitAngerOfFarmer;

/**
 * 农夫之怒
 */
public class TraitAngerOfFarmer extends AbstractTrait
{
	public TraitAngerOfFarmer()
	{
		super(nameTraitAngerOfFarmer,colorTraitAngerOfFarmer);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		// 你去死吧
		return target.getActivePotionEffect(Potions.farmland_trampler) != null ?
				newDamage * 1.75f :
				newDamage;
	}
}
