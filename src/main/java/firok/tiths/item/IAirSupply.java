package firok.tiths.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public interface IAirSupply
{
	/**
	 * 最大能补充到多少氧气
	 */
	default int getAirMax(ItemStack stack)
	{
		return 300;
	}

	/**
	 * 单次提供的氧气
	 */
	int getAir(ItemStack stack);

	default void onAirSupply(ItemStack stack, EntityLivingBase entityLiving)
	{
		int air=getAir(stack);
		int airMax=getAirMax(stack);

		int airTemp=entityLiving.getAir();
		airTemp=Math.min(airTemp+air,airMax);

		entityLiving.setAir( airTemp );

		stack.shrink(1);
	}

	/**
	 * 检查能否经由头盔为玩家补充氧气. 注意: 这个方法需要由特性来调用
	 * @param stack 物品
	 * @param helmet 尝试补充氧气的头盔. 可能是有特性的匠魂护甲, 或者是氧气面罩
	 */
	boolean canAutoSupply(ItemStack stack,ItemStack helmet,EntityLivingBase enlb);
}
