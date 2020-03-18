package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitRadiant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 辉耀 - 护甲
 */
public class TraitArmorRadiant extends TraitRadiant implements IAbstractArmorTrait
{
	@Override
	public void onAbilityTick(int level, World world, EntityPlayer player)
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
