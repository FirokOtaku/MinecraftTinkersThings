package firok.tiths.item;

import baubles.api.BaubleType;
import firok.tiths.util.InnerActions;
import net.minecraft.client.renderer.entity.RenderEntityItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class AbstractSoulBauble extends ItemBauble implements ISoulStore,ISoulGather
{
	public AbstractSoulBauble(BaubleType type)
	{
		super(type);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
	{
		addStoreInformation(stack, world, list, flag);
		addGatherInformation(stack, world, list, flag);
		InnerActions.addInformation(this,list,flag);
	}
}
