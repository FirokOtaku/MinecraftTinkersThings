package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.gui.Guis;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * 纸页 - 语言文件相关
 */
public class ItemLangPage extends ItemCustom implements IPage
{
	public ItemLangPage()
	{
		this.setHasSubtypes(true);
	}
	private List<ItemStack> subs=new ArrayList<>();
	public void addSubItem(ItemStack stack)
	{
		subs.add(stack);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			items.addAll(subs);
		}
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.openGui(TinkersThings.INSTANCE, Guis.GUI_PAGE,worldIn,0,0,0);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}

	@Override
	public void setText(ItemStack stack, String langkey)
	{
		if(stack==null) return;
		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);

		assert nbt!=null;
		nbt.setString("langkey",langkey);
	}

	@Override
	public String getText(ItemStack stack)
	{
		if(stack==null) return null;

		NBTTagCompound nbt=stack.hasTagCompound()?stack.getTagCompound():new NBTTagCompound();
		stack.setTagCompound(nbt);

		assert nbt!=null;

		if(nbt.hasKey("langkey"))
		{
			String text= I18n.format(nbt.getString("langkey"));
			return text.replace("<br>","\n");
		}
		else return null;
	}
}
