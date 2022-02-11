package firok.tiths.modifier.melee;

import firok.tiths.effect.TithsEffects;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 旋流
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitEddying.java
 */
@DevUse(isPlaceholder = true)
public class ModifierEddying extends Modifier
{
	public ModifierEddying()
	{
		super(0x3f516b);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null && target.isServerWorld() && target.getHealth() > 0)
		{
			target.addPotionEffect(new EffectInstance(TithsEffects.EFFECT_EDDYING.get(), 200, 0));
		}
		return 0;
	}
}
