package firok.tiths.intergration.conarm.traits;

import firok.tiths.common.Configs;
import firok.tiths.common.Items;
import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitCarbonizing;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 碳化 - 护甲
 */
public class TraitArmorCarbonizing extends TraitCarbonizing implements IAbstractArmorTrait
{
	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(!player.world.isRemote && canTrigger(player.world, Configs.Traits.rate_carbonizing_drop))
		{
			Actions.CauseSpawnItem(player,new ItemStack(Items.cinder));
		}
		return newDamage;
	}
}
