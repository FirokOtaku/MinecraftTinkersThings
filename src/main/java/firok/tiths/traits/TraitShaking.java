package firok.tiths.traits;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitShaking;
import static firok.tiths.common.Keys.nameTraitShaking;

// 撼击
public class TraitShaking extends AbstractTrait
{
	public TraitShaking()
	{
		super(nameTraitShaking, colorTraitShaking);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);

		if(wasHit && player.isServerWorld())
		{
			target.motionX = target.motionZ = 0;
			target.motionY = 0.4;
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,30,2));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,30,2));
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		super.applyEffect(rootCompound, modifierTag);
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		data.speed*=0.85f;
		data.attackSpeedMultiplier*=0.85f;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
