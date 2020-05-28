package firok.tiths.potion;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

import static firok.tiths.common.Keys.colorPotionTurbulent;

/**
 * 乱流
 */
public class PotionTurbulent extends BasePotion
{
	public PotionTurbulent()
	{
		super(icon("turbulent"),true,colorPotionTurbulent);
	}

	@Override
	public boolean isReady(int tick, int level)
	{
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level)
	{
		if(entity.isServerWorld() && entity.isInWater())
		{
			Vec3d motion=getMotion(entity);
			entity.motionX+= motion.x;
			entity.motionY+= motion.y;
			entity.motionZ+= motion.z;
		}
	}

	private static double withShift(double value1,double value2,int shifting,int shiftingMax)
	{
		return value1 + (value2-value1) / shiftingMax * shifting;
	}
	public static Vec3d getMotion(Entity entity)
	{
		flag++;
		if(flag>5000) reloadMotion();

		long time=entity.ticksExisted;

		int period=(int)(time / 20 % 100); // 当前周期
		int shifting=(int)(time % 20); // 当前偏移

		final double factor=0.05;

		double rx=withShift(mx[period],mx[period+1],shifting,20) * factor;
		double ry=withShift(my[period],my[period+1],shifting,20) * factor;
		double rz=withShift(mz[period],mz[period+1],shifting,20) * factor;

		return new Vec3d(rx,ry,rz);
	}

	private static double[] mx;
	private static double[] my;
	private static double[] mz;
	private static int flag;
	private static void reloadMotion()
	{
		flag=0;
		Random rand=new Random();
		mx =new double[101];
		my =new double[101];
		mz =new double[101];
		for(int i=0;i<101;i++)
		{
			Vec3d direction=Vec3d.fromPitchYaw( rand.nextFloat() * 180 - 90, rand.nextFloat() * 360 - 180 );
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
