package firok.tiths.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.DamageSources;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitBirefringent;
import static firok.tiths.common.Keys.nameTraitBirefringent;
import static firok.tiths.util.Predicates.canTrigger;

// 双折射 (双折)
public class TraitBirefringent extends AbstractTrait
{
	public TraitBirefringent()
	{
		super(nameTraitBirefringent, colorTraitBirefringent);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && player.isServerWorld() && target.isEntityAlive() && canTrigger(player.world, Configs.Traits.rate_birefringent_damage))
		{
			target.hurtResistantTime=0;
			target.attackEntityFrom(DamageSources.Birefringent(player),damageDealt);
		}
	}
}
