package firok.tiths.effect;

import firok.tiths.util.Actions;
import firok.tiths.util.DevUse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

// 酸蚀
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionAcidWetted.java#L15
@DevUse(isPlaceholder = true)
public class EffectAcidWetted extends TithsEffect
{
	EffectAcidWetted()
	{
		super(EffectType.HARMFUL, 0x0e8c39);
		setNoCure();
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 5, 0);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		final int damage = 5 + level * 5;
		Actions.CauseAcidDamage(living, damage, canTrigger(living.world, 0.15));
	}
}
