package firok.tiths.modifiers;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.tools.modifiers.ToolModifier;

import static firok.tiths.common.Keys.colorTraitDrilled;
import static firok.tiths.common.Keys.nameTraitDrilled;

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
		NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
		float attack=tag.getFloat(Tags.ATTACK);
		float speed=tag.getFloat(Tags.ATTACKSPEEDMULTIPLIER);
		float mineSpeed=tag.getFloat(Tags.MININGSPEED);
		int modifiers=tag.getInteger(Tags.TOOL_MODIFIERS);
		int freeModifiers=tag.getInteger(Tags.FREE_MODIFIERS);
		int durability=tag.getInteger(Tags.DURABILITY);

		attack=Math.max(0.05f,attack-2);
		speed+=0.2f;
		mineSpeed+=0.2f;
		modifiers+=2;
		freeModifiers+=2;
		durability=Math.max(1,durability-650);

		tag.setFloat(Tags.ATTACK, attack);
		tag.setFloat(Tags.ATTACKSPEEDMULTIPLIER, speed);
		tag.setFloat(Tags.MININGSPEED, mineSpeed);
		tag.setInteger(Tags.TOOL_MODIFIERS,modifiers);
		tag.setInteger(Tags.FREE_MODIFIERS, freeModifiers);
		tag.setInteger(Tags.DURABILITY,durability);

		TagUtil.setToolTag(rootCompound, tag);
	}
}
