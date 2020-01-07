package firok.tiths.motifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import static firok.tiths.common.Keys.*;

// 打孔
public class ModDrilled extends ToolModifier
{
	public ModDrilled()
	{
		super(nameTraitDrilled, colorTraitDrilled);

		addAspects(new ModifierAspect.MultiAspect(this, 5, 1, 1));
	}

	@Override
	protected boolean canApplyCustom(ItemStack stack)
	{
		ToolNBT data=TagUtil.getToolStats(TagUtil.getTagSafe(stack));
		return data.durability>650 && data.attack>2;
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		if(data.durability>650) data.durability-=650;
		if(data.attack>2) data.attack-=2;
		data.speed+=0.2f;
		data.attackSpeedMultiplier+=0.2;
		data.modifiers+=2;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
