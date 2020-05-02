package firok.tiths.modifiers;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
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
		NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
		float attack=tag.getFloat(Tags.ATTACK);
		float speed=tag.getFloat(Tags.ATTACKSPEEDMULTIPLIER);
		float mineSpeed=tag.getFloat(Tags.MININGSPEED);
		int durability=tag.getInteger(Tags.DURABILITY);

		attack+=1.25f;
		speed*=0.95f;
		mineSpeed*=0.95f;
		durability+=65;

		tag.setFloat(Tags.ATTACK, attack);
		tag.setFloat(Tags.ATTACKSPEEDMULTIPLIER, speed);
		tag.setFloat(Tags.MININGSPEED, mineSpeed);
		tag.setInteger(Tags.DURABILITY,durability);

		TagUtil.setToolTag(rootCompound, tag);
	}
}
