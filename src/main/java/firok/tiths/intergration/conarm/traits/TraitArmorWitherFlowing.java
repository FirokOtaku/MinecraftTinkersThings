package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitWitherFlowing;
import static firok.tiths.common.Keys.nameTraitWitherFlowing;

/**
 * 凋零流动 - 护甲
 */
public class TraitArmorWitherFlowing extends AbstractArmorTrait
{
	public TraitArmorWitherFlowing()
	{
		super(nameTraitWitherFlowing,colorTraitWitherFlowing);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(source instanceof EntityDamageSource)
		{
			EntityDamageSource enDS=(EntityDamageSource)source;
			Entity entity=enDS.getTrueSource();
			if(entity instanceof EntityLivingBase)
			{
				EntityLivingBase enlb=(EntityLivingBase)entity;

				enlb.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, Configs.ArmorTraits.factor_wither_flowing,2));
				enlb.addPotionEffect(new PotionEffect(MobEffects.WITHER, Configs.ArmorTraits.factor_wither_flowing,1));
			}
		}
		return newDamage;
	}
}
