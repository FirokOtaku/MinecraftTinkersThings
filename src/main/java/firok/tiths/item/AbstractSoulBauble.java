package firok.tiths.item;

import baubles.api.BaubleType;
import firok.tiths.util.InnerActions;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public abstract class AbstractSoulBauble extends ItemBauble implements ISoulStore,ISoulGather
{
	public AbstractSoulBauble(BaubleType type)
	{
		super(type);
	}

	@Override
	public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
	{
		InnerActions.addInformation(this,list,flag);
		int countStore=this.countSoul(stack);
		int countDrain=canDeathDrain(stack,world)?countDeathDrain(stack,world):0;
		list.add(I18n.format("tooltip.tiths.soul_storing",countStore,countDrain));

		int dropBase=this.soulDropBase(stack);
		if(dropBase>0) list.add(I18n.format("tooltip.tiths.soul_drop",dropBase));
	}
}
