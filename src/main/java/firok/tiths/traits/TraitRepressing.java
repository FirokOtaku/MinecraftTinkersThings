package firok.tiths.traits;

import firok.tiths.common.Potions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.TagUtil;

import static firok.tiths.common.Keys.colorTraitRepressing;
import static firok.tiths.common.Keys.nameTraitRepressing;

// 压制
public class TraitRepressing extends AbstractTrait
{
	public TraitRepressing()
	{
		super(nameTraitRepressing, colorTraitRepressing);
	}

	@Override
	public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit)
	{
		if(wasHit && target.isServerWorld() && target.isEntityAlive())
		{
			target.addPotionEffect(new PotionEffect(Potions.heavy,80,0));
		}
	}

	@Override
	public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag)
	{
		super.applyEffect(rootCompound, modifierTag);
		ToolNBT data = TagUtil.getToolStats(rootCompound);
		data.speed*=0.85f;
		data.attackSpeedMultiplier*=0.75f;
		TagUtil.setToolTag(rootCompound, data.get());
	}
}
