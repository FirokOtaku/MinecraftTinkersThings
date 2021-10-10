package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.SilverfishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class Actions
{
	// 在世界生成物品
	public static void CauseSpawnItem(Entity target, ItemStack stack)
	{
		World world=target.world;
		ItemEntity ei=new ItemEntity(world,target.getPosX(),target.getPosY(),target.getPosZ(),stack);
		world.addEntity(ei);
	}

	// 喀嚓 - 触发召唤
	public static void CauseSpawningSilverfish(LivingEntity player, double x, double y, double z)
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
		catch(Exception ignored) { }
	}
}
