package firok.tiths.modding;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 一份具体的魔改配置
 */
public class ModdingInfo
{
	public String type()
	{
		return type;
	}
	private String type;
	private ToolInfo toolinfo;
	private List<ModdingFunction> functions;

	public ModdingInfo(String type,ToolInfo toolinfo,List<ModdingFunction> functions)
	{
		this.type=type;
		this.toolinfo=toolinfo.copy();
		this.functions=new ArrayList<>(functions);
	}

	public boolean equalsToolInfo(ModdingInfo info)
	{
		return this.toolinfo.compareTo(info.toolinfo)==0;
	}
	public boolean match(ToolInfo toolinfo)
	{
		return this.toolinfo.compareTo(toolinfo)==0;
	}
	public void mod(ItemStack stack, EntityPlayer player, ToolInfo toolInfo)
	{
		for(ModdingFunction function:functions)
		{
			function.mod(stack, player, toolInfo);
		}
	}
}
