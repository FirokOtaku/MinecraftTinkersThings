package firok.tiths.potion;

import com.google.common.base.Predicate;
import firok.tiths.TinkersThings;
import firok.tiths.common.Keys;
import firok.tiths.util.Ranges;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTick;
import static firok.tiths.util.Predicates.canTrigger;

// 瘟疫
public class PotionPestilential extends BasePotion
{
	@SuppressWarnings("all")
	public final Predicate<? super Entity> selector;
	@SuppressWarnings("all")
	public PotionPestilential(Predicate<? super Entity> selector)
	{
		super(icon("weakened_pestilential"),true, Keys.colorPotionPestilential);
		this.selector=selector;
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return canTick(tick,40,4);
	}

	protected static final List<Potion> AVAILABLE_POTIONS = new ArrayList<>(); // 可能会添加的各类状态效果
	protected static float ratePerPotion = 0; // 每种效果每次可能出现的几率
	public static void addRandomPotion(Potion potion)
	{
		AVAILABLE_POTIONS.add(potion);
		ratePerPotion = 1f / AVAILABLE_POTIONS.size() / 1.3f;
	}

	static // 默认的debuff
	{
		addRandomPotion(MobEffects.SLOWNESS);
		addRandomPotion(MobEffects.BLINDNESS);
		addRandomPotion(MobEffects.NAUSEA);
		addRandomPotion(MobEffects.MINING_FATIGUE);
		addRandomPotion(MobEffects.WEAKNESS);
		addRandomPotion(MobEffects.POISON);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		World world=entity.world;
		if(!world.isRemote)
		{
			Random rand=world.rand;
			if(canTrigger(rand,0.25f)) // 瘟疫扩散
			{
				List<Entity> nearby=world.getEntitiesInAABBexcluding(
						entity,
						Ranges.Neighbour(entity,4),
						selector
				);

				for(Entity en:nearby)
				{
					EntityLivingBase enlb=(EntityLivingBase)en;
					enlb.addPotionEffect(new PotionEffect(this, 600 + 300 * level, level));
				}
			}

			for(Potion potion: AVAILABLE_POTIONS) // 添加各种乱七八糟的状态效果
			{
				if(canTrigger(rand,ratePerPotion))
				{
					entity.addPotionEffect(new PotionEffect(potion, 45, level));
				}
			}
		}
	}
}
