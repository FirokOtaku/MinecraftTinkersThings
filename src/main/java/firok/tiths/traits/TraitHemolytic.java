package firok.tiths.traits;

import firok.tiths.common.Configs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Configs.Traits.factor_hemolytic_damage;
import static firok.tiths.common.Configs.Traits.rate_hemolytic;
import static firok.tiths.common.Keys.colorTraitHemolytic;
import static firok.tiths.common.Keys.nameTraitHemolytic;
import static firok.tiths.util.Predicates.canTrigger;

// 溶血
public class TraitHemolytic  extends AbstractTrait
{
	public TraitHemolytic()
	{
		super(nameTraitHemolytic, colorTraitHemolytic);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return newDamage * (float)factor_hemolytic_damage;
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(!player.world.isRemote && wasHit && damageDealt > Configs.Traits.factor_hemolytic_limit && canTrigger(player.world,rate_hemolytic))
		{
			ToolHelper.healTool(tool,(int)(damageDealt / Configs.Traits.factor_hemolytic_repair),player);
		}
	}
}
