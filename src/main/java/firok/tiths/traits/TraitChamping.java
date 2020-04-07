package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.traits.AbstractTraitLeveled;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import static firok.tiths.common.Keys.colorTraitChamping;
import static firok.tiths.common.Keys.nameTraitChamping;
import static firok.tiths.util.Predicates.canTrigger;

// 强袭
public class TraitChamping extends AbstractTraitLeveled
{
	public TraitChamping(int level)
	{
		super(nameTraitChamping,colorTraitChamping,3,level);
	}

	@Override
	public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target)
	{
		ModifierNBT data = new ModifierNBT(TinkerUtil.getModifierTag(tool, name));
		return canTrigger(player.world,data.level*0.2f);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		return isCritical? (newDamage+damage/2):newDamage;
	}
}
