package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitCarbonizing;
import static firok.tiths.common.Keys.nameTraitArmorHiding;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 匿踪 - 护甲
 */
public class TraitArmorHiding extends AbstractArmorTrait
{
	public TraitArmorHiding()
	{
		super(nameTraitArmorHiding, colorTraitCarbonizing);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.ArmorTraits.rate_hiding_hit))
		{
			Actions.CauseAccumEffect(player,new PotionEffect(MobEffects.INVISIBILITY,160,0));
		}
		return newDamage;
	}
}