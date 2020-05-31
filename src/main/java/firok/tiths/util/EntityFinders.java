package firok.tiths.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public final class EntityFinders
{
	private EntityFinders(){}

	public static List<? extends Entity> Nearby(World world, BlockPos center, double distance)
	{
		return Nearby(world,center,distance,en->true);
	}
	public static List<? extends Entity> Nearby(World world, BlockPos center, double distance, Predicate<? super Entity> predicate)
	{
		List<Entity> ret=new ArrayList<>();
		List<Entity> temps=world.getEntitiesWithinAABB(Entity.class,Ranges.Neighbour(center,distance+2),predicate);
		final double distanceSq=distance*distance;
		for(Entity temp:temps)
		{
			if(temp.getDistanceSq(center)<=distanceSq)
				ret.add(temp);
		}

		return ret;
	}
	public static List<? extends Entity> Nearby(Entity center,double distance)
	{
		List<Entity> ret=new ArrayList<>();
		List<Entity> temps=center.world.getEntitiesWithinAABBExcludingEntity(center,Ranges.Neighbour(center,distance+2));
		final double distanceSq=distance*distance;
		for(Entity temp:temps)
		{
			if(center.getDistanceSqToEntity(temp)<=distanceSq)
				ret.add(temp);
		}

		return ret;
	}

	public static List<Entity> Nearby(Entity center, double distance, Predicate<? super Entity> predicate)
	{
		List<Entity> ret=new ArrayList<>();
		List<Entity> temps=center.world.getEntitiesInAABBexcluding(center,Ranges.Neighbour(center,distance+2), predicate);
		final double distanceSq=distance*distance;
		for(Entity temp:temps)
		{
			if(center.getDistanceSqToEntity(temp)<=distanceSq)
				ret.add(temp);
		}

		return ret;
	}

	public static List<Entity> Facing(Entity center,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world, center, center.getPositionVector(),center.getForward(),distanceFacingMax,distanceLineMax,predicate);
	}
	public static List<Entity> FacingEye(Entity center,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world,center,center.getPositionVector().addVector(0,center.getEyeHeight(),0),center.getForward(),distanceFacingMax,distanceLineMax,predicate);
	}
	public static List<Entity> FacingHeight(Entity center,final double height,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world,center,center.getPositionVector().addVector(0,height,0),center.getForward(),distanceFacingMax,distanceLineMax,predicate);
	}

	public static List<Entity> Facing(World world,Entity excluding,Vec3d center,Vec3d facing,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		List<Entity> temps=world.getEntitiesInAABBexcluding(excluding,Ranges.Neighbour(center,distanceFacingMax+2), predicate);
		final int tempSize=temps.size();

		Vec3d[] posTemps=new Vec3d[tempSize];

		for(int i=0;i<tempSize;i++)
		{
			Entity enTemp=temps.get(i);
			posTemps[i]=enTemp.getPositionVector().addVector(0,enTemp.getEyeHeight() / 2,0);
		}

		double[] distances=Actions.distanceToLine(center,facing,posTemps);

//		StringBuilder str=new StringBuilder();
		List<Entity> ret=new ArrayList<>();
		for(int i=0;i<tempSize;i++)
		{
			Entity entity=temps.get(i);
			// 勾股定理 a^2+b^2=c^2
			double tempDistanceLine=distances[i]; // b
//			str.append(String.format("临时距离line:%f",tempDistanceLine));
			if(tempDistanceLine>distanceLineMax) continue;

			double tempDistanceCenterSq=posTemps[i].squareDistanceTo(center); // c^2
			double tempDistanceFacingSq= tempDistanceCenterSq - tempDistanceLine * tempDistanceLine; // a^2
//			str.append(String.format("  临时面向距离:%f\n",tempDistanceFacing));
			if(tempDistanceFacingSq>distanceFacingMax * distanceFacingMax) continue;

			Vec3d spaceFacing=new Vec3d( entity.posX-center.x, entity.posY-center.y, entity.posZ-center.z );
			if(spaceFacing.dotProduct(facing)<=0) continue; // 点乘 只取面向的实体

			ret.add(entity);
		}
//		System.out.println(str);
		return ret;
	}
}
