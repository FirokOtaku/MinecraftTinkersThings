package firok.tiths.traits;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.List;

import static firok.tiths.common.Keys.colorTraitFarmer;
import static firok.tiths.common.Keys.nameTraitFarmer;
import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Predicates.isCrops;

/**
 * 农场主
 */
public class TraitFarmer extends AbstractTrait
{
	public TraitFarmer()
	{
		super(nameTraitFarmer,colorTraitFarmer);
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event)
	{
		World world=event.getWorld();
		if(world.isRemote || !canTrigger(world,0.4)) return;
		List<ItemStack> drops=event.getDrops();
		IBlockState state=event.getState();
		if(isCrops(state))
		{
			for(ItemStack drop:drops)
			{
				drop.setCount( drop.getCount() * 2 );
			}
		}
	}
}
