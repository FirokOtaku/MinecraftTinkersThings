package firok.tiths.modifier.general;

import firok.tiths.effect.TithsEffects;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 侵蚀
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitEroding.java
 */
@DevUse
public class ModifierEroding extends Modifier
{
	public ModifierEroding()
	{
		super(0x8a0d82);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null && target.isServerWorld())
		{
			EffectInstance peEroded=target.getActivePotionEffect(TithsEffects.EFFECT_ERODED.get());
			int lv=peEroded!=null?peEroded.getAmplifier()+1:0;
			if(lv>9) lv=9;
			target.addPotionEffect(new EffectInstance(TithsEffects.EFFECT_ERODED.get(),100,lv));
		}

		return 0;
	}

}
