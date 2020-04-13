package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitSwitching;
import static firok.tiths.common.Keys.nameTraitSwitching;
/**
 * 换位
 */
public class TraitSwitching extends AbstractTrait
{
	public TraitSwitching()
	{
		super(nameTraitSwitching,colorTraitSwitching);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase entity, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && target.isEntityAlive())
		{
			Actions.CauseSwitching(entity,target);
		}
	}
}
