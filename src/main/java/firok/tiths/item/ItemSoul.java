package firok.tiths.item;

import firok.tiths.entity.item.EntityItemSoul;
import firok.tiths.util.SoulUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;

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
//		if(entityIn instanceof EntityPlayer) SoulUtil.chargeSoul((EntityPlayer) entityIn,stack.getCount()*1000);
//		stack.setCount(0);
	}

	@Override
	public boolean hasCustomEntity(ItemStack stack)
	{
		return true;
	}

	@Override
	public EntityItemSoul createEntity(World world, Entity location, ItemStack itemstack)
	{
		return new EntityItemSoul(world,location.posX,location.posY,location.posZ,itemstack);
	}
}
