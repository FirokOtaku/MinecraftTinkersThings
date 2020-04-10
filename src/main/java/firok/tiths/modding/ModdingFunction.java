package firok.tiths.modding;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * 魔改工具具体方法
 */
public interface ModdingFunction
{
	String id();
	void mod(ItemStack stack, EntityPlayer player, ToolInfo toolInfo);
}
