package firok.tiths.intergration.conarm.traits;

import firok.tiths.common.Configs;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitCreaky;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 咔嚓 - 护甲
 */
public class TraitArmorCreaky extends TraitCreaky implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.Traits.rate_creaky_hit))
		{
			Actions.CauseSpawningSilverfish(player, player.posX, player.posY, player.posZ, player.world);
		}
		return newDamage;
	}
}
