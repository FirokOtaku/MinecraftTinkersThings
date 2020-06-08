package firok.tiths.item;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public interface ISoulWatch
{
	@SideOnly(Side.CLIENT)
	default void addWatchInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag)
	{
		list.add(I18n.format("tooltip.tiths.soul_watching"));
	}
}

