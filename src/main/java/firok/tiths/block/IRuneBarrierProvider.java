package firok.tiths.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

/**
 * 实现此接口的方块会提供符文屏障
 */
public interface IRuneBarrierProvider
{
	/**
	 * @param stateProvider 提供者状态
	 * @param world 世界
	 * @param posProvider 提供者位置
	 * @param context 逻辑上下文
	 * @return 是否允许实体通过
	 */
	boolean canEntityPassRuneBarrier(BlockState stateProvider, IBlockReader world, BlockPos posProvider, ISelectionContext context);

	/**
	 * @param stateProvider 提供者状态
	 * @param world 世界
	 * @param posProvider 提供者位置
	 * @return 向上方提供多高的屏障
	 */
	int provideRuneBarrierTopHeight(BlockState stateProvider, IBlockReader world, BlockPos posProvider);

	/**
	 * @return 如果条件允许 此方块最多向上方提供多高屏障
	 * @implNote 这个方法仅用于向屏障系统注册用
	 */
	int provideRuneBarrierTopHeightMax();

	/**
	 * 更新屏障状态
	 * @param world 世界
	 * @param posProvider 提供者位置
	 * @param stateProvider 提供者状态
	 * @param flag true-生成屏障 false-移除屏障
	 * @see AbstractBlock.AbstractBlockState#isAir 判断是否为空气的逻辑可能需要更换
	 */
	@SuppressWarnings("all")
	default void updateRuneBarrier(World world, BlockPos posProvider, BlockState stateProvider, boolean flag)
	{
		final int heightTop = this.provideRuneBarrierTopHeight(stateProvider, world, posProvider);
		final int x = posProvider.getX(), y = posProvider.getY(), z = posProvider.getZ();
		final BlockState stateBarrier = TithsBlocks.RUNE_BARRIER.get().getDefaultState();
		final BlockState stateAir = Blocks.AIR.getDefaultState();

		BlockPos.Mutable pos = new BlockPos.Mutable();
		UPDATE_TOP: for(int step = 1; step <= heightTop; step++)
		{
			pos.setPos(x, y + step, z);
			BlockState state = world.getBlockState(pos);

			if(flag)
			{
				if(state == stateBarrier) continue UPDATE_TOP;
				else if(state.isAir(world, pos)) world.setBlockState(pos, stateBarrier);
				else break UPDATE_TOP;
			}
			else
			{
				if(state == stateBarrier) world.setBlockState(pos, stateAir, 16);
				else if(state == stateAir) continue UPDATE_TOP;
				else break UPDATE_TOP;
			}
		}
		UPDATE_BOTTOM: ;
	}
}
