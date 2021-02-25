package firok.tiths.block.farm;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public abstract class BlockFarmStone extends BlockContainer
{
	public BlockFarmStone(Material blockMaterialIn, MapColor blockMapColorIn)
	{
		super(blockMaterialIn, blockMapColorIn);
	}

	public BlockFarmStone(Material materialIn)
	{
		super(Material.WATER);
	}

}
