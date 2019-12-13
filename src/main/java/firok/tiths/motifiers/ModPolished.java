package firok.tiths.motifiers;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import static firok.tiths.common.Keys.colorTraitPolished;
import static firok.tiths.common.Keys.nameTraitPolished;

// 打磨
public class ModPolished extends ToolModifier
{
	public ModPolished()
	{
		super(nameTraitPolished, colorTraitPolished);

		addAspects(new ModifierAspect.MultiAspect(this, 5, 1, 1));
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		data.attack= Math.max(0.1f, data.attack-1.25f);
		data.durability= (int)Math.max(1, data.durability*0.95f);
		data.speed*=1.05f;
		data.attackSpeedMultiplier*=1.05f;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
