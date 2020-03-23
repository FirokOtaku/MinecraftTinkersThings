package firok.tiths.intergration.conarm.traits;

import firok.tiths.intergration.conarm.IAbstractArmorTrait;
import firok.tiths.traits.TraitExtremeFreezing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * 极寒 - 护甲
 */
public class TraitArmorExtremeFreezing extends TraitExtremeFreezing implements IAbstractArmorTrait
{
	@Override
	public void onAbilityTick(int level, World world, EntityPlayer player)
	{
		if(checkParticle(world)) particle(player);
		if(checkFreeze(world)) freeze(player);
	}
}
