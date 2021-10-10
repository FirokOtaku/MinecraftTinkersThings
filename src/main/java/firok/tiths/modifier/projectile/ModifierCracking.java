package firok.tiths.modifier.projectile;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;

/**
 * 崩裂
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/traits/TraitCracking.java
 */
public class ModifierCracking extends Modifier
{
	public ModifierCracking()
	{
		super(0x4d4133);
	}

	@Override
	public void afterBlockBreak(IModifierToolStack tool, int level, ToolHarvestContext context)
	{
		// todo
	}
}
