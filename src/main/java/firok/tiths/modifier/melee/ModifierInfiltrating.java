package firok.tiths.modifier.melee;

import firok.tiths.util.Actions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

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
				Actions.CauseDamageStack(stackEquipment, 20, holder);
			}
		}
		return 0;
	}
}
