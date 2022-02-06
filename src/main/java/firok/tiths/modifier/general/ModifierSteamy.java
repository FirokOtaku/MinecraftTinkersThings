package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 气动
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitSteamy.java
 */
@DevUse
public class ModifierSteamy extends Modifier
{
	public ModifierSteamy()
	{
		super(0xb7d3f5);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		return damage * 0.75f;
	}

	@Override
	public float beforeEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback)
	{
		return (knockback + 0.25f) * 1.25f;
	}
}
