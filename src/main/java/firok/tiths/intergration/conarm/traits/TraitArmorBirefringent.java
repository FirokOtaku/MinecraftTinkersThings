package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitBirefringent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.Optional;

import static firok.tiths.common.DamageSources.BirefringentDamage;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 双折 - 护甲
 */
public class TraitArmorBirefringent extends TraitBirefringent implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		Optional.ofNullable(player.getLastAttackedEntity())
				.filter(EntityLivingBase::isEntityAlive)
				.filter(enlb->canTrigger(enlb.world,rate))
				.ifPresent(enlb->{
					enlb.hurtResistantTime=0;
					enlb.attackEntityFrom(BirefringentDamage,newDamage);
				});
		return newDamage;
	}
}
