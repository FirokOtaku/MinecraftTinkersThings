package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

// 助燃
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitCombustionSupporting.java
@DevUse(isPlaceholder = true)
public class ModifierCombustionSupporting extends Modifier
{
	public ModifierCombustionSupporting()
	{
		super(0xa56228);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		if (context.getTarget().isBurning())
		{
			ToolDamageUtil.directDamage(tool, 5, context.getAttacker(), null);
			return damage + baseDamage / 2;
		}
		return damage;
	}
}
