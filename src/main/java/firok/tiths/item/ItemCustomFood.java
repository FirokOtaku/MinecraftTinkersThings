package firok.tiths.item;

import firok.tiths.util.InnerActions;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCustomFood extends ItemFood
{
	public ItemCustomFood(int food, float saturation, boolean isWolfFood) {
		super(food,saturation,isWolfFood);
//		this.setCreativeTab(CreativeTabs.FOOD);
	}
	public ItemCustomFood(int food, boolean isWolfFood) {
		this(food, 0.6F, isWolfFood);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		InnerActions.addInformation(this,tooltip,flagIn);
	}
}
