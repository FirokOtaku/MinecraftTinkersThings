package firok.mtim.util;

import net.minecraft.world.World;

public final class Predicates
{
	private Predicates(){}

	public static boolean canTick(long time,int interval,int offset)
	{
		return time%interval==offset;
	}
	public static boolean canTick(World world,int interval,int offset)
	{
		return canTick(world.getTotalWorldTime(),interval,offset);
	}
}
