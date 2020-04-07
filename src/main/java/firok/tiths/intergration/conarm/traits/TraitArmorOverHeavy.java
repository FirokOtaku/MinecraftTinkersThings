package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitOverHeavy;
import static firok.tiths.common.Keys.nameTraitOverHeavy;
import static firok.tiths.util.Predicates.canTick;

/**
 * 过沉 - 护甲
 */
public class TraitArmorOverHeavy extends AbstractArmorTrait
{
	public TraitArmorOverHeavy()
	{
		super(nameTraitOverHeavy,colorTraitOverHeavy);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,80,4))
		{
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS,85,0));
			player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE,85,0));
		}
	}
}
