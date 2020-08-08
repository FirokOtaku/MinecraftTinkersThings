package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.common.Configs;
import firok.tiths.common.Potions;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Ranges;
import firok.tiths.util.Selectors;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitHyper;
import static firok.tiths.common.Keys.nameTraitHyper;
import static firok.tiths.util.Predicates.canTick;

/**
 * 振奋 - 护甲
 */
public class TraitArmorHyper extends AbstractArmorTrait
{
	public TraitArmorHyper()
	{
		super(nameTraitHyper, colorTraitHyper);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,60,4))
		{
			int size= EntityFinders.Nearby(player, Configs.ArmorTraits.range_hyper,Selectors.mobAlive).size();
			int level= MathHelper.log2DeBruijn(size)-1;
			if(level>=0)
			{
				player.addPotionEffect(new PotionEffect(Potions.hidden_hyper,65,level));
			}
		}
	}
}
