package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 提供氧气的消耗品
 */
public class ItemAirSupply extends Item implements IAirSupply
{
	int air;
	int airMax;
	boolean canAutoSupply=false;
	public ItemAirSupply(int air)
	{
		this(air,300);
	}
	public ItemAirSupply(int air,int airMax)
	{
		super();
		this.air=air;
		this.airMax=airMax;
	}

	@Deprecated
	public ItemAirSupply setAutoSupply()
	{
		this.canAutoSupply=true;
		return this;
	}

	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		onAirSupply(stack, entityLiving);
		return stack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 6;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	/**
	 * Called when the equipped item is right clicked.
	 */
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		playerIn.setActiveHand(handIn);
		return new ActionResult<>(EnumActionResult.SUCCESS, itemstack);
	}

	@Override
	public int getAirMax(ItemStack stack)
	{
		return airMax;
	}

	@Override
	public int getAir(ItemStack stack)
	{
		return air;
	}

	@Override
	public boolean canAutoSupply(ItemStack stack, ItemStack helmet, EntityLivingBase enlb)
	{
		return canAutoSupply && enlb.getAir() + getAir(stack) <= getAirMax(stack);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this, tooltip, flagIn);
	}
}
