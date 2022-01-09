package firok.tiths.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class ItemEnderResonanceCube extends Item
{
	public ItemEnderResonanceCube()
	{
		super(new Properties().group(ItemGroup.TOOLS).maxStackSize(1).rarity(Rarity.UNCOMMON).setNoRepair().isImmuneToFire());
	}

	@Override
	public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		super.inventoryTick(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public static final String KEY_NBT_ITEMS = "items";

	/**
	 * 向一个末影谐振之石填充物品
	 * @param stackNew 要填充的物品
	 * @param stackStone 末影谐振之石
	 * @param countFullType 最大填充种类数
	 * @return 叠加之后剩下的物品堆. 如果叠加成功就返回 {@code ItemStack.EMPTY}
	 */
	public static ItemStack addItem(ItemStack stackNew, ItemStack stackStone, final int countFullType)
	{
		try
		{
			final int countNew = stackNew.getCount();
			CompoundNBT nbtStone = stackStone.getOrCreateTag();

			ListNBT listOld;
			if(nbtStone.contains(KEY_NBT_ITEMS, 9))
				listOld = nbtStone.getList(KEY_NBT_ITEMS, 10);
			else
			{
				listOld = new ListNBT();
				nbtStone.put(KEY_NBT_ITEMS, listOld);
			}

			final int sizeOld = listOld.size();
			final boolean isTypeFull = sizeOld >= countFullType;

			boolean hasStacked = false; // 是否在储存的物品里找到了一个能叠加的
			ItemStack stackExtra = null; // 如果找到了能叠加的stack 是否叠加剩下了一部分

			for(int step = sizeOld - 1; step >= 0; step--)
			{
				CompoundNBT nbtItemOld = listOld.getCompound(step);
				ItemStack stackOld = ItemStack.read(nbtItemOld); // 本次尝试叠加的stack
				final int maxStackSize = stackOld.getMaxStackSize(); // 本次尝试叠加stack的最大数量
				final int oldStackSize = stackOld.getCount(); // 本次尝试叠加的stack的当前数量
				final int remainStackSize = maxStackSize - oldStackSize; // 这个stack还能叠加多少
				if(remainStackSize <= 0 || !ItemStack.areItemsEqual(stackOld, stackNew) || !ItemStack.areItemStackTagsEqual(stackOld, stackNew))
					continue; // 满了 或者不能叠加 检查下一个

				// 可以叠加 那就进行一个叠的加
				if(countNew > remainStackSize) // 太多哩 太多哩
				{
					stackOld.setCount(maxStackSize);
					stackExtra = stackNew.copy();
					stackExtra.setCount(countNew - remainStackSize);
				}
				else
				{
					stackOld.setCount(oldStackSize + countNew);
					stackExtra = ItemStack.EMPTY;
				}

				// 把数据写回NBT
				CompoundNBT nbtItemOverride = stackOld.write(new CompoundNBT());
				listOld.set(step, nbtItemOverride);

				hasStacked = true;
			}
			if(hasStacked) // 已经叠上了
			{
				if(stackExtra != ItemStack.EMPTY) // 还有剩下的
				{
					if(isTypeFull) // 不能追加列表了
						return stackExtra;

					// 能追加列表
					listOld.add(stackExtra.write(new CompoundNBT()));
				}
				// 没有剩下的
			}
			else // 没叠上
			{
				if(isTypeFull) // 不能追加列表
					return stackNew;
				// 能追加列表
				listOld.add(stackNew.write(new CompoundNBT()));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return ItemStack.EMPTY;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		try
		{
			if(stack.hasTag())
			{
				CompoundNBT nbt = stack.getTag();
				assert nbt != null;
				if(!nbt.contains(KEY_NBT_ITEMS, 9)) return;

				ListNBT list = nbt.getList(KEY_NBT_ITEMS, 10);
				final int size = list.size();
				for(int step = 0; step < size; step++)
				{
					CompoundNBT nbtItem = list.getCompound(step);
					ItemStack stackItem = ItemStack.read(nbtItem);

					tooltip.add(new StringTextComponent(stackItem.getDisplayName().getString() + " × " + stackItem.getCount()));
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
