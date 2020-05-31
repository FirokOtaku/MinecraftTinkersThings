package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 储存灵魂的物品
 */
public interface ISoulStore
{
	int Low=100;
	int Common=50;
	int High=0;
	/**
	 * 充能优先级
	 * @param stack 物品
	 * @param entity 实体
	 * @return 优先级
	 */
	default int chargeSoulPriority(ItemStack stack, EntityLivingBase entity)
	{
		return Common;
	}

	/**
	 * 充能
	 * @param stack 物品
	 * @param entity 实体
	 * @param point 充能量
	 * @return 实际充能量
	 */
	default int chargeSoul(ItemStack stack,EntityLivingBase entity,int point)
	{
		if(stack==null || point<=0) return 0;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);
		assert nbt!=null;
		int origin=countSoul(stack);
		nbt.setInteger("soul",origin+point);
		return point;
	}

	@Deprecated // this is just a util function
	default int chargeSoulMax(ItemStack stack,EntityLivingBase entity,int point,int max)
	{
		if(stack==null || point<=0) return 0;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);
		assert nbt!=null;
		int origin=countSoul(stack);

		if(origin+point>max)
		{
			nbt.setInteger("soul",max);
			return max-origin;
		}
		else
		{
			nbt.setInteger("soul",origin+point);
			return point;
		}
	}

	/**
	 * 消耗优先级
	 * @param stack 物品
	 * @param entity 实体
	 * @return 优先级
	 */
	default int costSoulPriority(ItemStack stack, EntityLivingBase entity)
	{
		return Common;
	}

	/**
	 * 消耗
	 * @param stack 物品
	 * @param entity 实体
	 * @param point 消耗量
	 * @param fromDeath 消耗是否来自死亡逸散
	 * @return 实际消耗量
	 */
	default int costSoul(ItemStack stack,EntityLivingBase entity,int point,boolean fromDeath)
	{
		if(stack==null || point<=0) return 0;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);
		assert nbt!=null;
		int origin=countSoul(stack);

		if(origin<point)
		{
			nbt.setInteger("soul",0);
			return origin;
		}
		else
		{
			nbt.setInteger("soul",origin-point);
			return point;
		}
	}

	/**
	 * 获取一个物品里储存了多少灵魂<br>
	 * 跟其它方法不一样, 这个方法不接收实体参数, 因为储存的点数这个属性本身不受实体状态影响
	 * @param stack 物品
	 * @return 储存的灵魂点数
	 */
	default int countSoul(ItemStack stack)
	{
		if(stack==null) return 0;
		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);
		assert nbt!=null;

		return nbt.hasKey("soul")?nbt.getInteger("soul"):0;
	}

	/**
	 * 死亡时是否逸散灵魂
	 * @param stack 物品
	 * @param world 世界
	 */
	default boolean canDeathDrain(ItemStack stack,World world)
	{
		return true;
	}

	/**
	 * 死亡时逸散数量
	 * @param stack 物品
	 * @param world 世界
	 * @return 逸散量
	 */
	default int countDeathDrain(ItemStack stack, World world)
	{
		return countSoul(stack);
	}

	/**
	 * 死亡逸散事件<br>
	 * 不需要在这个方法内手动执行消耗方法, 这由我们的事件统一执行
	 * @param stack 物品
	 * @param entity 实体
	 * @param point 死亡时逸散了多少灵魂
	 * @param event 死亡事件
	 * @return 是否中断死亡逸散结算
	 */
	default boolean onDeathDrain(ItemStack stack, EntityLivingBase entity, int point, LivingDeathEvent event)
	{
		return false;
	}

	default int deathDrainPriority(ItemStack stack)
	{
		return Common;
	}


}
