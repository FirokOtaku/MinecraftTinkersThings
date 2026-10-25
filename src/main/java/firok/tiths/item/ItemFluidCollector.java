package firok.tiths.item;

import firok.tiths.common.Items;
import firok.tiths.util.Actions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 液体收集器
 */
public class ItemFluidCollector extends ItemCustom
{
	/** 单球最大容量 (mB) */
	public static final int CAPACITY_PER_BALL = 8000;
	/** 水平 / 向下收集半径 */
	public static final int COLLECT_RANGE = 3;
	/** 每个源方块折合体积 (mB) */
	public static final int AMOUNT_PER_BLOCK = 1000;

	public int countEmptyFluidBalls(EntityPlayer player)
	{
		InventoryPlayer inv = player.inventory;
		int count = 0;
		final int size = inv.getSizeInventory();
		for(int i = 0; i < size; i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			Item item = stack.getItem();
			if(stack.isEmpty() || item != Items.fluidBall || Items.fluidBall.getFluidStack(stack) != null) continue;
			count += stack.getCount();
		}
		return count;
	}

	/** 对指定液体类型可灌装的剩余容量 (未满同种球 + 空球) */
	public int calcAvailableCapacity(EntityPlayer player, Fluid fluidType)
	{
		InventoryPlayer inv = player.inventory;
		int capacity = 0;
		final int size = inv.getSizeInventory();
		for(int i = 0; i < size; i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty() || stack.getItem() != Items.fluidBall) continue;

			FluidStack stored = Items.fluidBall.getFluidStack(stack);
			if(stored == null)
			{
				capacity += stack.getCount() * CAPACITY_PER_BALL;
			}
			else if(stored.getFluid() == fluidType && stored.amount < CAPACITY_PER_BALL)
			{
				capacity += (CAPACITY_PER_BALL - stored.amount) * stack.getCount();
			}
		}
		return capacity;
	}

	public ItemStack takeOneEmptyFluidBall(EntityPlayer player)
	{
		InventoryPlayer inv = player.inventory;
		final int size = inv.getSizeInventory();
		for(int i = 0; i < size; i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			Item item = stack.getItem();
			if(stack.isEmpty() || item != Items.fluidBall || Items.fluidBall.getFluidStack(stack) != null) continue;
			return stack.splitStack(1);
		}
		return null;
	}

	/**
	 * 先灌满背包里未满的同种液体球, 再消耗空液体球灌装.
	 * @return 未能灌入的剩余量
	 */
	public int fillFluidBalls(EntityPlayer player, Fluid fluidType, int amount)
	{
		if(fluidType == null || amount <= 0) return amount;

		InventoryPlayer inv = player.inventory;
		final int size = inv.getSizeInventory();

		// 1) 优先填未满的同种液体球
		for(int i = 0; i < size && amount > 0; i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			if(stack.isEmpty() || stack.getItem() != Items.fluidBall) continue;

			FluidStack stored = Items.fluidBall.getFluidStack(stack);
			if(stored == null || stored.getFluid() != fluidType || stored.amount >= CAPACITY_PER_BALL) continue;

			while(amount > 0 && !stack.isEmpty())
			{
				stored = Items.fluidBall.getFluidStack(stack);
				if(stored == null || stored.getFluid() != fluidType || stored.amount >= CAPACITY_PER_BALL) break;

				if(stack.getCount() > 1)
				{
					ItemStack single = stack.splitStack(1);
					FluidStack singleFluid = Items.fluidBall.getFluidStack(single);
					int space = CAPACITY_PER_BALL - singleFluid.amount;
					int fill = Math.min(space, amount);
					singleFluid = singleFluid.copy();
					singleFluid.amount += fill;
					Items.fluidBall.setFluidStack(single, singleFluid);
					amount -= fill;
					if(!inv.addItemStackToInventory(single))
					{
						Actions.CauseSpawnItem(player, single);
					}
				}
				else
				{
					int space = CAPACITY_PER_BALL - stored.amount;
					int fill = Math.min(space, amount);
					FluidStack updated = stored.copy();
					updated.amount += fill;
					Items.fluidBall.setFluidStack(stack, updated);
					amount -= fill;
					break;
				}
			}
		}

		// 2) 再灌空液体球
		while(amount > 0)
		{
			ItemStack empty = takeOneEmptyFluidBall(player);
			if(empty == null) break;

			int fill = Math.min(CAPACITY_PER_BALL, amount);
			ItemStack stackFluidBall = new ItemStack(Items.fluidBall);
			Items.fluidBall.setFluidStack(stackFluidBall, new FluidStack(fluidType, fill));
			if(!inv.addItemStackToInventory(stackFluidBall))
			{
				Actions.CauseSpawnItem(player, stackFluidBall);
			}
			amount -= fill;
		}

		return amount;
	}

	/** 解析方块上可收集的源液体; 非满源返回 null */
	public static FluidStack getCollectableFluid(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();

		if(block instanceof BlockFluidBase)
		{
			BlockFluidBase blockFluid = (BlockFluidBase) block;
			float percent = blockFluid.getFilledPercentage(world, pos);
			if(percent < 1) return null;
			Fluid fluid = blockFluid.getFluid();
			return fluid == null ? null : new FluidStack(fluid, AMOUNT_PER_BLOCK);
		}

		if(block instanceof BlockLiquid)
		{
			if(state.getValue(BlockLiquid.LEVEL) != 0) return null;
			if(block == Blocks.WATER || block == Blocks.FLOWING_WATER)
			{
				return new FluidStack(FluidRegistry.WATER, AMOUNT_PER_BLOCK);
			}
			if(block == Blocks.LAVA || block == Blocks.FLOWING_LAVA)
			{
				return new FluidStack(FluidRegistry.LAVA, AMOUNT_PER_BLOCK);
			}
		}

		return null;
	}

	private static boolean inCollectRange(BlockPos origin, BlockPos pos)
	{
		int dx = Math.abs(pos.getX() - origin.getX());
		int dz = Math.abs(pos.getZ() - origin.getZ());
		int dy = origin.getY() - pos.getY(); // 只允许同高或向下
		return dx <= COLLECT_RANGE && dz <= COLLECT_RANGE && dy >= 0 && dy <= COLLECT_RANGE;
	}

	/**
	 * 从点击坐标开始: 同层连通液体按切比雪夫环形顺序收集, 再向下层扩展.
	 * @return 实际收集到的 mB
	 */
	public int collectFluidBlocks(World world, EntityPlayer player, ItemStack stackHeld,
	                              BlockPos origin, Fluid fluidType, int maxCollect)
	{
		if(fluidType == null || maxCollect < AMOUNT_PER_BLOCK) return 0;

		int amount = 0;
		Set<BlockPos> collected = new LinkedHashSet<>();

		for(int depth = 0; depth <= COLLECT_RANGE && amount + AMOUNT_PER_BLOCK <= maxCollect; depth++)
		{
			int y = origin.getY() - depth;
			Queue<BlockPos> queue = new ArrayDeque<>();
			Set<BlockPos> layerSeen = new HashSet<>();

			if(depth == 0)
			{
				queue.offer(origin);
				layerSeen.add(origin);
			}
			else
			{
				for(BlockPos prev : collected)
				{
					if(prev.getY() != y + 1) continue;
					BlockPos below = prev.down();
					if(layerSeen.contains(below) || !inCollectRange(origin, below)) continue;
					FluidStack fs = getCollectableFluid(world, below);
					if(fs == null || fs.getFluid() != fluidType) continue;
					layerSeen.add(below);
					queue.offer(below);
				}
			}

			Set<BlockPos> layerContiguous = new HashSet<>();
			while(!queue.isEmpty())
			{
				BlockPos pos = queue.poll();
				FluidStack fs = getCollectableFluid(world, pos);
				if(fs == null || fs.getFluid() != fluidType) continue;
				layerContiguous.add(pos);

				for(EnumFacing facing : EnumFacing.HORIZONTALS)
				{
					BlockPos next = pos.offset(facing);
					if(layerSeen.contains(next) || !inCollectRange(origin, next) || next.getY() != y) continue;
					layerSeen.add(next);
					queue.offer(next);
				}
			}

			List<BlockPos> ordered = new ArrayList<>(layerContiguous);
			ordered.sort(Comparator
					.comparingInt((BlockPos p) -> Math.max(Math.abs(p.getX() - origin.getX()), Math.abs(p.getZ() - origin.getZ())))
					.thenComparingInt(BlockPos::getX)
					.thenComparingInt(BlockPos::getZ));

			for(BlockPos pos : ordered)
			{
				if(amount + AMOUNT_PER_BLOCK > maxCollect) break;

				if(!player.canPlayerEdit(pos, EnumFacing.UP, stackHeld)) continue;
				if(!world.isBlockModifiable(player, pos)) continue;

				FluidStack fs = getCollectableFluid(world, pos);
				if(fs == null || fs.getFluid() != fluidType) continue;

				world.setBlockToAir(pos);
				amount += AMOUNT_PER_BLOCK;
				collected.add(pos);
			}
		}

		return amount;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld = player.getHeldItem(hand);

		RayTraceResult ray = this.rayTrace(world, player, true);
		if(ray == null || ray.typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return new ActionResult<>(EnumActionResult.PASS, stackHeld);
		}

		BlockPos posHit = ray.getBlockPos();
		FluidStack hitFluid = getCollectableFluid(world, posHit);
		if(hitFluid == null)
		{
			return new ActionResult<>(EnumActionResult.PASS, stackHeld);
		}

		if(world.isRemote)
		{
			return new ActionResult<>(EnumActionResult.SUCCESS, stackHeld);
		}

		Fluid fluidType = hitFluid.getFluid();
		int maxCollect = calcAvailableCapacity(player, fluidType);
		if(maxCollect < AMOUNT_PER_BLOCK)
		{
			return new ActionResult<>(EnumActionResult.FAIL, stackHeld);
		}

		int amount = collectFluidBlocks(world, player, stackHeld, posHit, fluidType, maxCollect);
		if(amount > 0)
		{
			fillFluidBalls(player, fluidType, amount);
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stackHeld);
	}
}
