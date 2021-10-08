package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
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
}
