package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitLingering;
import static firok.tiths.common.Keys.nameTraitLingering;
import static firok.tiths.util.Predicates.canTick;

/**
 * 缭绕
 */
public class TraitArmorLingering extends AbstractArmorTrait
{
	public TraitArmorLingering()
	{
		super(nameTraitLingering,colorTraitLingering);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,80,0))
		{
			player.addPotionEffect(new PotionEffect(Potions.bruming,85,0,false,false));
		}
	}
}
