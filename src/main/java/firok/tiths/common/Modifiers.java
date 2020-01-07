package firok.tiths.common;

import firok.tiths.motifiers.*;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public class Modifiers
{
	public static final ToolModifier hardened=new ModHardened(); // 硬化
	public static final ToolModifier polished=new ModPolished();// 打磨
	public static final ToolModifier drilled=new ModDrilled(); // 打孔
	static
	{
		hardened.addItem(Items.hardener);
		polished.addItem(Items.polisher);
		drilled.addItem(Items.driller);
	}

	public static void log()
	{
	}
}
