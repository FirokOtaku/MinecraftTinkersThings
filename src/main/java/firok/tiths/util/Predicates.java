package firok.tiths.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;

import java.util.Random;

public final class Predicates
{
	private Predicates() {}

	public static boolean canTick(long time, int interval, int offset)
	{
		return time % interval == offset;
	}
	public static boolean canTickServer(World world, int interval, int offset)
	{
		return world != null && !world.isRemote && canTick(world.getGameTime(), interval, offset);
	}
	public static boolean canTickClient(World world, int interval, int offset)
	{
		return world != null && world.isRemote && canTick(world.getGameTime(), interval, offset);
	}

	public static boolean canTrigger(World world, double rate)
	{
		return world != null && world.rand.nextDouble() < rate;
	}
	public static boolean canTrigger(ToolAttackContext context, ForgeConfigSpec.DoubleValue rate)
	{
		return canTrigger(context.getAttacker().world, rate.get());
	}
	public static boolean canTrigger(ToolHarvestContext context, ForgeConfigSpec.DoubleValue rate)
	{
		return canTrigger(context.getWorld(), rate.get());
	}

	public static boolean isAir(BlockState state)
	{
		return state.getBlock() == Blocks.AIR;
	}
	public static boolean isDirt(BlockState state)
	{
		return state.getBlock() == Blocks.DIRT;
	}
}
