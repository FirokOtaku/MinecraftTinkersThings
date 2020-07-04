package firok.tiths.traits;

import firok.tiths.util.Actions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;

import static firok.tiths.common.Keys.colorTraitInfiltrating;
import static firok.tiths.common.Keys.nameTraitInfiltrating;

/**
 * 透蚀
 */
public class TraitInfiltrating extends AbstractTrait
{
	public TraitInfiltrating()
	{
		super(nameTraitInfiltrating,colorTraitInfiltrating);
	}

	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		if(target==null) return;

		for(ItemStack stackEquipment:target.getEquipmentAndArmor())
		{
			if(stackEquipment==null || stackEquipment.isEmpty()) continue;

			Item itemEquipment=stackEquipment.getItem();

			int amount=20;

			if(itemEquipment instanceof ITinkerable)
			{
				ToolHelper.damageTool(stackEquipment,amount,target);
			}
			else if(stackEquipment.isItemStackDamageable())
			{
				stackEquipment.damageItem(amount,target);
			}
			ToolHelper.damageTool(tool,2,player);
		}
	}
}
