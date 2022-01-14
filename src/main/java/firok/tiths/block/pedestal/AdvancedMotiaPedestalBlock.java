package firok.tiths.block.pedestal;

import firok.tiths.tile.pedestal.TileAdvancedMotiaPedestal;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class AdvancedMotiaPedestalBlock extends PedestalBlockBase
{
	public AdvancedMotiaPedestalBlock()
	{
		super(Properties.create(Material.ROCK)
				.notSolid()
				.setRequiresTool()
				.harvestTool(ToolType.PICKAXE)
				.harvestLevel(HarvestLevels.WOOD)
				.hardnessAndResistance(2,50));
	}

	@Override
	public TileAdvancedMotiaPedestal createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileAdvancedMotiaPedestal();
	}
}
