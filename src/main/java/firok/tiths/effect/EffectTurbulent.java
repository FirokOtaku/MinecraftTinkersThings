package firok.tiths.effect;

import firok.tiths.util.DevUse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectType;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

/**
 * 乱流
 * https://github.com/351768593/MinecraftTinkersThings/blob/indev1122/src/main/java/firok/tiths/potion/PotionTurbulent.java
 */
@DevUse(isPlaceholder = true)
public class EffectTurbulent extends TithsEffect
{
	EffectTurbulent()
	{
		super(EffectType.HARMFUL, 0x062147);
		setNoCure();
	}

	@Override
	public boolean isReady(int duration, int level)
	{
		return true;
	}

	@Override
	public void performEffect(LivingEntity living, int level)
	{
		if(living.isServerWorld() && living.isInWater())
		{
			Vector3d motionOld = living.getMotion();
			Vector3d motionOffset = getMotion(living);

			living.setMotion(motionOld.add(motionOffset));
		}
	}

	private static double withShift(double value1,double value2,int shifting,int shiftingMax)
	{
		return value1 + (value2-value1) / shiftingMax * shifting;
	}

	public static Vector3d getMotion(Entity entity)
	{
		flag++;
		if(flag>5000) {reloadMotion();}

		long time=entity.ticksExisted;

		int period=(int)(time / 20 % 100); // 当前周期
		int shifting=(int)(time % 20); // 当前偏移

		final double factor=0.05;

		double rx=withShift(mx[period],mx[period+1],shifting,20) * factor;
		double ry=withShift(my[period],my[period+1],shifting,20) * factor;
		double rz=withShift(mz[period],mz[period+1],shifting,20) * factor;

		return new Vector3d(rx,ry,rz);
	}

	private static double[] mx;
	private static double[] my;
	private static double[] mz;
	private static int flag;
	private static void reloadMotion()
	{
		flag=0;
		Random rand=new Random();
		mx = new double[101];
		my = new double[101];
		mz = new double[101];
		for(int i=0;i<101;i++)
		{
			Vector3d direction = Vector3d.fromPitchYaw( rand.nextFloat() * 180 - 90, rand.nextFloat() * 360 - 180 );
			mx[i] = direction.x;
			my[i] = direction.y;
			mz[i] = direction.z;
		}
	}
	static
	{
		reloadMotion();
	}
}
