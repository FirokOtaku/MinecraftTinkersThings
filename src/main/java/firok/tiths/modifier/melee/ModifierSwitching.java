package firok.tiths.modifier.melee;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 换位
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitSwitching.java
 */
@DevUse
public class ModifierSwitching extends Modifier
{
	public ModifierSwitching()
	{
		super(Colors.SeaGreen);
	}

	@Override
	public int afterEntityHit(IModifierToolStack tool, int level, ToolAttackContext context, float damageDealt)
	{
		LivingEntity target = context.getLivingTarget();
		LivingEntity holder = context.getAttacker();
		if(damageDealt > 0 && target != null && holder.isAlive() && target.isAlive())
		{
			Vector3d posHolder = holder.getPositionVec();
			Vector3d posTarget = target.getPositionVec();
			target.setPositionAndUpdate(posHolder.x, posHolder.y, posHolder.z);
			holder.setPositionAndUpdate(posTarget.x, posTarget.y, posTarget.z);
		}
		return 0;
	}
}
