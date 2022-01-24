package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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

	public static final double PI_2 = Math.PI / 2, PI_4 = Math.PI / 4, PI_6 = Math.PI / 6, PI = Math.PI;

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

	/**
	 * 因为{@link Entity#getForward}是客户端专有方法, 所以做了一个封装方法
	 */
	public static Vector3d getEntityForward(Entity entity)
	{
		if(entity == null) return new Vector3d(0,0,0);
		else return Vector3d.fromPitchYaw(entity.rotationPitch, entity.rotationYaw);
	}

	/**
	 * 用来求若干点到直线距离
	 * @param p1 直线上一点坐标
	 * @param facing 方向向量
	 * @param points 若干需要求的点
	 * @return 直线导致这些点的距离
	 */
	public static double[] distanceToLine(Vector3d p1, Vector3d facing, Vector3d... points)
	{
		if(points==null) return new double[0];
		double[] ret=new double[points.length];

		Vector3d p2=p1.add(facing); // 第2个点

		double d12=p1.distanceTo(p2); // 底边长度
		for(int i=0;i<points.length;i++)
		{
			// 海伦公式 Heron's formula
			// https://baike.baidu.com/item/%E6%B5%B7%E4%BC%A6%E5%85%AC%E5%BC%8F
			Vector3d d3=points[i];

			// 求出剩下两边长
			double d32=d3.distanceTo(p2);
			double d31=d3.distanceTo(p1);

			double p=(d12+d32+d31)/2; // 半周长
			double pp1=Math.abs(p-d12),pp2=Math.abs(p-d32),pp3=Math.abs(p-d31);

			double area= MathHelper.sqrt( p*pp1*pp2*pp3 ); // 三角形面积

			// 三角形面积=底×高÷2 => 三角形面积×2÷底=高
			ret[i] = area * 2 / d12;

//			System.out.printf("d12[%f],d31[%f],d32[%f],p[%f],area[%f],ret[%f],pp1~3[%f,%f,%f]\n",d12,d31,d32,p,area,ret[i],pp1,pp2,pp3);
		}

//		System.out.println("到直线距离"+Arrays.toString(ret));

		return ret;
	}

	/**
	 * 计算单位向量
	 */
	public static Vector3d calcUnitVector(Vector3d vec)
	{
		double length=vec.x * vec.x + vec.y * vec.y + vec.z * vec.z;
		length = Math.sqrt(length);

		if(length!=0) return new Vector3d( vec.x / length, vec.y / length, vec.z / length );
		else return null;
	}
}
