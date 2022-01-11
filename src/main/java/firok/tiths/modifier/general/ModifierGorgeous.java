package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.config.ConfigModifier.*;

/**
 * 斑斓
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitGorgeous.java
 * */
@DevUse
public class ModifierGorgeous extends Modifier
{
	public ModifierGorgeous()
	{
		super(0x0a7dd6);
	}

	/**
	 * 1.1f ~ 1.2f
	 */
	public static float factorGorgeous(Entity entity)
	{
		final double min = factor_gorgeous_min.get(), max = factor_gorgeous_max.get();
		if(min > max) return (float) min;

		double bet = max - min;
		return (float)(entity.world.rand.nextFloat() * bet + min);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		return damage * factorGorgeous(context.getPlayerAttacker());
	}
}
