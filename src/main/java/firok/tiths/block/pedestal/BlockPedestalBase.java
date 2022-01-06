package firok.tiths.block.pedestal;

import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public abstract class BlockPedestalBase extends Block
{
	public BlockPedestalBase(Properties properties)
	{
		super(properties);
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TilePedestalBase createTileEntity(BlockState state, IBlockReader world)
	{
		return new TilePedestalBase();
	}

	protected TilePedestalBase getTilePedestalAt(World world, BlockPos pos)
	{
		TilePedestalBase tile = (TilePedestalBase) world.getTileEntity(pos);
		if(tile == null)
		{
			tile = createTileEntity(world.getBlockState(pos), world);
			world.setTileEntity(pos, tile);
		}
		return tile;
	}
}
