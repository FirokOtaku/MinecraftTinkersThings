package firok.tiths.effect;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;

/**
 * 特性 - 可溶 (隐藏状态)
 * https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionHiddenSoluble.java#L12
 */
@DevUse(isPlaceholder = true)
public class EffectSoluble extends TithsEffect
{
	EffectSoluble()
	{
		super(EffectType.NEUTRAL, Colors.LightGray);
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
		if(!living.isInWater() || living.isSneaking()) return;
		Vector3d vec = Vector3d.fromPitchYaw(living.rotationPitch, living.rotationYaw);
		Vector3d mot = living.getMotion();
		living.setMotion(
				mot.x + vec.x / 25,
				mot.y + vec.y / 25,
				mot.z + vec.z / 25
		);
	}
}
