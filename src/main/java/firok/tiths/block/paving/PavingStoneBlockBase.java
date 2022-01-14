package firok.tiths.block.paving;

import firok.tiths.block.IRuneBarrierProvider;
import firok.tiths.block.logic.RuneBarrierManager;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.PiglinTasks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import slimeknights.tconstruct.library.utils.HarvestLevels;

/**
 * 铺路石
 */
public abstract class PavingStoneBlockBase extends Block implements IRuneBarrierProvider
{
	public PavingStoneBlockBase(Properties properties)
	{
		super(properties);
	}
	protected PavingStoneBlockBase()
	{
		super(
				Properties.create(Material.ROCK)
						.harvestTool(ToolType.PICKAXE)
						.harvestLevel(HarvestLevels.WOOD)
						.hardnessAndResistance(2, 5)
		);
		RuneBarrierManager.registerBarrierProvider(this);
	}

	@Override
	public void onBlockAdded(BlockState state, World world, BlockPos pos, BlockState oldState, boolean isMoving)
	{
		super.onBlockAdded(state, world, pos, oldState, isMoving);
		this.updateRuneBarrier(world, pos, state, true);
	}

	public void onBlockHarvested(World world, BlockPos pos, BlockState state, PlayerEntity player)
	{
		super.onBlockHarvested(world, pos, state, player);
		this.updateRuneBarrier(world, pos, state, false);
	}

	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
	{
		super.neighborChanged(state, world, pos, blockIn, fromPos, isMoving);
		this.updateRuneBarrier(world, pos, state, true);
	}

	// 所有的铺路石默认提供2格高度屏障
	@Override
	public int provideRuneBarrierTopHeightMax()
	{
		return 2;
	}

	@Override
	public int provideRuneBarrierTopHeight(BlockState stateProvider, IBlockReader world, BlockPos posProvider)
	{
		return 2;
	}
}
