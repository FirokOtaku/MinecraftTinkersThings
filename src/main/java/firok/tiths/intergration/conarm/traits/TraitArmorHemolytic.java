package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitHemolytic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 溶血 - 护甲
 */
public class TraitArmorHemolytic extends TraitHemolytic implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(!player.world.isRemote && newDamage>limitRepairDamage/2 && canTrigger(player.world,rate))
		{
			ArmorHelper.healArmor(armor,(int)(2*newDamage/factorRepairDamage),player,0); // fixme slot参数现在是随便写了一个0
		}
		return newDamage;
	}
}
