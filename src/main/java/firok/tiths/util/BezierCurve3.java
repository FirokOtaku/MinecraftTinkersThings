package firok.tiths.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * 三次方贝塞尔曲线
 *
 * https://zh.wikipedia.org/wiki/%E8%B2%9D%E8%8C%B2%E6%9B%B2%E7%B7%9A
 */
public class BezierCurve3
{
	/**
	 * 曲线参数
	 * */
	public final Vector3d p0, p1, p2, p3;
	/**
	 * 缓存
	 * todo 以后可能加入缓存
	 */
	private final Vector3d[] buffer;

	/**
	 * 无缓冲曲线
	 */
	public BezierCurve3(Vector3d p0, Vector3d p1, Vector3d p2, Vector3d p3)
	{
		this(p0, p1, p2, p3, 0);
	}

	/**
	 * 带缓冲曲线
	 */
	@Deprecated
	public BezierCurve3(Vector3d p0, Vector3d p1, Vector3d p2, Vector3d p3, int sizeCache)
	{
		this.p0 = Objects.requireNonNull(p0);
		this.p1 = Objects.requireNonNull(p1);
		this.p2 = Objects.requireNonNull(p2);
		this.p3 = Objects.requireNonNull(p3);

		if(sizeCache > 0)
		{
			buffer = new Vector3d[sizeCache];
		}
		else
		{
			buffer = null;
		}
	}

	/**
	 * 根据公式 直接计算曲线上一点的坐标
	 * @param t 参数. 范围0~1
	 * @return 如果参数不在范围, 返回null; 否则返回计算后的点
	 */
	@Nullable
	public Vector3d calc(double t)
	{
		if(t < 0 || t > 1) return null;

		double tr = 1 - t;

		return p0.scale( tr * tr * tr ).add(
				p1.scale(3).scale(t * tr * tr)
		).add(
				p2.scale(3).scale(t * t * tr)
		).add(
				p3.scale(t * t * t)
		);
	}

	/**
	 * 将曲线数据序列化为long数组
	 */
	public long[] toLongArray()
	{
		return new long[] {
				Double.doubleToRawLongBits(p0.x),
				Double.doubleToRawLongBits(p0.y),
				Double.doubleToRawLongBits(p0.z),

				Double.doubleToRawLongBits(p1.x),
				Double.doubleToRawLongBits(p1.y),
				Double.doubleToRawLongBits(p1.z),

				Double.doubleToRawLongBits(p2.x),
				Double.doubleToRawLongBits(p2.y),
				Double.doubleToRawLongBits(p2.z),

				Double.doubleToRawLongBits(p3.x),
				Double.doubleToRawLongBits(p3.y),
				Double.doubleToRawLongBits(p3.z),
		};
	}

	/**
	 * 从long数组提取数据为贝塞尔曲线
	 */
	public static BezierCurve3 fromLongArray(long[] array)
	{
		return fromLongArray(array, 0);
	}
	/**
	 * 从long数组提取数据为贝塞尔曲线
	 */
	@Deprecated
	public static BezierCurve3 fromLongArray(long[] array, int sizeBuffer)
	{
		return new BezierCurve3(
				new Vector3d(array[0], array[1], array[2]),
				new Vector3d(array[3], array[4], array[5]),
				new Vector3d(array[6], array[7], array[8]),
				new Vector3d(array[9], array[10], array[11]),
				sizeBuffer
		);
	}
}
