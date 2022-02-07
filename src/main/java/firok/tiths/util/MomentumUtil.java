package firok.tiths.util;

import firok.tiths.config.ConfigGameplay;
import firok.tiths.effect.TithsEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;

/**
 * 动量效果计算工具类
 */
public class MomentumUtil
{
	/**
	 * 单位动量 <-> 状态效果持续时间 换算量
	 * 1 unit momentum = factor * tick
	 */
	private static int getFactorUnit()
	{
		return ConfigGameplay.factor_momentum_unit.get();
	}

	/**
	 * @param entity 指定实体
	 * @return 当前动量
	 */
	public static int getCurrentMomentum(LivingEntity entity)
	{
		if(entity == null) return 0;
		EffectInstance ei = entity.getActivePotionEffect(TithsEffects.EFFECT_MOMENTUM_SURROUNDING.get());
		return ei == null ? 0 : ei.getDuration();
	}
	public static void setCurrentMomentum(LivingEntity entity, int value)
	{
		if(entity == null) return;

		final Effect ems = TithsEffects.EFFECT_MOMENTUM_SURROUNDING.get();
		entity.removePotionEffect(ems);
		entity.addPotionEffect(new EffectInstance(ems, value, 0));
	}

	public static int getCurrentMomentumUnit(LivingEntity entity)
	{
		return getCurrentMomentum(entity) / getFactorUnit();
	}
	public static void setCurrentMomentumUnit(LivingEntity entity, int value)
	{
		setCurrentMomentum(entity, value * getFactorUnit());
	}
}
