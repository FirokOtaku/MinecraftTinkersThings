package firok.tiths.block.paving;

import firok.tiths.tile.TileWithEntityType;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class MotiaPavingStoneBlock extends SwitchablePavingStoneBlockBase
{
	public MotiaPavingStoneBlock()
	{
		super(
				AbstractBlock.Properties.create(Material.ROCK)
						.harvestTool(ToolType.PICKAXE)
						.harvestLevel(HarvestLevels.WOOD)
						.hardnessAndResistance(2, 5)
		);
	}

	@Override
	public boolean hasTileEntity(BlockState state)
	{
		return true;
	}

	@Override
	public TileWithEntityType createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileWithEntityType();
	}

	private static boolean isInstance(IBlockReader world, BlockPos pos, Entity entity)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileWithEntityType)
		{
			return ((TileWithEntityType) te).isInstance(entity);
		}
		return false;
	}

	public TileWithEntityType getTileWithTypeAt(World world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
		if(!(tile instanceof TileWithEntityType))
		{
			tile = createTileEntity(world.getBlockState(pos), world);
			world.setTileEntity(pos, tile);
		}
		return (TileWithEntityType) tile;
	}

	@Override
	public boolean canEntityPassRuneBarrier(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		Entity entity = context.getEntity();
		if(entity == null) return false;

		boolean isPowered = state.get(LIT), isInstance = isInstance(worldIn, pos, entity);
		// 充能状态 仅允许指定实体通过
		// 默认状态 不允许指定实体通过
		return isPowered == isInstance;
	}
}
