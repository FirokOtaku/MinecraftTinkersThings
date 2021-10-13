package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3d;

public class Ranges
{
	public static AxisAlignedBB Neighbour(Entity entity, double distance)
	{
		Vector3d pos = entity.getPositionVec();
		return new AxisAlignedBB(
				pos.x - distance, pos.y - distance, pos.z - distance,
				pos.x + distance, pos.y + distance, pos.z + distance
		);
	}
}
