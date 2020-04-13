package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitGorgeous;
import static firok.tiths.common.Keys.nameTraitGorgeous;
import static firok.tiths.traits.TraitGorgeous.factorGorgeous;

/**
 * 斑斓 - 护甲
 */
public class TraitArmorGorgeous extends AbstractArmorTrait
{
	public TraitArmorGorgeous()
	{
		super(nameTraitGorgeous,colorTraitGorgeous);
	}
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		return newDamage / factorGorgeous(player);
	}
}
