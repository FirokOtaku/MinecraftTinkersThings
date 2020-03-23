package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitChemicalInstable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 化学不稳定 - 护甲
 */
public class TraitArmorChemicalInstable extends TraitChemicalInstable implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(canTrigger(player.world,0.12f))
		{
			boom(player,player.posX,player.posY,player.posZ);
		}
		return newDamage;
	}
}
