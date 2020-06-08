package firok.tiths.item;

import baubles.api.BaubleType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemSoulGatherBauble extends ItemBauble implements ISoulGather
{
	int baseDrop,extraDrop;
	public ItemSoulGatherBauble(BaubleType type, int baseDrop, int extraDrop)
	{
		super(type);
		this.baseDrop=baseDrop;
		this.extraDrop=extraDrop;
	}

	@Override
	public int soulDropBase(ItemStack stack)
	{
		return baseDrop;
	}

	@Override
	public int soulDropExtra(ItemStack stack, EntityLivingBase wearer, EntityLivingBase dead)
	{
		return extraDrop;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		addGatherInformation(stack, worldIn, list, flagIn);
		super.addInformation(stack, worldIn, list, flagIn);
	}
}
