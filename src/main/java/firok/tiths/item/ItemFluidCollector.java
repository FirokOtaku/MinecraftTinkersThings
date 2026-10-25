package firok.tiths.item;

import firok.tiths.common.Items;
import firok.tiths.util.Actions;
import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * 液体收集器
 */
public class ItemFluidCollector extends ItemCustom
{
	/** 单球最大容量 (mB) */
	public static final int CAPACITY_PER_BALL = 4000;

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

	public ItemStack takeOneEmptyFluidBall(EntityPlayer player)
	{
		InventoryPlayer inv = player.inventory;
		final int size = inv.getSizeInventory();
		for(int i = 0; i < size; i++)
		{
			ItemStack stack = inv.getStackInSlot(i);
			Item item = stack.getItem();
			if(stack.isEmpty() || item != Items.fluidBall || Items.fluidBall.getFluidStack(stack) != null) continue;
			stack.shrink(1);
			return stack;
		}
		return null;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
	{
		ItemStack stackHeld = player.getHeldItem(hand);

		if(world.isRemote)
		{
			return new ActionResult<>(EnumActionResult.SUCCESS, stackHeld);
		}

		int emptyBalls = countEmptyFluidBalls(player);
		if(emptyBalls <= 0) return new ActionResult<>(EnumActionResult.FAIL, stackHeld);

		final int maxCollect = emptyBalls * CAPACITY_PER_BALL;
		BlockPos posCenter = player.getPosition();
		Fluid fluidType = null;
		int amount = 0;

		FOR_COLLECT:
		for(int ox = -3; ox <= 3; ox++)
		{
			for(int oz = -3; oz <= 3; oz++)
			{
				for(int oy = -3; oy <= 3; oy++)
				{
					if(amount >= maxCollect) break FOR_COLLECT;

					BlockPos posTemp = posCenter.add(ox, oy, oz);

					if(!player.canPlayerEdit(posTemp, EnumFacing.UP, stackHeld)) continue;
					if(!world.isBlockModifiable(player, posTemp)) continue;

					IBlockState stateTemp = world.getBlockState(posTemp);
					Block blockTemp = stateTemp.getBlock();

					if(blockTemp == Blocks.AIR) continue;

					Fluid fluidTemp = null;
					int amountTemp = 0;

					if(blockTemp instanceof BlockFluidBase)
					{
						BlockFluidBase blockFluid = (BlockFluidBase) blockTemp;
						fluidTemp = blockFluid.getFluid();
						float percent = blockFluid.getFilledPercentage(world, posTemp);
						if(percent >= 1) amountTemp = 1000;
					}
					else if(blockTemp == Blocks.WATER)
					{
						fluidTemp = FluidRegistry.WATER;
						amountTemp = 1000;
					}
					else if(blockTemp == Blocks.LAVA)
					{
						fluidTemp = FluidRegistry.LAVA;
						amountTemp = 1000;
					}

					if(fluidTemp == null || amountTemp < 1000) continue;
					if(fluidType != null && fluidType != fluidTemp) continue;
					if(amount + amountTemp > maxCollect) continue;

					fluidType = fluidTemp;
					amount += amountTemp;
					world.setBlockToAir(posTemp);
				}
			}
		}

		if(fluidType != null && amount > 0)
		{
			while(amount > 0)
			{
				if(takeOneEmptyFluidBall(player) == null) break;

				int fill = Math.min(CAPACITY_PER_BALL, amount);
				ItemStack stackFluidBall = new ItemStack(Items.fluidBall);
				Items.fluidBall.setFluidStack(stackFluidBall, new FluidStack(fluidType, fill));
				Actions.CauseSpawnItem(player, stackFluidBall);
				amount -= fill;
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stackHeld);
	}
}
