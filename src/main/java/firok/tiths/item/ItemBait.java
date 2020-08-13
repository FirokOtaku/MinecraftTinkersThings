package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBait extends ItemCustom
{
	Block bait;
	public ItemBait(Block blockBait)
	{
		super();
		this.bait=blockBait;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);
		BlockPos posTarget=pos.offset(side);
		if(world.isAirBlock(posTarget))
		{
//			if(!player.isCreative()) stackHeld.shrink(1);
			// fixme 几率损耗

			world.setBlockState(posTarget, bait.getDefaultState());

			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		list.add( I18n.format("tooltip.tiths.item_bait") );
		InnerActions.addInformation(this,list,flagIn);
	}
}
