package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

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
		if(temps.isEmpty()) return ret;
		final double distanceSq=distance*distance;
		for(Entity temp:temps)
		{
			if(temp.getDistanceSq(center.getX(), center.getY(), center.getZ())<=distanceSq)
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
			if(center.getDistanceSq(temp)<=distanceSq)
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
			if(center.getDistanceSq(temp)<=distanceSq)
				ret.add(temp);
		}

		return ret;
	}

	public static List<Entity> Facing(Entity center,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world, center, center.getPositionVec(), Calculates.getEntityForward(center),distanceFacingMax,distanceLineMax,predicate);
	}
	public static List<Entity> FacingEye(Entity center,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world,center,center.getPositionVec().add(0,center.getEyeHeight(),0),Calculates.getEntityForward(center),distanceFacingMax,distanceLineMax,predicate);
	}
	public static List<Entity> FacingHeight(Entity center,final double height,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		return Facing(center.world,center,center.getPositionVec().add(0,height,0),Calculates.getEntityForward(center),distanceFacingMax,distanceLineMax,predicate);
	}

	public static List<Entity> Facing(World world,Entity excluding,Vector3d center,Vector3d facing,final double distanceFacingMax,final double distanceLineMax, Predicate<? super Entity> predicate)
	{
		List<Entity> temps=world.getEntitiesInAABBexcluding(excluding,Ranges.Neighbour(center,distanceFacingMax+2), predicate);
		final int tempSize=temps.size();

		Vector3d[] posTemps=new Vector3d[tempSize];

		for(int i=0;i<tempSize;i++)
		{
			Entity enTemp=temps.get(i);
			posTemps[i]=enTemp.getPositionVec().add(0,enTemp.getEyeHeight() / 2,0);
		}

		double[] distances = Calculates.distanceToLine(center,facing,posTemps);

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

			Vector3d spaceFacing = new Vector3d(
					entity.getPosX() - center.x,
					entity.getPosY() - center.y,
					entity.getPosZ() - center.z
			);
			if(spaceFacing.dotProduct(facing)<=0) continue; // 点乘 只取面向的实体

			ret.add(entity);
		}
//		System.out.println(str);
		return ret;
	}
}