package firok.tiths.intergration.conarm.traits;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitEndothermic;
import static firok.tiths.common.Keys.nameTraitEndothermic;

/**
 * 吸热 - 护甲
 */
public class TraitArmorEndothermic extends AbstractArmorTrait
{
	public TraitArmorEndothermic()
	{
		super(nameTraitEndothermic,colorTraitEndothermic);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(source.isFireDamage())
		{
			ArmorHelper.damageArmor(armor,source,1,player);
			return Math.max(0,newDamage - damage / 4);
		}
		else
		{
			return newDamage;
		}
	}
}
