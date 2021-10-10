package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Actions;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

import static firok.tiths.util.Predicates.canTrigger;

// 喀嚓
// https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitCreaky.java
@DevUse(isPlaceholder = true)
public class ModifierCreaky extends Modifier
{
	public ModifierCreaky()
	{
		super(Colors.DarkSlateGray);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		if(canTrigger(context, ConfigModifier.rate_creaky_use))
		{
			Entity target = context.getTarget();
			Actions.CauseSpawningSilverfish(target, target.getPosX(), target.getPosY(), target.getPosZ());
		}
		return 0;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		if(canTrigger(context, ConfigModifier.rate_creaky_use))
		{
			LivingEntity target = context.getLiving();
			Actions.CauseSpawningSilverfish(target, target.getPosX(), target.getPosY(), target.getPosZ());
		}
	}
}
