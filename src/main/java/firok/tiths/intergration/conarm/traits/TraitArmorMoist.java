package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitMoist;
import static firok.tiths.common.Keys.nameTraitMoist;
import static firok.tiths.util.Predicates.canDealWith;

/**
 * 湿润
 */
public class TraitArmorMoist extends AbstractArmorTrait
{
	public TraitArmorMoist()
	{
		super(nameTraitMoist,colorTraitMoist);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(canDealWith(source,true,null,true,null,null))
			newDamage-=damage/2;
		return newDamage>0?newDamage:0;
	}
}
