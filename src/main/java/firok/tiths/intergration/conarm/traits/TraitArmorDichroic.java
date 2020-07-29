package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;

import static firok.tiths.common.Keys.colorTraitDichroic;
import static firok.tiths.common.Keys.nameTraitDichroic;
import static firok.tiths.util.Actions.getLight;

/**
 * 二色性
 */
public class TraitArmorDichroic extends AbstractArmorTrait
{
	public TraitArmorDichroic()
	{
		super(nameTraitDichroic,colorTraitDichroic);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		int light=getLight(player);

		return newDamage / (float)( 1 + (light - Configs.Traits.factor_dichroic_light_mid ) / Configs.Traits.factor_dichroic_light );
	}

	@Override
	public void onKnockback(ItemStack armor, EntityPlayer player, LivingKnockBackEvent evt)
	{
		int light=getLight(player);

		float strength=evt.getStrength(); // todo 估计只改这个没用
		strength /=(float)( 1 + (light - Configs.Traits.factor_dichroic_light_mid ) / Configs.Traits.factor_dichroic_light );

		evt.setStrength( strength );
	}
	// todo
}
