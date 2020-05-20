package firok.tiths.common;

import firok.tiths.modifiers.ModDrilled;
import firok.tiths.modifiers.ModHardened;
import firok.tiths.modifiers.ModPolished;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

public final class Modifiers
{
	private Modifiers(){}

	public static final ToolModifier drilled=new ModDrilled(); // 打孔
	public static final ToolModifier hardened=new ModHardened(); // 硬化
	public static final ToolModifier polished=new ModPolished();// 打磨
	static
	{
		drilled.addItem(Items.driller);
		hardened.addItem(Items.hardener);
		polished.addItem(Items.polisher);
	}

	public static void log()
	{
	}
}
