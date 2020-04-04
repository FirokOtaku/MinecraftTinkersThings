package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitRadiant;
import static firok.tiths.common.Keys.nameTraitRadiant;
import static firok.tiths.traits.TraitRadiant.*;

/**
 * 辉耀 - 护甲
 */
public class TraitArmorRadiant extends AbstractArmorTrait
{
	public TraitArmorRadiant()
	{
		super(nameTraitRadiant, colorTraitRadiant);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(checkBurn(world))
		{
			burn(world, player);
		}

		if(checkParticle(world))
		{
			particle(world,player,world.rand);
		}
	}
}
