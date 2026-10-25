package firok.tiths.item;

import firok.tiths.TinkersThings;
import firok.tiths.util.InnerActions;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 叶绿提灯
 */
public class ItemChloroplastLight extends ItemCustom
{
	public ItemChloroplastLight()
	{
		super();
		this.setMaxStackSize(1);
		this.setHasSubtypes(false);
		this.setNoRepair();
	}

	public static byte getFactor(ItemStack stack)
	{
		NBTTagCompound nbt=InnerActions.getNBT(stack);
		return nbt.hasKey("factor")?nbt.getByte("factor"):0;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
	{
		stack.setItemDamage(5);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if( entityIn!=null && entityIn.isInWater())
		{
			byte factor=getFactor(stack);
			if(factor==0) return;

			if(factor>0)
			{
				entityIn.motionY+=factor*0.025;
			}
			else
			{
				entityIn.motionY+=factor*0.005;
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack stack=playerIn.getHeldItem(handIn);

		NBTTagCompound nbt=InnerActions.getNBT(stack);
		byte factor=nbt.hasKey("factor")?nbt.getByte("factor"):0;
		byte temp= (byte) (playerIn.isSneaking()?1:-1);
		factor+=temp;


		if(factor>10) factor=10;
		else if(factor<-10) factor=-10;


		if(!worldIn.isRemote)
		{
			TinkersThings.log("clicked "+factor);
		}

		nbt.setByte("factor",factor);

		return ActionResult.newResult(EnumActionResult.SUCCESS,stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		super.addInformation(stack, worldIn, list, flagIn);
		list.add(I18n.format("item.tiths.chloroplast_light.factor", getFactor(stack)));
	}
}
