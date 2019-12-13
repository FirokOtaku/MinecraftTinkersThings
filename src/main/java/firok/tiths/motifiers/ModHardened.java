package firok.tiths.motifiers;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import static firok.tiths.common.Keys.colorTraitHardened;
import static firok.tiths.common.Keys.nameTraitHardened;

// 硬化
public class ModHardened extends ToolModifier
{
	public ModHardened()
	{
		super(nameTraitHardened, colorTraitHardened);

		addAspects(new ModifierAspect.MultiAspect(this, 5, 1, 1));
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		data.attack+=1.25f;
		data.durability+=65;
		data.speed*=0.95f;
		data.attackSpeedMultiplier*=0.95f;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
