package firok.tiths.item;

import firok.tiths.common.Blocks;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 气罐
 */
public class ItemAirTank extends Item implements IAirSupply
{
	public ItemAirTank()
	{
		super();
		this.setMaxStackSize(1);
		this.setMaxDamage(16);
	}

	@Override
	public int getAir(ItemStack stack)
	{
		return stack.getItemDamage()<16?300:0;
	}

	@Override
	public void onAirSupply(ItemStack stack, EntityLivingBase entityLiving)
	{
		int damage=stack.getItemDamage();
		if(damage>=16) return;

		stack.damageItem(1,entityLiving);
		entityLiving.setAir(300);
	}

	@Override
	public boolean canAutoSupply(ItemStack stack, ItemStack helmet, EntityLivingBase enlb)
	{
		int air=getAir(stack);
		return stack.getItemDamage()<16 && air > 0 && enlb.getAir() + air <= getAirMax(stack);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if( checkPump(world,pos) )
		{
			ItemStack stackHeld=player.getHeldItem(hand);
			stackHeld.setItemDamage(0);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.FAIL;
		}
	}

	public static boolean checkPump(World world,BlockPos center)
	{
		if(world.getBlockState(center).getBlock()!=Blocks.blockAirPump) return false;
		for(EnumFacing facing:EnumFacing.VALUES)
		{
			if (world.isAirBlock( center.offset(facing) )) return true;
		}
		return false;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this, tooltip, flagIn);
	}
}
