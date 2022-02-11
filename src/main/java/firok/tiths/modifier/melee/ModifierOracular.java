package firok.tiths.modifier.melee;

import firok.tiths.DamageSources;
import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 神谕
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitOracular.java
 * */
@DevUse
public class ModifierOracular extends Modifier
{
	ModifierOracular()
	{
		super(0x5dffb1);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget(), holder = context.getAttacker();

		int countTarget = Actions.CauseRemoveEffect(target, true, false, EffectType.BENEFICIAL); // 移除目标增益效果
		if(countTarget > 0) target.attackEntityFrom(DamageSources.Oracular(holder), countTarget * 1.5f);

		int countHolder = Actions.CauseRemoveEffect(holder, true, false, EffectType.HARMFUL); // 移除持有者负面效果
		if(countHolder > 0) holder.heal(countHolder * 1.5f);

		return 0;
	}
}
