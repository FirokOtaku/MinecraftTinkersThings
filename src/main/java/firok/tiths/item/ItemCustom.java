package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 自定义物品
 */
public class ItemCustom extends Item
{
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this,list,flagIn);
	}

//	@Override
//	public EnumRarity getRarity(ItemStack stack)
//	{
//		return super.getRarity(stack);
//	}

	IRarity rarity=EnumRarity.COMMON;

	public ItemCustom setRarity(IRarity rarity)
	{
		this.rarity=rarity;
		return this;
	}
	@Override
	public IRarity getForgeRarity(ItemStack stack)
	{
		return rarity;
	}
}
