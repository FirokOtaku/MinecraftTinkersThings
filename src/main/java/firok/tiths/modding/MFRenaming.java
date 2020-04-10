package firok.tiths.modding;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * 为工具重命名
 */
public class MFRenaming implements ModdingFunction
{
	@Override
	public String id()
	{
		return "tiths:mf_renaming";
	}

	public final String name;
	public MFRenaming(String name)
	{
		this.name=name;
	}

	@Override
	public void mod(ItemStack stack, EntityPlayer player, ToolInfo toolInfo)
	{
		stack.setStackDisplayName(name);
	}
}
