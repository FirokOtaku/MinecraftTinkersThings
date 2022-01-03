package firok.tiths.block;

import firok.tiths.tile.TileMotiaPedestal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockMotiaPedestal extends Block
{
	public BlockMotiaPedestal()
	{
		super(Properties.create(Material.GOURD)
				.hardnessAndResistance(10,1));
	}

	@Override
	public void onBlockClicked(BlockState state, World worldIn, BlockPos pos, PlayerEntity player)
	{
		super.onBlockClicked(state, worldIn, pos, player);
	}

	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
	{
		try
		{
			ItemStack stackHeld = player.getHeldItemMainhand();
			if(stackHeld == ItemStack.EMPTY) return ActionResultType.PASS;

			TileMotiaPedestal te = (TileMotiaPedestal) worldIn.getTileEntity(pos);
			if(te == null)
			{
				te = new TileMotiaPedestal();
				te.setWorldAndPos(worldIn, pos);
				worldIn.setTileEntity(pos, te);
			}

			te.setStackPhoto(stackHeld);

			return ActionResultType.SUCCESS;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ActionResultType.PASS;
		}
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileMotiaPedestal();
	}
}
