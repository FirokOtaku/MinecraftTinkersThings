package firok.tiths.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

// 遍历用的东西
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

	public static AxisAlignedBB Neighbour(Entity entity,double distance)
	{
		return new AxisAlignedBB(
				entity.posX-distance,entity.posY-distance,entity.posZ-distance,
				entity.posX+distance,entity.posY+distance,entity.posZ+distance
		);
	}
}
