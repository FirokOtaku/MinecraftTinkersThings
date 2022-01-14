package firok.tiths.block.logic;

import firok.tiths.block.IRuneBarrierProvider;
import firok.tiths.util.EntityFinders;
import firok.tiths.util.Predicates;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 符文屏障方块
 *
 * 目前由各类铺路石使用
 * 碰撞箱的计算取决于下方有没有可以提供屏障的方块
 */
public class RuneBarrierBlock extends BarrierBlock
{
	protected static final VoxelShape CUBE = VoxelShapes.fullCube();
	protected static final VoxelShape EMPTY = VoxelShapes.empty();
	public RuneBarrierBlock()
	{
		super(
				Properties.create(Material.BARRIER)
						.hardnessAndResistance(-1.0F, 3600000.8F)
						.noDrops().notSolid()
						.setAllowsSpawn(Predicates::neverAllowSpawn)
		);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		boolean canPass = canEntityPass(state, worldIn, pos, context);
		return canPass ? VoxelShapes.create(0,0,0,0,0,0) : CUBE;
	}

	public boolean canEntityPass(BlockState stateRuneBarrier, IBlockReader world, BlockPos posRuneBarrier, ISelectionContext context)
	{
		// todo 这个以后可以考虑做一下缓存
		final int maxTop = RuneBarrierManager.getMaxTop();
		for(int step = 1; step <= maxTop; step++)
		{
			BlockPos pos = posRuneBarrier.down(step);
			BlockState state = world.getBlockState(pos);
			if(state == stateRuneBarrier) continue; // 连接的屏障
			Block block = state.getBlock();
			if(!(block instanceof IRuneBarrierProvider)) return false; // 不是屏障 但也不是屏障提供者 直接不允许
			IRuneBarrierProvider provider = (IRuneBarrierProvider) block;
			return provider.canEntityPassRuneBarrier(state, world, pos, context); // 交给提供者判断
		}

		return false;
	}

	@Override
	public void onBlockHarvested(World world, BlockPos posRuneBarrier, BlockState stateRuneBarrier, PlayerEntity player)
	{
		final int maxTop = RuneBarrierManager.getMaxTop();
		final BlockState stateBarrier = this.getDefaultState();
		for(int step = 1; step <= maxTop; step++)
		{
			BlockPos pos = posRuneBarrier.down(step);
			BlockState state = world.getBlockState(pos);
			Block block = state.getBlock();
			if(state == stateBarrier) continue;
			else if(block instanceof IRuneBarrierProvider)
			{
				IRuneBarrierProvider provider = (IRuneBarrierProvider) block;
				int height = provider.provideRuneBarrierTopHeight(state, world, pos);
				if(step <= height) provider.updateRuneBarrier(world, pos, state, true);
			}
			else break;
		}
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
	{
		return EMPTY;
	}

	@Override
	public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context)
	{
		return context != null &&
				context.getEntity() instanceof PlayerEntity &&
				((PlayerEntity) context.getEntity()).isCreative() ?
				CUBE : EMPTY;
	}

	@Override
	public boolean canBeReplacedByLeaves(BlockState state, IWorldReader world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean canBeReplacedByLogs(BlockState state, IWorldReader world, BlockPos pos)
	{
		return false;
	}

	@Override
	public boolean isReplaceable(BlockState state, Fluid fluid)
	{
		return false;
	}

	@Override
	public boolean isReplaceable(BlockState state, BlockItemUseContext useContext)
	{
		return false;
	}

	/**
	 * @see TorchBlock
	 */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand)
	{
		addParticle(world, pos, rand, true);
		final int count = EntityFinders.Nearby(world, pos, 2, Predicates::isLivingAlive).size();
		if(count > 0)
		{
			addParticle(world, pos, rand, false);
			addParticle(world, pos, rand, false);
		}
	}
	private void addParticle(World world, BlockPos pos, Random rand, boolean optional)
	{
		if(canTrigger(rand, 0.6f))
		world.addOptionalParticle(ParticleTypes.ENCHANT,
				pos.getX() + rand.nextFloat(),
				pos.getY() + rand.nextFloat(),
				pos.getZ() + rand.nextFloat(),
				0.0D, 0.0D, 0.0D);
	}
}
