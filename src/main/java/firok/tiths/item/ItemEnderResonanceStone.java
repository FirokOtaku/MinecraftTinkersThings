package firok.tiths.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

public class ItemEnderResonanceStone extends Item
{
	public ItemEnderResonanceStone()
	{
		super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1).rarity(Rarity.UNCOMMON).setNoRepair().isImmuneToFire());
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public static final String KEY_NBT_TYPES = "types";
	public static final String KEY_NBT_ITEMS = "items";

	/**
	 * 向一个末影谐振之石填充物品
	 * @param stackNew 要填充的物品
	 * @param stackStone 末影谐振之石
	 * @param countFullType 最大填充种类数
	 * @return
	 */
	public static boolean addItem(ItemStack stackNew, ItemStack stackStone, final int countFullType)
	{
		final int countNew = stackNew.getCount();
		final Item itemNew = stackNew.getItem();
		CompoundNBT nbtStone = stackStone.getOrCreateTag();

		final int currentTypes = nbtStone.contains(KEY_NBT_TYPES,3) ? nbtStone.getInt(KEY_NBT_TYPES) : 0;
		final boolean isTypeFull = currentTypes >= countFullType;

		ListNBT listItem = nbtStone.getList(KEY_NBT_ITEMS, 10);
		final int sizeList = listItem.size();

		boolean hasStacked = false; // 是否在储存的物品里找到了一个能叠加的
		ItemStack stackExtra = null; // 如果找到了能叠加的stack 是否叠加剩下了一部分

		for(int step = sizeList - 1; step >= 0; step--)
		{
			CompoundNBT nbtItem = listItem.getCompound(step);
			ItemStack stackItem = ItemStack.read(nbtItem);
			final int maxStackSize = stackItem.getMaxStackSize();
			final int currentStackSize = stackItem.getCount();
			final int remainStackSize = maxStackSize - currentStackSize; // 这个stack还能叠加多少
			if(remainStackSize <= 0 || !ItemStack.areItemsEqual(stackItem, stackNew) || !ItemStack.areItemStackTagsEqual(stackItem, stackNew))
				continue; // 满了 或者不能叠加 检查下一个



		}

		return false;
	}
}
