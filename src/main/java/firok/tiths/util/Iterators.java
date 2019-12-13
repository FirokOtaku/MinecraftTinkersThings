package firok.tiths.util;

import net.minecraft.util.math.BlockPos;

// 遍历用的东西
public class Iterators
{
	public static BlockPos[] Neibour(BlockPos center)
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
}
