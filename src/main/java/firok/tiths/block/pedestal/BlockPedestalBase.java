package firok.tiths.block.pedestal;

import firok.tiths.tile.pedestal.TilePedestalBase;
import firok.tiths.util.Actions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FurnaceBlock;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.datafix.fixes.EntityItemFrameFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public abstract class BlockPedestalBase extends Block
{
	public BlockPedestalBase(Properties properties)
	{
		super(properties);
	}
	private boolean shouldDropWhenHarvested = false;
	protected void setShouldDropWhenHarvested(boolean shouldDropWhenHarvested)
	{
		this.shouldDropWhenHarvested = shouldDropWhenHarvested;
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

	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBlockHarvested(worldIn, pos, state, player);
	}

	@Deprecated
	public void spawnAdditionalDrops(BlockState state, ServerWorld worldIn, BlockPos pos, ItemStack stack) {
		if(shouldDropWhenHarvested)
		{
			TilePedestalBase te = getTilePedestalAt(worldIn, pos);
			ItemStack stackPedestal = te.getStackPedestal();
			ItemEntity ie = new ItemEntity(worldIn, pos.getX(), pos.getY() + 1, pos.getZ(), stackPedestal);
			worldIn.addEntity(ie);
		}
	}

	public TilePedestalBase getTilePedestalAt(World world, BlockPos pos)
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
