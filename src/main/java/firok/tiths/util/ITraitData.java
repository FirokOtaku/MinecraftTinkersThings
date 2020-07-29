package firok.tiths.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.utils.TagUtil;

/**
 * 对匠魂额外NBT数据进行读写
 */
@SuppressWarnings("all")
public interface ITraitData<T extends TraitExtraData>
{
	/**
	 * 提供读写key
	 */
	String getDataKey();

	/**
	 * 从物品读取额外数据原始NBT数据<br>
	 * 一定会返回一个<code>NBTTagCompound</code>实例, 但是可能不包含指定数据
	 * @apiNote 仅供内部使用
	 * @deprecated 尽量不要覆写这个方法
	 */
	@Deprecated
	default NBTTagCompound readSpecialDataNBT(ItemStack stack)
	{
		NBTTagCompound tagExtra=TagUtil.getExtraTag(stack);
		String key=getDataKey();
		return tagExtra.getCompoundTag(key);
	}

	/**
	 * 向物品写入额外数据原始NBT数据
	 * @apiNote 仅供内部使用
	 * @deprecated 尽量不要覆写这个方法
	 */
	@Deprecated
	default void writeExtraDataNBT(ItemStack stack,NBTTagCompound extraDataNBT)
	{
		if(stack==null || extraDataNBT==null) return;

		NBTTagCompound tagExtra=TagUtil.getExtraTag(stack);
		String key=getDataKey();

		tagExtra.setTag(key,extraDataNBT);

		TagUtil.setExtraTag(stack,tagExtra);
	}

	/**
	 * 将原始NBT数据转换成Java Bean<br>
	 * 传入的<code>NBTTagCompound</code>一定不会为<code>null</code>, 但是可能不包含指定的数据, 所以实现方法时需要手动判断
	 */
	T readExtraData(NBTTagCompound nbtExtraStat);

	/**
	 * 将Java Bean转换为原始NBT数据<br>
	 * 实现方法一定不能返回<code>null</code>
	 */
	NBTTagCompound writeExtraData(T data);

	/**
	 * 从物品读取数据
	 */
	default T readExtraDataFromStack(ItemStack stack)
	{
		NBTTagCompound nbtExtraData=readSpecialDataNBT(stack); // 先把原始NBT读取出来
		return readExtraData(nbtExtraData); // 然后转换为真实数据
	}

	/**
	 * 向物品写入数据
	 */
	default void writeExtraDataToStack(ItemStack stack,T data)
	{
		NBTTagCompound nbtExtraData=writeExtraData(data); // 先把真实数据转换为NBT
		writeExtraDataNBT(stack,nbtExtraData); // 然后写入物品
	}
}
