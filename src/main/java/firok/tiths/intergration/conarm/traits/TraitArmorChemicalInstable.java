package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitChemicalInstable;
import static firok.tiths.common.Keys.nameTraitChemicalInstable;
import static firok.tiths.traits.TraitChemicalInstable.boom;
import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 不稳定化合 - 护甲
 */
public class TraitArmorChemicalInstable extends AbstractArmorTrait
{
	public TraitArmorChemicalInstable()
	{
		super(nameTraitChemicalInstable,colorTraitChemicalInstable);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(canDealWith(source,true,null,null,null,null) && canTrigger(player.world, Configs.ArmorTraits.rate_chemical_instable_hit))
		{
			boom(player,player.posX,player.posY,player.posZ);
		}
		return newDamage;
	}
}
