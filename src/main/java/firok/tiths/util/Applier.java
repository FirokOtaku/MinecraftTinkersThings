package firok.tiths.util;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tools.ToolNBT;

public interface Applier
{
	void apply(NBTTagCompound rootCompound, ToolNBT data, int level, boolean harvest, boolean weapon, boolean launcher);
}