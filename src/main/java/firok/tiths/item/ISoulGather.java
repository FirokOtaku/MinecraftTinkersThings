package firok.tiths.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

/**
 * 增加灵魂掉落的物品
 */
public interface ISoulGather
{
	/**
	 * 计算基础灵魂掉落
	 * @param stack 物品
	 */
	default int soulDropBase(ItemStack stack)
	{
		return 0;
	}

	/**
	 * 计算额外灵魂掉落
	 * @param stack 物品
	 * @param wearer 佩戴者
	 * @param dead 目标
	 */
	default int soulDropExtra(ItemStack stack, EntityLivingBase wearer, EntityLivingBase dead)
	{
		return 0;
	}
}
