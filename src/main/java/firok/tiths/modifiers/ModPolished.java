package firok.tiths.modifiers;

import firok.tiths.util.InnerActions;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
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
		InnerActions.apply(rootCompound, modifierTag, ModPolished::applyInner);
	}

	public static void applyInner(NBTTagCompound rootCompound, NBTTagCompound modifierTag, ToolNBT data,int level,boolean harvest,boolean weapon,boolean launcher)
	{
		while(level-->0)
		{
			data.durability=Math.max(1,(int)(data.durability*0.95f));

			if(harvest)
			{
				data.speed*=1.05;
				data.speed+=0.1;
			}

			if(weapon)
			{
				data.attackSpeedMultiplier*=1.05;
				data.attackSpeedMultiplier+=0.1;

				data.attack-=1.25;
				if(data.attack<0.05) data.attack=0.05f;
			}

			TagUtil.setToolTag(rootCompound, data.get());

			if(launcher)
			{
				ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
				launcherData.drawSpeed*=1.05;
				launcherData.drawSpeed+=0.1;
				TagUtil.setToolTag(rootCompound, launcherData.get());
			}
		}
	}
}
