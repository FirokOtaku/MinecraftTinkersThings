package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * util class for many calculations
 */
public final class Calculates
{
	private Calculates() { }

	public static int getLight(Entity entity)
	{
		World world = entity.world;
		BlockPos pos = entity.getPosition();
		return world.getLight(pos);
	}

	/**
	 * 角度弧度互相换算用的
	 */
	public static final float FAC = (float) Math.PI/180;
}
