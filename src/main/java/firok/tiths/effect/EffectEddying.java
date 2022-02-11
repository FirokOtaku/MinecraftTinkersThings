package firok.tiths.effect;

import firok.tiths.util.Calculates;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

/**
 * 旋流
 * https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionEddying.java#L12
 */
@DevUse(isPlaceholder = true)
public class EffectEddying extends TithsEffect
{
	EffectEddying()
	{
		super(EffectType.NEUTRAL, 0x3f516b);
		setNoCure();
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return true;
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(living.isServerWorld() && living.isInWater())
		{
			final int cycle = 60;
			final float factorCycle = 360f / cycle;

			final float angle = living.ticksExisted % cycle * factorCycle * Calculates.FAC;

			Vector3d motion = living.getMotion();
			Vector3d motionNew = new Vector3d(
					motion.x + MathHelper.cos(angle) * 0.1,
					motion.y - 0.12,
					motion.z + MathHelper.sin(angle) * 0.1
			);
			living.setMotion(motionNew);
		}
	}
}
