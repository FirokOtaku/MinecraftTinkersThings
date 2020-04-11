package firok.tiths.modding;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * 为工具重命名
 */
public class MFRenaming implements ModdingFunction
{
	public static final String TYPE="tiths:mf_renaming";
	public static final int COUNT=1;
	@Override
	public String id()
	{
		return TYPE;
	}

	public final String name;
	public MFRenaming(String name)
	{
		this.name=name;
	}
	public MFRenaming(String[] name)
	{
		this(name[0]);
	}

	@Override
	public void mod(ItemStack stack, EntityPlayer player, ToolInfo toolInfo)
	{
		stack.setStackDisplayName(name);
	}
}
