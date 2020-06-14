package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitIlluminating;
import static firok.tiths.common.Keys.nameTraitAblaze;

/**
 * 灯明
 */
public class TraitAblaze extends AbstractTrait
{
	public TraitAblaze()
	{
		super(nameTraitAblaze,colorTraitIlluminating);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		target.addPotionEffect(new PotionEffect(Potions.illuminating,500,0));
	}
}
