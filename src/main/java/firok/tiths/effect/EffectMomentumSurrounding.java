package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.potion.EffectType;

import static firok.tiths.util.Predicates.canTick;

/**
 * 动量环绕
 */
@DevUse
public class EffectMomentumSurrounding extends TithsEffect
{
	protected EffectMomentumSurrounding()
	{
		super(EffectType.NEUTRAL, 0x56728a, false);
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 4, 0);
	}
}
