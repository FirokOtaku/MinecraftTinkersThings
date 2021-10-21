package firok.tiths.effect;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;

// 沉重
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionHeavy.java#L10
@DevUse(isPlaceholder = true)
public class EffectHeavy extends TithsEffect
{
	public EffectHeavy()
	{
		super(EffectType.NEUTRAL, Colors.DarkSeaGreen);
	}

	@Override
	public boolean isReady(int duration, int amplifier)
	{
		return true;
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(living instanceof PlayerEntity && ((PlayerEntity) living).isCreative()) {return;}

		level ++;
		Vector3d motion = living.getMotion();
		if(motion.y > -3 * level) {
			living.setMotion(new Vector3d(
					motion.x,
					motion.y - 0.08f * level,
					motion.z
			));
		}
	}
}
