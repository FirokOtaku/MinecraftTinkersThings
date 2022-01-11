package firok.tiths.modifier.general;

import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 振奋
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitHyper.java
 * */
@DevUse
public class ModifierHyper extends Modifier
{
	public ModifierHyper()
	{
		super(0xa022ff);
	}

	public void addEffects(LivingEntity living)
	{
		if(living != null && living.isServerWorld())
		{
			Actions.CauseAccumEffect(living, new EffectInstance(Effects.SPEED, 30, 1));
			Actions.CauseAccumEffect(living, new EffectInstance(Effects.HASTE, 30, 1));
		}
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		addEffects(context.getAttacker());
		return 0;
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		addEffects(context.getLiving());
	}
}
