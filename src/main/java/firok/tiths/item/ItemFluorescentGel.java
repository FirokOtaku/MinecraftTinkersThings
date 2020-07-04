package firok.tiths.item;

import firok.tiths.common.Fluids;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Firok
 */
public class ItemFluorescentGel extends ItemCustom
{
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);

		Vec3d vecTarget=player.getForward().scale(2).add(player.getPositionVector());
		BlockPos posTarget=new BlockPos(vecTarget.x, vecTarget.y + player.eyeHeight,vecTarget.z);

		IBlockState stateTarget=world.getBlockState(posTarget);
		Block blockTarget=stateTarget.getBlock();
		boolean replacable= blockTarget == Blocks.WATER;

		if(replacable)
		{
			player.swingArm(hand);
			if(!player.isCreative()) stackHeld.shrink(1);

			world.setBlockState(posTarget, Fluids.shiningGel.getBlock().getDefaultState() );
		}


		return ActionResult.newResult(replacable? EnumActionResult.SUCCESS:EnumActionResult.FAIL,stackHeld);
	}
}
