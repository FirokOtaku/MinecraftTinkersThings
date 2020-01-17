package firok.tiths.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

// 右键提供经验值的消耗品
public class ItemXP extends Item
{
	boolean isLv;
	int amount;
	public ItemXP(boolean isLv,int amount)
	{
		super();
		this.isLv=isLv;
		this.amount=amount;
	}
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stack=player.getHeldItem(hand);
		if(stack.getItem() != this) return new ActionResult<>(EnumActionResult.PASS,stack);

		// 提供经验
		if(isLv) player.addExperienceLevel(amount);
		else player.addExperience(amount);

		// 消耗品数量-1
		if(!player.isCreative())
		{
			stack.setCount(stack.getCount()-1);
		}

		return new ActionResult<>(EnumActionResult.SUCCESS,player.getHeldItem(hand));
//		return super.onItemRightClick(p_onItemRightClick_1_, p_onItemRightClick_2_, p_onItemRightClick_3_);
	}
}
