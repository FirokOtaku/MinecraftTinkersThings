package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.SoundEvents;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import static firok.tiths.common.Keys.colorTraitDevouring;
import static firok.tiths.common.Keys.nameTraitDevouring;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 吞噬 - 护甲
 */
public class TraitArmorDevouring extends AbstractArmorTrait
{
	public TraitArmorDevouring()
	{
		super(nameTraitDevouring, colorTraitDevouring);
	}

	@Override
	public float onHurt(ItemStack armor, EntityPlayer player, DamageSource source, float damage, float newDamage, LivingHurtEvent evt)
	{
		if(source!=null && source.isMagicDamage())
		{
			if(!player.world.isRemote && newDamage > 0 && canTrigger(player.world,0.2))
			{
				player.world.playSound(null,player.posX,player.posY,player.posZ, SoundEvents.effectTransforming, SoundCategory.MASTER,2,1);
				player.heal(newDamage * 0.5f);
			}

			return 0;
		}
		else return newDamage;
	}

	@Override
	public int onArmorDamage(ItemStack armor, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot)
	{
		return source!=null && source.isMagicDamage() ? 0 : newDamage;
	}

}
