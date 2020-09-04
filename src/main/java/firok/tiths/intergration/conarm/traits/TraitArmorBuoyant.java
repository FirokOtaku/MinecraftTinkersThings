package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Potions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitBuoyant;
import static firok.tiths.common.Keys.nameTraitBuoyant;
import static firok.tiths.util.Predicates.canTick;

/**
 * 浮力 - 护甲
 */
public class TraitArmorBuoyant extends AbstractArmorTrait
{
	public TraitArmorBuoyant()
	{
		super(nameTraitBuoyant,colorTraitBuoyant);
	}

//	@Override
//	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
//	{
//		super.onArmorTick(tool, world, player);
//	}

	@Override
	public void onAbilityTick(final int level, World world, EntityPlayer player)
	{
		if(player.isInWater() && canTick(player.world,4,0))
		{
			player.addPotionEffect(new PotionEffect(Potions.hidden_buoyant,5,0,false,false));
		}
	}
}
