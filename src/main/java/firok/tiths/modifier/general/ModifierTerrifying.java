package firok.tiths.modifier.general;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 恐吓
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitTerrifying.java
 */
@DevUse
public class ModifierTerrifying extends Modifier
{
	public ModifierTerrifying()
	{
		super(0x2a135a);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(damageDealt > 0 && target != null && !target.world.isRemote && target.isAlive())
		{
			target.addPotionEffect(new EffectInstance(Effects.NAUSEA, 205, 0));
			target.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 205, 0));
			target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 205, 0));
			target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 205, 0));
		}

		return 0;
	}
}
