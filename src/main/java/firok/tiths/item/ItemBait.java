package firok.tiths.item;

import firok.tiths.block.BlockBait;
import firok.tiths.util.Actions;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static firok.tiths.util.Predicates.canTrigger;

public class ItemBait extends ItemCustom
{
	Block bait;
	public ItemBait(Block blockBait)
	{
		super();
		this.bait=blockBait;
	}

	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos posBase, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack stackHeld=player.getHeldItem(hand);

		IBlockState stateBase=world.getBlockState(posBase);
		BlockFaceShape shapeBase=stateBase.getBlockFaceShape(world,posBase,side);

		BlockPos posTarget=posBase.offset(side);
		if(shapeBase==BlockFaceShape.SOLID && world.isAirBlock(posTarget))
		{
			if(!world.isRemote)
			{
				if(!player.isCreative() && canTrigger(world.rand,0.125))
				{
					stackHeld.shrink(1);
					Actions.CauseSpawnItem(player,new ItemStack(Items.STRING,4));
				}

				world.setBlockState(posTarget, bait.getDefaultState().withProperty(BlockBait.FACING,side.getOpposite()));
			}

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
