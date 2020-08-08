package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitSmooth;
import static firok.tiths.common.Keys.nameTraitSmooth;
import static firok.tiths.util.Predicates.canDealWith;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 光滑 - 护甲
 */
public class TraitArmorSmooth extends AbstractArmorTrait
{
	public TraitArmorSmooth()
	{
		super(nameTraitSmooth,colorTraitSmooth);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(canDealWith(source,true,null,null,null,null))
		{
			float rate=(float) (player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ > 0 ?
					Configs.ArmorTraits.rate_smooth_moving : Configs.ArmorTraits.rate_smooth_standing );
			return canTrigger(player.world,rate)? 0 : newDamage;
		}
		return newDamage;
	}
}
