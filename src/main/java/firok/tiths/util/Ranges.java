package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

// 遍历用的东西
public final class Ranges
{
	private Ranges(){}

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

	public static AxisAlignedBB Neighbour(Entity entity,double distance)
	{
		return new AxisAlignedBB(
				entity.posX-distance,entity.posY-distance,entity.posZ-distance,
				entity.posX+distance,entity.posY+distance,entity.posZ+distance
		);
	}
	public static AxisAlignedBB Neighbour(BlockPos center,double distance)
	{
		return new AxisAlignedBB(
				center.getX()-distance,center.getY()-distance,center.getZ()-distance,
				center.getX()+distance,center.getY()+distance,center.getZ()+distance
		);
	}
	public static AxisAlignedBB Neighbour(Vec3d center,double distance)
	{
		return new AxisAlignedBB(
				center.x-distance,center.y-distance,center.z-distance,
				center.x+distance,center.y+distance,center.z+distance
		);
	}
}
