package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitInky;
import static firok.tiths.common.Keys.nameTraitInky;

/**
 * 墨染
 */
public class TraitInky extends AbstractTrait
{
	public TraitInky()
	{
		super(nameTraitInky, colorTraitInky);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
		if(wasHit && target.isEntityAlive()) {
			target.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		ToolNBT data = TagUtil.getToolStats(rootCompound);

		data.speed*=0.9f;
		data.attackSpeedMultiplier*=0.9f;

		TagUtil.setToolTag(rootCompound, data.get());
	}
}
