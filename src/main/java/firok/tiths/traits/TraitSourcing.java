package firok.tiths.traits;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockStem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import static firok.tiths.common.Keys.colorTraitSourcing;
import static firok.tiths.common.Keys.nameTraitSourcing;

/**
 * 思源
 */
public class TraitSourcing extends AbstractTrait
{
	public TraitSourcing()
	{
		super(nameTraitSourcing,colorTraitSourcing);
	}

	@Override
	public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event)
	{
		World world=event.getWorld();
		IBlockState state=event.getState();
		Block block=state.getBlock();
		BlockCrops c=null;
		BlockStem stem=null;
	}
}
