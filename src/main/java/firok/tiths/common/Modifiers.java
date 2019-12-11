package firok.tiths.common;

import firok.tiths.motifiers.ModHardened;
import firok.tiths.motifiers.ModPolished;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class Modifiers
{
	public static final ToolModifier hardened=new ModHardened(); // 硬化
	public static final ToolModifier polished=new ModPolished();// 打磨
	static
	{
		hardened.addItem(Items.hardener);
		polished.addItem(Items.polisher);
	}

	public static void log()
	{
	}
}
