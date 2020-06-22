package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitSliding;
import static firok.tiths.common.Keys.nameTraitSliding;
import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 软滑 - 护甲
 */
public class TraitArmorSliding extends AbstractArmorTrait
{
	public TraitArmorSliding()
	{
		super(nameTraitSliding,colorTraitSliding);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(canDealWith(source,true,null,null,null,null) && canTrigger(player.world,0.15f))
		{
			ArmorHelper.damageArmor( armor, source, (int)(newDamage*5), player, 0 );
			return 0;
		}
		return newDamage;
	}
}
