package firok.tiths.modifier.melee;

import firok.tiths.util.Colors;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.vector.Vector3d;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 撼击
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitShaking.java
 */
public class ModifierShaking extends Modifier
{
	public ModifierShaking()
	{
		super(Colors.Fuchsia);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		if(target != null && damageDealt > 0)
		{
			Vector3d motionOld = target.getMotion();
			Vector3d motionNew = new Vector3d(
					0,
					motionOld.y <= 0 ? 0.4 : motionOld.y + 0.4,
					0
			);
			target.setMotion(motionNew);
			target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 30, 2));
			target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 30, 2));

			// todo play sound
		}

		return 0;
	}

	// todo 降低攻速和挖掘速度
}
