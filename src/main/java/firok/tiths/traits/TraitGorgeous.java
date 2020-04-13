package firok.tiths.traits;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Configs.Traits.factor_gorgeous_max;
import static firok.tiths.common.Configs.Traits.factor_gorgeous_min;
import static firok.tiths.common.Keys.colorTraitGorgeous;
import static firok.tiths.common.Keys.nameTraitGorgeous;

// 斑斓
public class TraitGorgeous extends AbstractTrait
{
	public TraitGorgeous()
	{
		super(nameTraitGorgeous, colorTraitGorgeous);
	}

	/**
	 * 1.1f ~ 1.2f
	 */
	public static float factorGorgeous(Entity entity)
	{
		if(factor_gorgeous_min > factor_gorgeous_max) return 1;
		else if(factor_gorgeous_min==factor_gorgeous_max) return (float)(factor_gorgeous_min);

		double bet=factor_gorgeous_max-factor_gorgeous_min;

		return (float)(entity.world.rand.nextFloat() * bet + factor_gorgeous_min);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage * factorGorgeous(player);
	}
}
