package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitInductance;
import static firok.tiths.common.Keys.nameTraitInductance;

/**
 * 电感
 */
public class TraitInductance extends AbstractTrait
{
	public TraitInductance()
	{
		super(nameTraitInductance,colorTraitInductance);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		// todo 音效
		return newDamage + ( player.world.isThundering()? 6 : 0 );
	}
}
