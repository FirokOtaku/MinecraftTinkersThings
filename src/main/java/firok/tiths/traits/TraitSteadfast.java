package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitSteadfast;
import static firok.tiths.common.Keys.nameTraitSteadfast;

/**
 * 岿然
 */
public class TraitSteadfast extends AbstractTrait
{
	public TraitSteadfast()
	{
		super(nameTraitSteadfast,colorTraitSteadfast);
	}

	@Override
	public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity)
	{
		return super.onToolDamage(tool, damage, newDamage, entity);
	}
}
