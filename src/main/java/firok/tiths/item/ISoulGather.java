package firok.tiths.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

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

	@SideOnly(Side.CLIENT)
	default void addGatherInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
	{
		int dropBase=this.soulDropBase(stack);
		if(dropBase>0) list.add(I18n.format("tooltip.tiths.soul_drop",dropBase));
	}
}
