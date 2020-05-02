package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitCombustionSupporting;
import static firok.tiths.common.Keys.nameTraitCombustionSupporting;

// 助燃
public class TraitCombustionSupporting extends AbstractTrait
{
	public TraitCombustionSupporting()
	{
		super(nameTraitCombustionSupporting,colorTraitCombustionSupporting);
	}

	@Override
	public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical)
	{
		if(target.isBurning())
		{
			ToolHelper.damageTool(tool,5,player);
			return newDamage + damage*0.5f;
		}
		return newDamage;
	}
}