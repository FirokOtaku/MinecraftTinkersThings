package firok.mtim.traits;

import firok.mtim.util.Colors;
import firok.mtim.util.Predicates;
import firok.mtim.util.Selectors;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

/**
 * 辉耀
 */
public class TraitRadiant extends AbstractTrait
{
	public TraitRadiant()
	{
		super("radiant", Colors.Tomato);
	}

	@Override
	public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected)
	{
//		super.onUpdate(tool, world, entity, itemSlot, isSelected);
		if(isSelected && !world.isRemote && Predicates.canTick(world,80,1))
		{
			List<Entity> ens=world.getEntitiesInAABBexcluding(
					entity,
					new AxisAlignedBB(
							entity.posX-5,entity.posY-4,entity.posZ-5,
							entity.posX+5,entity.posY+4,entity.posZ+5
					),
					Selectors.mobAlive
			);
			for(Entity en:ens)
			{
				en.setFire(5);
			}
		}
	}
}
