package firok.tiths.util;

import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityFinders
{
	public static List<? extends Entity> Nearby(World world, BlockPos center, double distance)
	{
		return Nearby(world,center,distance,en->true);
	}
	public static List<? extends Entity> Nearby(World world, BlockPos center, double distance, Predicate<? super Entity> predicate)
	{
		List<Entity> ret=new ArrayList<>();
		List<Entity> temps=world.getEntitiesWithinAABB(Entity.class,Ranges.Neighbour(center,distance),predicate);
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
		List<Entity> temps=center.world.getEntitiesWithinAABBExcludingEntity(center,Ranges.Neighbour(center,distance));
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
		List<Entity> temps=center.world.getEntitiesInAABBexcluding(center,Ranges.Neighbour(center,distance), predicate);
		final double distanceSq=distance*distance;
		for(Entity temp:temps)
		{
			if(center.getDistanceSqToEntity(temp)<=distanceSq)
				ret.add(temp);
		}

		return ret;
	}
}
