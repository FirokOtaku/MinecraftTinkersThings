package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 电感
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitInductance.java
 */
@DevUse
public class ModifierInductance extends Modifier
{
	public ModifierInductance()
	{
		super(0x135cc7);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		return context.getTarget().world.isThundering() ? damage + 6 : damage;
	}
}
