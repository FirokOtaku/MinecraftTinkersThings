package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;

public class Ranges
{
	public static BlockPos[] Neighbour(BlockPos center)
	{
		return new BlockPos[]{
				center.up(),
				center.down(),
				center.east(),
				center.west(),
				center.south(),
				center.north(),
		};
	}
	public static AxisAlignedBB Neighbour(Entity entity, double distance)
	{
		Vector3d pos = entity.getPositionVec();
		return new AxisAlignedBB(
				pos.x - distance, pos.y - distance, pos.z - distance,
				pos.x + distance, pos.y + distance, pos.z + distance
		);
	}
	public static AxisAlignedBB Neighbour(BlockPos center,double distance)
	{
		return new AxisAlignedBB(
				center.getX()-distance,center.getY()-distance,center.getZ()-distance,
				center.getX()+distance,center.getY()+distance,center.getZ()+distance
		);
	}
	public static AxisAlignedBB Neighbour(Vector3d center,double distance)
	{
		return new AxisAlignedBB(
				center.x-distance,center.y-distance,center.z-distance,
				center.x+distance,center.y+distance,center.z+distance
		);
	}
}
