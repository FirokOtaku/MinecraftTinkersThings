package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitAntiGrav;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;

import java.util.Optional;

/**
 * 反重力 - 护甲
 */
public class TraitArmorAntiGrav extends TraitAntiGrav implements IAbstractArmorTrait
{
	@Override
	public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical)
	{
		super.onHit(tool, player, target, damage, isCritical);
	}

	@Override
	public float onDamaged(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingDamageEvent evt)
	{
		Optional.ofNullable(player.getLastAttackedEntity())
				.filter(EntityLivingBase::isEntityAlive)
				.ifPresent(enlb->enlb.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 80)));
		return newDamage;
	}

	@Override
	public void onFalling(ItemStack armor, EntityPlayer player, LivingFallEvent evt)
	{
		evt.setDamageMultiplier(evt.getDamageMultiplier() * 0.75f);
	}

	@Override
	public void onJumping(ItemStack armor, EntityPlayer player, LivingEvent.LivingJumpEvent evt)
	{

	}
}