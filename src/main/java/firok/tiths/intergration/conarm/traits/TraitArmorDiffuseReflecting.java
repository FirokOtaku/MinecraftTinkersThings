package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.DamageSources;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Selectors;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

import static firok.tiths.common.DamageSources.TypeDiffuseReflecting;
import static firok.tiths.common.Keys.colorTraitDiffuseReflecting;
import static firok.tiths.common.Keys.nameTraitDiffuseReflecting;

/**
 * 漫反射 - 护甲
 */
public class TraitArmorDiffuseReflecting extends AbstractArmorTrait
{
	public TraitArmorDiffuseReflecting()
	{
		super(nameTraitDiffuseReflecting,colorTraitDiffuseReflecting);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		try
		{
			if(TypeDiffuseReflecting.equals(source.getDamageType())) return newDamage;

			List<EntityLivingBase> ens= (List)EntityFinders.Nearby(player,5, Selectors.livingBaseAlive);
			for(EntityLivingBase enlb:ens)
			{
				enlb.hurtResistantTime=0;
				enlb.attackEntityFrom(DamageSources.DiffuseReflecting(player),damage/4);
			}

			int count=ens.size();
			if(count<=0) return newDamage;
			else if(count>=10) return newDamage-damage*0.4f;
			else return newDamage-damage*0.04f*count;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return newDamage;
		}
	}
}
