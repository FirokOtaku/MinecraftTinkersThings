package firok.tiths.modifier.general;

import firok.tiths.util.Actions;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 致残
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitMaiming.java
 */
@DevUse
public class ModifierMaiming extends Modifier
{
	public ModifierMaiming()
	{
		super(Colors.Crimson);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null)
		{
			Actions.CauseAccumEffect(target, new EffectInstance(Effects.SLOWNESS, 80, 2));
			Actions.CauseAccumEffect(target, new EffectInstance(Effects.WEAKNESS, 80, 2));
		}
		return 0;
	}
}
