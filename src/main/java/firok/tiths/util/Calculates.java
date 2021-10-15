package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

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

	public static int between(Random rand,int min,int max)
	{
		return min + (max > min ? rand.nextInt(1+max-min) : 0);
	}
	public static float between(Random rand, float min, float max)
	{
		return min + rand.nextFloat() * (max-min);
	}
	public static int range(int value, int min, int max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	public static float range(float value, float min, float max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
}
