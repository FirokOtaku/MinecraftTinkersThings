package firok.tiths.block.logic;

import firok.tiths.tile.logic.TESearing;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static firok.tiths.TinkersThings.tell;
import static firok.tiths.util.Ranges.Neighbour;

// 焦黑窖内部方块
public class BlockLogicSearing extends BlockContainer
{
	public BlockLogicSearing()
	{
		super(Material.AIR);
	}

	@Override
	public void observedNeighborChange(IBlockState state, World world, BlockPos pos, Block block, BlockPos pos2)
	{
//		super.observedNeighborChange(state, world, pos, block, pos2);
		tell(String.format("state:%s, pos1:%s, block:%s, pos2:%s",state,pos,block.getUnlocalizedName(),pos2));

		for(BlockPos tempPos: Neighbour(pos))
		{
			if(world.isAirBlock(tempPos))
			{
				world.setBlockToAir(pos);
				break;
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TESearing();
	}

}
