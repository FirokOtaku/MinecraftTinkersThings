package firok.tiths.item;

import firok.tiths.util.SoulUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSoul extends ItemCustom
{
	public ItemSoul()
	{
		super();
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		SoulUtil.chargeSoul(playerIn,stack.getCount()*1000);
		stack.setCount(0);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(entityIn instanceof EntityPlayer) SoulUtil.chargeSoul((EntityPlayer) entityIn,stack.getCount()*1000);
		stack.setCount(0);
	}
}
