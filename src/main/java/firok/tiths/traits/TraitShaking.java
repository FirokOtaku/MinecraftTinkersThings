package firok.tiths.traits;

import firok.tiths.common.SoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;

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
			target.motionY = ((target.motionY<=0)?(0.4):(target.motionY+0.4));
			target.isAirBorne=true;
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,30,2));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS,30,2));

			player.world.playSound(null,target.getPosition(), SoundEvents.effectShake, SoundCategory.MASTER,1,1);
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		super.applyEffect(rootCompound, modifierTag);
		NBTTagCompound tag = TagUtil.getToolTag(rootCompound);
		float speed=tag.getFloat(Tags.ATTACKSPEEDMULTIPLIER);
		float mineSpeed=tag.getFloat(Tags.MININGSPEED);

		speed*=0.85f;
		mineSpeed*=0.85f;

		tag.setFloat(Tags.ATTACKSPEEDMULTIPLIER, speed);
		tag.setFloat(Tags.MININGSPEED, mineSpeed);
	}
}
