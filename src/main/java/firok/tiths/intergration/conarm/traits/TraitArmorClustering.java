package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitClustering;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

/**
 * 群簇 - 护甲
 */
public class TraitArmorClustering extends TraitClustering implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		// todo
		return newDamage;
	}
}
