package firok.tiths.block.paving;

import firok.tiths.tile.TileWithEntityType;
import firok.tiths.tile.TithsTiles;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

public class BlockMotiaPavingStone extends BlockSwitchablePavingStoneBase
{
	public BlockMotiaPavingStone()
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
	public TileEntity createTileEntity(BlockState state, IBlockReader world)
	{
		return new TileWithEntityType(TithsTiles.teWithEntityType.get());
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

	@Override
	protected boolean canEntityPass(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		Entity entity = context.getEntity();
		if(entity == null) return false;

		boolean isPowered = state.get(LIT), isInstance = isInstance(worldIn, pos, entity);
		// 充能状态 仅允许指定实体通过
		// 默认状态 不允许指定实体通过
		return isPowered == isInstance;
	}
}
