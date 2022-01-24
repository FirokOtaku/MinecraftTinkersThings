package firok.tiths.modifier.general;

import firok.tiths.config.ConfigModifier;
import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 星绽
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitStarDashing.java
 */
@DevUse
public class ModifierStarDashing extends Modifier
{
	public ModifierStarDashing()
	{
		super(0xea7632);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity holder = context.getAttacker();
		Vector3d pos = holder.getPositionVec();

		if(!holder.world.isRemote)
		{
			Actions.CauseStarDashing(holder.world,pos.x,pos.y,pos.z,
				ConfigModifier.factor_star_dashing_amount.get(),
				ConfigModifier.factor_star_dashing_speed.get().floatValue(),
				ConfigModifier.factor_star_dashing_damage.get().floatValue(),
				holder);
		}
		return 0;
	}
}
