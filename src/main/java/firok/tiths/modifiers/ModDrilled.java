package firok.tiths.modifiers;

import firok.tiths.util.InnerActions;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
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
		InnerActions.apply(rootCompound, modifierTag, ModDrilled::applyInner);
	}

	public static void applyInner(NBTTagCompound rootCompound, ToolNBT data,int level,boolean harvest,boolean weapon,boolean launcher)
	{
		while(level-->0)
		{
			data.durability-=650;
			if(data.durability<0) data.durability=1;

			data.modifiers+=2;

			if(harvest)
			{
				data.speed+=0.2;
			}

			if(weapon)
			{
				data.attackSpeedMultiplier+=0.2;
				data.attack-=2;
				if(data.attack<0.05) data.attack=0.05f;
			}

			TagUtil.setToolTag(rootCompound, data.get());

			if(launcher)
			{
				ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
				launcherData.drawSpeed+=0.2;
				TagUtil.setToolTag(rootCompound, launcherData.get());
			}
		}
	}
}
