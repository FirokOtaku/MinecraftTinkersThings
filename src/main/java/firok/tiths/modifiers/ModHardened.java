package firok.tiths.modifiers;

import firok.tiths.util.InnerActions;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
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
		InnerActions.apply(rootCompound, modifierTag, ModHardened::applyInner);
	}

	public static void applyInner(NBTTagCompound rootCompound, ToolNBT data,int level,boolean harvest,boolean weapon,boolean launcher)
	{
		while(level-->0)
		{
			data.durability+=65;

			if(harvest)
			{
				data.speed*=0.95;
				data.speed-=0.05;
				if(data.speed<0.05) data.speed=0.05f;
			}

			if(weapon)
			{
				data.attackSpeedMultiplier*=0.95;
				data.attackSpeedMultiplier-=0.05;
				if(data.attackSpeedMultiplier<0.05) data.attackSpeedMultiplier=0.05f;

				data.attack+=1.25;
			}

			TagUtil.setToolTag(rootCompound, data.get());

			if(launcher)
			{
				ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
				launcherData.drawSpeed*=0.95;
				launcherData.drawSpeed-=0.05;
				if(launcherData.drawSpeed<0.05) launcherData.drawSpeed=0.05f;
				TagUtil.setToolTag(rootCompound, launcherData.get());
			}
		}
	}
}
