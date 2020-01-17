package firok.tiths.item;

import firok.tiths.util.Actions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static firok.tiths.util.Predicates.canTick;

// 折跃之石
public class ItemGatewayGem extends Item
{
	public int range;
	public ItemGatewayGem(int range)
	{
		this.range=range;
		this.setMaxDamage(4);
		this.setMaxStackSize(1);
		this.setHasSubtypes(false);
		this.setNoRepair();
	}
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int slot, boolean inHand)
	{
//		super.onUpdate(stack, world, entity, slot, inHand);
		if(canTick(world,160,2))
		{
			int damage=stack.getItemDamage();

			if(damage<0) stack.setItemDamage(damage+1);
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);
		this.setMaxDamage(100);

		int damage=stackHeld.getItemDamage();
		if(damage==0) damage=100;
		if(damage!=100) return new ActionResult<>(EnumActionResult.FAIL,stackHeld);

		Actions.CauseGatewayTeleport(player,range);
		stackHeld.setItemDamage(0);
		return new ActionResult<>(EnumActionResult.SUCCESS, stackHeld);
	}
}
