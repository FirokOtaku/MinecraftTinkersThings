package firok.tiths.util;

import firok.tiths.entity.TithsEntities;
import firok.tiths.entity.projectile.ProjectileDashingStar;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.item.ToolItem;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static firok.tiths.util.Calculates.*;

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

	/**
	 * 对一堆物品造成损伤
	 * @return 是否成功造成损伤
	 */
	public static boolean CauseDamageStack(ItemStack stack, int damage, LivingEntity holder)
	{
		if(stack == null || stack.isEmpty() || damage <= 0) return false;

		Item item = stack.getItem();
		if(item instanceof ToolItem)
		{
			IModifierToolStack infoStack = ToolStack.from(stack);
			ToolDamageUtil.damage(infoStack, damage, holder, stack);
			return true;
		}
		else if(stack.isDamageable())
		{
			// todo maybe we should replace tool holder to equipment wearer (target)
			stack.damageItem(damage, holder, (stackHolder)->{});
			return true;
		}
		else return false;
	}

	/**
	 * 维修一堆物品
	 * @return 是否成功维修
	 */
	public static boolean CauseRepairStack(ItemStack stack, int healing, LivingEntity holder)
	{
		if(stack == null || stack.isEmpty() || healing <= 0) return false;

		Item item = stack.getItem();
		if(item instanceof ToolItem)
		{
			IModifierToolStack infoStack = ToolStack.from(stack);
			ToolDamageUtil.repair(infoStack, healing);
			return true;
		}
		else if(stack.isRepairable())
		{
			int newDamage = Math.max(stack.getDamage() - healing, 0);
			stack.setDamage(newDamage);
			return true;
		}
		return false;
	}

	/**
	 * 移除指定实体身上的状态效果
	 * @param living 指定实体
	 * @param directly 是否触发移除事件
	 * @param containHidden 是否移除隐藏状态效果
	 * @param types 要移除的状态效果种类
	 * @return 移除数量
	 */
	public static int CauseRemoveEffect(LivingEntity living, boolean directly, boolean containHidden, EffectType... types)
	{
		if(types == null || living == null) return 0;

		Collection<EffectInstance> listEffect = living.getActivePotionEffects();
		for(EffectInstance ei : listEffect)
		{
			Effect effect = ei.getPotion();
			// todo 这个判断以后可能需要重写
			// 目前的判断是 普通的状态效果是会在3个地方都显示
			// 特殊的状态效果才会在某些地方不显示
			boolean isEffectRender = effect.shouldRender(ei) && effect.shouldRenderHUD(ei) && effect.shouldRenderInvText(ei);
			if(!isEffectRender && !containHidden) continue; // 不渲染 不包含隐藏效果

			// 检查是否是指定种类
			boolean isEffectShouldRemove = false;
			for(EffectType t : types)
				if(t == effect.getEffectType())
				{
					isEffectShouldRemove = true;
					break;
				}
			if(!isEffectShouldRemove) continue;

			if(directly)
				living.removeActivePotionEffect(effect);
			else
				living.removePotionEffect(effect);
		}

		return 0;
	}

	// 星绽 - 创建粒子
	public static ProjectileDashingStar CauseStarDashing(World world,double fromX,double fromY,double fromZ,double toX,double toY,double toZ,float speed,float damage,Entity shootingEntity)
	{
		ProjectileDashingStar star = TithsEntities.projectileDashingStar.get().create(world);
		star.setPosition(fromX, fromY, fromZ);
		star.setShooter(shootingEntity);

		double mod = (fromX-toX)*(fromX-toX)+(fromY-toY)*(fromY-toY)+(fromZ-toZ)*(fromZ-toZ);
		mod = MathHelper.sqrt(mod);

		star.setMotion(
				speed * (toX-fromX) / mod,
				speed * (toY-fromY) / mod + 0.2,
				speed * (toZ-fromZ) / mod
		);
		star.setDamage(damage);

		world.addEntity(star);
		return star;
	}
	public static ProjectileDashingStar CauseStarDashing(Entity from,Entity to,float speed,float damage,Entity shootingEntity)
	{
		Vector3d fromPos = from.getPositionVec(), toPos = to.getPositionVec();
		ProjectileDashingStar star=CauseStarDashing(from.world,
				fromPos.x, fromPos.y, fromPos.z,
				toPos.x,toPos.y+to.getEyeHeight(),toPos.z,
				speed,damage,shootingEntity);
		return star;
	}
	private static final float OffsetY=0.866f;
	private static final float OffsetY2=1.732f;
	public static ProjectileDashingStar[] CauseStarDashing(final World world,final double centerX,final double centerY,final double centerZ,final int amount,final float speed,final float damage,Entity shootingEntity)
	{
		ProjectileDashingStar[] ret=new ProjectileDashingStar[amount];
		Random rand=world.rand;

		for(int i=0;i<amount;i++)
		{
			double fromY= centerY-OffsetY+rand.nextDouble()*OffsetY2;
			double toY= fromY-OffsetY+rand.nextDouble()*OffsetY2;

			float rotXZ=rand.nextFloat() * 2 * (float)Math.PI; // 从中心点到第一圈的角度
			float rotXZ2=rotXZ- (float)PI_6 +rand.nextFloat()*2*(float)PI_6; // 从第一圈向外的角度

			double fromX=centerX+ MathHelper.cos(rotXZ)*2;
			double fromZ=centerZ+ MathHelper.sin(rotXZ)*2;

			double toX=fromX+ MathHelper.cos(rotXZ2);
			double toZ=fromZ+ MathHelper.sin(rotXZ2);

			ret[i]=CauseStarDashing(world,fromX,fromY,fromZ,toX,toY,toZ,speed,damage,shootingEntity);
		}

		// todo sound event

		return ret;
	}
}
