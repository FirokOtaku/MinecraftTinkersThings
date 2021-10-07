package firok.tiths.util;

import net.minecraft.world.World;

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
}
