package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

/**
 * 激流推进器
 */
public class ItemTorrentialThruster extends ItemCustom
{
	public ItemTorrentialThruster()
	{
		super();
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

//	@Override
//	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
//	{
//		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
//	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack heldItem=player.getHeldItem(hand);

		if(player.isInWater()/* && heldItem.getItem()==this */)
		{

			Vec3d forward=player.getForward();
			double factor=0.65;

			player.motionX += forward.x * factor;
			player.motionY += forward.y * factor + 0.1;
			player.motionZ += forward.z * factor;

//			player.setActiveHand(hand);
//			player.swingProgressInt = 0;
//			player.isSwingInProgress = false;
		}

//		return super.onItemRightClick(world, player, hand);
		return new ActionResult<>(EnumActionResult.FAIL, heldItem);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
	{

	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return Integer.MAX_VALUE;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this,list,flagIn);
	}


}
