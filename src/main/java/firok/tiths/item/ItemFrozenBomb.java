package firok.tiths.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

/**
 * 冰结弹
 */
public class ItemFrozenBomb extends Item
{
	public ItemFrozenBomb()
	{
		super();
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack=playerIn.getHeldItem(handIn);
		if(stack.getItem()!=this) return new ActionResult<>(EnumActionResult.PASS,stack);

		;

		if(!playerIn.isCreative()) stack.shrink(1);
		return new ActionResult<>(EnumActionResult.SUCCESS,stack);
	}
}
