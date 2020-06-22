package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitFading;
import static firok.tiths.common.Keys.nameTraitFading;
import static firok.tiths.util.Actions.getLight;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 影淡
 */
public class TraitArmorFading extends AbstractArmorTrait
{
	public TraitArmorFading()
	{
		super(nameTraitFading,colorTraitFading);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		int light=getLight(player);
		float rate=50-6.25f*light;
		if(source.isUnblockable()) rate-=0.4f;
		if(source.isProjectile()) rate+=0.2f;
		if(source.isExplosion()) rate-=0.4f;

		if(canTrigger(player.world,rate))
		{
			evt.setCanceled(true);
			return 0;
		}
		return newDamage;
	}
}
