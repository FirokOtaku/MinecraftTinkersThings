package firok.tiths.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nullable;

abstract class TithsEffect extends Effect
{
	private final boolean isInstant;
	protected TithsEffect(EffectType typeIn, int liquidColorIn, boolean isInstant)
	{
		super(typeIn, liquidColorIn);
		this.isInstant = isInstant;
	}

	@Override
	public void performEffect(LivingEntity living, int level) { }

	@Override
	public void affectEntity(@Nullable Entity source, @Nullable Entity sourceIndirect, LivingEntity living, int level, double health) { }

	@Override
	public boolean isReady(int duration, int level) { return false; }

	@Override
	public final boolean isInstant() { return isInstant; }
}
