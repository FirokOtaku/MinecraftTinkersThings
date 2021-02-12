package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitTranquilizing;
import static firok.tiths.common.Keys.nameTraitTranquilizing;

/**
 * 镇定
 */
public class TraitTranquilizing extends AbstractTrait
{
	public TraitTranquilizing()
	{
		super(nameTraitTranquilizing,colorTraitTranquilizing);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		super.onHit(tool, player, target, damage, isCritical);
		target.addPotionEffect(new PotionEffect(Potions.tranquilized,600,0));
	}
}
