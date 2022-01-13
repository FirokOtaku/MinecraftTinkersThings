package firok.tiths.block.paving;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

/**
 * 铺路石
 */
public abstract class BlockPavingStoneBase extends Block
{
	protected final VoxelShape shapeBox1 = VoxelShapes.fullCube();
	protected final VoxelShape shapeBox3 = VoxelShapes.create(0,0,0,1,3,1);

	public BlockPavingStoneBase(Properties properties)
	{
		super(properties);
	}
	protected BlockPavingStoneBase()
	{
		super(
				Properties.create(Material.ROCK)
						.harvestTool(ToolType.PICKAXE)
						.harvestLevel(HarvestLevels.WOOD)
						.hardnessAndResistance(2, 5)
		);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return canEntityPass(state, worldIn, pos, context) ? shapeBox1 : shapeBox3;
	}

	@Override
	public boolean collisionExtendsVertically(BlockState state, IBlockReader world, BlockPos pos, Entity collidingEntity)
	{
		return true;
	}

	protected abstract boolean canEntityPass(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context);

	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn, BlockPos pos)
	{
		return super.getRenderShape(state, worldIn, pos);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return shapeBox3;
	}
}
