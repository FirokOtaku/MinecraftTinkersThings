package firok.tiths.modifier.melee;

import firok.tiths.util.Calculates;
import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Vector3d;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 尊重
 *
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitRespecting.java
 */
@DevUse
public class ModifierRespecting extends Modifier
{
	public ModifierRespecting()
	{
		super(Colors.Brown);
	}

	@Override
	public float getEntityDamage(IModifierToolStack tool, int level, ToolAttackContext context, float baseDamage, float damage)
	{
		LivingEntity holder = context.getAttacker(), target = context.getLivingTarget();
		if(target == null) return damage;

		Vector3d vecHolderLook = holder.getLookVec();
		Vector3d vecBetweenGap = Calculates.calcUnitVector(
				target.getPositionVec().subtract(holder.getPositionVec())
		);

		// todo 可能需要增加位置判断
		return vecBetweenGap != null && vecHolderLook.dotProduct(vecBetweenGap) > 0.85 ?
				damage + baseDamage * 0.5f : damage;
	}
}
