package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitSteamy;
import static firok.tiths.common.Keys.nameTraitSteamy;

// 气动
public class TraitSteamy extends AbstractTrait
{
	public TraitSteamy()
	{
		super(nameTraitSteamy, colorTraitSteamy);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage * 0.75f;
	}

	@Override
	public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical)
	{
		return newKnockback * 1.25f;
	}
}
