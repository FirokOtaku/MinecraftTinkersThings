package firok.tiths.intergration.conarm.traits;

import c4.conarm.lib.traits.AbstractArmorTrait;
import firok.tiths.util.Actions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import static firok.tiths.common.Keys.colorTraitPhotosynthetic;
import static firok.tiths.common.Keys.nameTraitPhotosynthetic;
import static firok.tiths.util.Predicates.canTick;

/**
 * 光合作用 - 护甲
 */
public class TraitArmorPhotosynthetic extends AbstractArmorTrait
{
	public TraitArmorPhotosynthetic()
	{
		super(nameTraitPhotosynthetic,colorTraitPhotosynthetic);
	}

	@Override
	public void onArmorTick(ItemStack tool, World world, EntityPlayer player)
	{
		if(!world.isRemote && canTick(world,20,0))
		{
			int light=Actions.getLight(player);
			if(light>8)
			{
				int airOrigin=player.getAir();
				int airNew=Math.min(airOrigin+light-8,300);
				player.setAir(airNew);
			}
		}
	}
}
