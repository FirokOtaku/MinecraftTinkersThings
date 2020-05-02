package firok.tiths.modifiers;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
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
		NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
		float attack=tag.getFloat(Tags.ATTACK);
		float speed=tag.getFloat(Tags.ATTACKSPEEDMULTIPLIER);
		float mineSpeed=tag.getFloat(Tags.MININGSPEED);
		int durability=tag.getInteger(Tags.DURABILITY);

		attack=Math.max(0.05f,attack-1.25f);
		speed*=1.05f;
		mineSpeed*=1.05f;
		durability=Math.max(1,(int)(durability*0.95f));

		tag.setFloat(Tags.ATTACK, attack);
		tag.setFloat(Tags.ATTACKSPEEDMULTIPLIER, speed);
		tag.setFloat(Tags.MININGSPEED, mineSpeed);
		tag.setInteger(Tags.DURABILITY,durability);

		TagUtil.setToolTag(rootCompound, tag);
	}
}
