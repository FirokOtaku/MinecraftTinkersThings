package firok.tiths.traits;

import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;

import static firok.tiths.util.Keys.colorTraitClustering;
import static firok.tiths.util.Keys.nameTraitClustering;

// 群簇
public class TraitClustering extends AbstractTrait
{
	private static final float durability =550;
	private static final float factorAtk = 2;
	private static final float factorMotiSpd = 1; // 攻击速度 (因数)
	private static final float factorMineSpd = 2; // 挖掘速度
	private static final float factorDrawSpd= 0.08f; // 拉弓速度

	public TraitClustering()
	{
		super(nameTraitClustering, colorTraitClustering);
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
		super.applyEffect(rootCompound, modifierTag);

		// todo 这里的公式肯定还要改

		// add the attack speed boost
		ToolNBT data = TagUtil.getToolStats(rootCompound);

		// 计算因数 大于指定耐久度的百分比
		float factor=Math.abs(data.durability / durability - 1);

		if(data.durability- durability>0) // 大于指定耐久度
		{
			data.attack += factor * factorAtk;
		}
		else // 小于指定耐久度
		{
			factor=Math.max(factor,0.6f);
			data.speed += factor * factorMineSpd;
			data.attackSpeedMultiplier += factor * factorMotiSpd;

			if(TinkerUtil.hasCategory(rootCompound, Category.LAUNCHER)) {
				ProjectileLauncherNBT launcherData = new ProjectileLauncherNBT(TagUtil.getToolTag(rootCompound));
				launcherData.drawSpeed += launcherData.drawSpeed * factorDrawSpd;
				TagUtil.setToolTag(rootCompound, launcherData.get());
			}
		}

		TagUtil.setToolTag(rootCompound, data.get());
	}

//	@Override
//	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
//		// 10% bonus speed
//		event.setNewSpeed(event.getNewSpeed() * (1 + bonus));
//	}

//	@Override
//	public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
//		String loc = String.format(LOC_Extra, getModifierIdentifier());
//
//		return ImmutableList.of(Util.translateFormatted(loc, Util.dfPercent.format(bonus)));
//	}
}