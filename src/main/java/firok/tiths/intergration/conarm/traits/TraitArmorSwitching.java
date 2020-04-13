package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.Actions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitSwitching;
import static firok.tiths.common.Keys.nameTraitSwitching;

/**
 * 换位 - 护甲
 */
public class TraitArmorSwitching extends AbstractArmorTrait
{
	public TraitArmorSwitching()
	{
		super(nameTraitSwitching,colorTraitSwitching);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(source instanceof EntityDamageSource)
		{
			EntityDamageSource ends=(EntityDamageSource)source;
			Entity entity=ends.getTrueSource();
			if(entity instanceof EntityLivingBase)
			{
				Actions.CauseSwitching(player,(EntityLivingBase) entity);
			}
		}
		return newDamage;
	}
}
