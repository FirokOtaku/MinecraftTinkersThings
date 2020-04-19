package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.gui.Guis;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 残页
 */
public class ItemPage extends Item
{
	final String key;
	final ResourceLocation background;
	public ItemPage(String key, ResourceLocation background)
	{
		this.key=key;
		this.background=background;
	}
	public String pageKey(World world,EntityPlayer player, ItemStack stack)
	{
		return key;
	}
	public ResourceLocation background()
	{
		return new ResourceLocation(TinkersThings.MOD_ID,"textures/gui/page1.png");
	}

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		playerIn.openGui(TinkersThings.INSTANCE, Guis.GUI_PAGE,worldIn,0,0,0);
		return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
	}
}
