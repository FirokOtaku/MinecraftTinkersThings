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

import static firok.tiths.common.Keys.colorTraitPoisonous;
import static firok.tiths.common.Keys.nameTraitArsenicPoisonous;
import static firok.tiths.util.Predicates.canDealWith;

/**
 * 砷毒 - 护甲
 */
public class TraitArmorArsenicPoisonous extends AbstractArmorTrait
{
	public TraitArmorArsenicPoisonous()
	{
		super(nameTraitArsenicPoisonous,colorTraitPoisonous);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(source instanceof EntityDamageSource && canDealWith(source,true,null,null,null,false))
		{

			EntityDamageSource ends=(EntityDamageSource)source;
			Entity entity=ends.getTrueSource();
			if(entity instanceof EntityLivingBase)
			{
				EntityLivingBase enlb=(EntityLivingBase)entity;
				enlb.addPotionEffect(new PotionEffect(MobEffects.POISON, Configs.ArmorTraits.arsenic_poisonous_duration, 1));
			}
		}
		return newDamage;
	}
}
