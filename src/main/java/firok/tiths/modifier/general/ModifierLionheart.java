package firok.tiths.modifier.general;

import firok.tiths.effect.TithsEffects;
import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 狮心
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitLionheart.java
 * */
@DevUse
public class ModifierLionheart extends Modifier
{
	public ModifierLionheart()
	{
		super(0x87143e);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		// some new flavor
		return damage + 2.5f;
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		if(!context.getTarget().world.isRemote && damageDealt > 0)
		{
			LivingEntity holder = context.getAttacker();
			Actions.CauseAccumEffect(holder, new EffectInstance(TithsEffects.EFFECT_LION_HEART.get(), 55, 0));
		}
		return 0;
	}
}
