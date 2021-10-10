package firok.tiths.modifier.general;

import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.config.ConfigModifier.rate_decoying_use;
import static firok.tiths.util.Predicates.canTrigger;

// 诱食
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitDecoying.java
@DevUse(isPlaceholder = true)
public class ModifierDecoying extends Modifier
{
	public ModifierDecoying()
	{
		super(0xea9b32);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		if(context.getAttacker().isServerWorld() && canTrigger(context, rate_decoying_use))
		{
			Actions.CauseSpawningPassives(context.getAttacker());
		}
		return 0;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		if(canTrigger(context, rate_decoying_use))
		{
			Actions.CauseSpawningPassives(context.getLiving());
		}
	}
}
