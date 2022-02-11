package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

import static firok.tiths.util.Predicates.canTick;

/**
 * 清凉
 * https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionIcy.java#L13
 */
@DevUse(isPlaceholder = true)
public class EffectIcy extends TithsEffect
{
	EffectIcy()
	{
		super(EffectType.BENEFICIAL, 0x4194c0);
		setNoCure();
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, Math.max(1, 20 / (level + 1)), 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(living.isBurning())
			living.extinguish();
	}
}
