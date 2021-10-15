package firok.tiths.effect;

import firok.tiths.util.Colors;
import firok.tiths.util.DevUse;
import firok.tiths.util.Predicates;
import firok.tiths.util.Ranges;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

// 瘟疫
// https://github.com/351768593/MinecraftTinkersThings/blob/5bf9e32008780961f3951e2700317b245a9bf967/src/main/java/firok/tiths/potion/PotionPestilential.java#L23
@DevUse(isPlaceholder = true)
public class EffectPestilential extends TithsEffect
{
	final Predicate<? super Entity> selector;
	protected EffectPestilential(Predicate<? super Entity> selector)
	{
		super(EffectType.HARMFUL, Colors.DarkGreen);
		this.selector = selector;
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return canTick(duration, 40, 4);
	}

	protected static final List<Effect> AVAILABLE_POTIONS = new ArrayList<>(12); // 可能会添加的各类状态效果
	protected static float ratePerPotion = 0; // 每种效果每次可能出现的几率
	public static void addRandomEffect(Effect potion)
	{
		AVAILABLE_POTIONS.add(potion);
		ratePerPotion = 1f / AVAILABLE_POTIONS.size() / 1.3f;
	}
	static // 默认的debuff
	{
		addRandomEffect(Effects.SLOWNESS);
		addRandomEffect(Effects.BLINDNESS);
		addRandomEffect(Effects.NAUSEA);
		addRandomEffect(Effects.MINING_FATIGUE);
		addRandomEffect(Effects.WEAKNESS);
		addRandomEffect(Effects.POISON);
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		World world = living.world;
		Random rand = world.rand;
		if(world.isRemote) return;

		if(canTrigger(rand, 0.25))
		{
			List<Entity> nearby = world.getEntitiesInAABBexcluding(living, Ranges.Neighbour(living, 4), selector);
			for(Entity en : nearby)
			{
				if(en instanceof LivingEntity)
				{
					((LivingEntity) en).addPotionEffect(new EffectInstance(this, 600 + 300 * level, level));
				}
			}
		}

		for(Effect potion: AVAILABLE_POTIONS) // 添加各种乱七八糟的状态效果
		{
			if(canTrigger(rand, ratePerPotion))
			{
				living.addPotionEffect(new EffectInstance(potion, 45, level));
			}
		}
	}

	/**
	 * 瘟疫
	 */
	public static class pestilential extends EffectPestilential
	{
		public pestilential()
		{
			super(Predicates::isLivingAlive);
		}
	}

	/**
	 * 弱化瘟疫 只在怪物间传播
	 */
	public static class weak_pestilential extends EffectPestilential
	{
		public weak_pestilential()
		{
			super(Predicates::isMobAlive);
		}
	}
}
