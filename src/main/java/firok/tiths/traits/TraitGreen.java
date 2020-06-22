package firok.tiths.traits;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.util.Predicates.isPlants;

/**
 * 绿意
 */
public class TraitGreen extends AbstractTrait
{
	public TraitGreen()
	{
		super("green",0);
	}

	@Override
	public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event)
	{
		if(isPlants(event.getState())) event.setNewSpeed(0);
	}
}
