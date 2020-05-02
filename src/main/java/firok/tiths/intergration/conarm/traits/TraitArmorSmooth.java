package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitSmooth;
import static firok.tiths.common.Keys.nameTraitSmooth;
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
		float rate=player.motionX * player.motionX + player.motionY * player.motionY + player.motionZ * player.motionZ > 0 ?
				0.5f : 0.2f;
		return canTrigger(player.world,rate)? 0 : newDamage;
	}
}
