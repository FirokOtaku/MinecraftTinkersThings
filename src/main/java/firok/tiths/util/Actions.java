package firok.tiths.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * util class for many effects
 */
public class Actions
{
	// 在世界生成物品
	public static void CauseSpawnItem(Entity target, ItemStack stack)
	{
		World world = target.world;
		ItemEntity ei = new ItemEntity(world, target.getPosX(), target.getPosY(), target.getPosZ(), stack);
		world.addEntity(ei);
	}

	// 喀嚓 - 触发召唤
	public static void CauseSpawningSilverfish(Entity player, double x, double y, double z)
	{
		try
		{
			World world = player.world;
			SilverfishEntity entity = new SilverfishEntity(EntityType.SILVERFISH, world);
			entity.setPosition(x, y, z);
			world.addEntity(entity);
			entity.setLastAttackedEntity(player);
			entity.playSound(SoundEvents.ENTITY_SILVERFISH_AMBIENT, 1, 1);
		}
		catch (Exception ignored) { }
	}

	private static final ArrayList<EntityType<?>> entityPassives = new ArrayList<>();
	static
	{
		entityPassives.add(EntityType.COW);
		entityPassives.add(EntityType.SHEEP);
		entityPassives.add(EntityType.PIG);
		entityPassives.add(EntityType.CHICKEN);
	}
	public static void registerPassive(EntityType<?> typePassive)
	{
		if(typePassive == null) throw new IllegalArgumentException("param EntityType cannot be null");
		entityPassives.add(typePassive);
	}
	// 诱食 - 触发召唤
	public static void CauseSpawningPassives(LivingEntity player)
	{
		try
		{
			if (entityPassives.size() <= 0) return;

			World world = player.world;
			Random rand = world.rand;
			// 寻找召唤位置
			final int cx = (int) player.getPosX(), cy = (int) player.getPosY(), cz = (int) player.getPosZ();
			final int tx = cx + rand.nextInt(16) - 8, tz = cz + rand.nextInt(16) - 8;

			EntityType<?> typePassive = entityPassives.get(rand.nextInt(entityPassives.size()));
			Entity passive = typePassive.create(world);

			byte countAir = 0;
			FOR_FIND_LOCATION_SPAWN:
			for (int ty = cy + 5; ty > cy - 5; ty--)
			{
				BlockPos posTemp = new BlockPos(tx, ty, tz);
				BlockState state = world.getBlockState(posTemp);
				if (state.getBlock() == Blocks.AIR)
				{
					countAir++;
				} else // is not air
				{
					if (countAir >= 3 && state.canEntitySpawn(world, posTemp, typePassive))
					{
						passive.setPosition(posTemp.getX(), posTemp.getY() + 2.5, posTemp.getZ());
						world.addEntity(passive);
						break FOR_FIND_LOCATION_SPAWN;
					}

					countAir = 0;
				}
			}
		} catch (Exception ignored)
		{
		}
	}

	/**
	 * 酸蚀 - 触发耐久损伤
	 */
	public static void CauseAcidDamage(LivingEntity entity, int damage, boolean playSound)
	{
		// todo play sound

		for(ItemStack stackEqui : entity.getEquipmentAndArmor())
		{
			if(stackEqui.isDamageable()) {
				stackEqui.damageItem(damage, entity, living -> {});
			}
		}
	}

	/**
	 * 为目标叠加状态
	 * */
	public static void CauseAccumEffect(LivingEntity target, EffectInstance ei)
	{
		if(target == null || ei == null) return;

		Effect effect = ei.getPotion();
		EffectInstance eiOrigin = target.getActivePotionEffect(effect);
		if(eiOrigin == null || eiOrigin.getAmplifier() < ei.getAmplifier())
			target.addPotionEffect(ei);
		else
			target.addPotionEffect(new EffectInstance(effect, eiOrigin.getDuration() + ei.getDuration(), ei.getAmplifier()));
	}
}
