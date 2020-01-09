package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.DamageSources.BirefringentDamage;
import static firok.tiths.common.Keys.colorTraitBirefringent;
import static firok.tiths.common.Keys.nameTraitBirefringent;
import static firok.tiths.util.Predicates.canTrigger;

// 双折射 (双折)
public class TraitBirefringent extends AbstractTrait
{
	public static final float rate=0.3f; // 双折几率
	public TraitBirefringent()
	{
		super(nameTraitBirefringent, colorTraitBirefringent);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
		if(wasHit && player.isServerWorld() && target.isEntityAlive() && canTrigger(player.world,rate))
		{
			target.hurtResistantTime=0;
			target.attackEntityFrom(BirefringentDamage,damageDealt);
		}
	}
}
