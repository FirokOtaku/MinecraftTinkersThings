package firok.tiths.modifier.general;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

/**
 * 透蚀
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitInfiltrating.java
 * */
public class ModifierInfiltrating extends Modifier
{
	public ModifierInfiltrating()
	{
		super(0x6a6a78);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		if(damage >= 2) return damage - 2;
		else return 0;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		LivingEntity holder = context.getAttacker();
		if(target != null)
		{
			for(ItemStack stackEquipment : target.getEquipmentAndArmor())
			{
				if(stackEquipment == null || stackEquipment.isEmpty()) continue;

				Item itemEquipment = stackEquipment.getItem();
				if(itemEquipment instanceof ToolItem)
				{
					IModifierToolStack infoEquipment = ToolStack.from(stackEquipment);
					ToolDamageUtil.damage(infoEquipment, 20, holder, stackEquipment);
				}
				else if(stackEquipment.isDamageable())
				{
					// todo maybe we should replace tool holder to equipment wearer (target)
					stackEquipment.damageItem(20, holder, (toolHolder)->{});
				}
			}
		}
		return 0;
	}
}
