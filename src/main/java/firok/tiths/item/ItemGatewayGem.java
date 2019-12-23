package firok.tiths.item;

import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

// 折跃之石
public class ItemGatewayGem extends Item
{
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);
		if(!player.isCreative()) stackHeld.setCount(stackHeld.getCount()-1);
		Actions.CauseGatewayTeleport(player,5); // todo 先弄个距离5测试一下
		return new ActionResult<>(EnumActionResult.SUCCESS, stackHeld);
	}
}
