package firok.tiths.modifier.melee;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.config.ConfigModifier.*;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 溶血
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitHemolytic.java
 * */
@DevUse
public class ModifierHemolytic extends Modifier
{

	public ModifierHemolytic()
	{
		super(Colors.DarkRed);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		return (float)(damage * factor_hemolytic_damage.get());
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		Entity target = context.getTarget();
		World world = target.world;
		if(world.isRemote && damageDealt >= factor_hemolytic_limit.get() && canTrigger(world, rate_hemolytic.get()))
		{
			ToolDamageUtil.repair(tool, (int)(damageDealt / factor_hemolytic_repair.get()));
		}
		return 0;
	}

}
