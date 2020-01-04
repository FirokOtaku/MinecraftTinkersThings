package firok.tiths.world;

import net.minecraft.world.World;

import java.util.Random;

@FunctionalInterface
public interface IChunkGen
{
	/**
	 * 在指定区块生成结构
	 * @param world 世界
	 * @param chunkX 区块顶点坐标
	 * @param chunkZ 区块顶点坐标
	 * @param rand 随机数生成器
	 * @return 是否生成
	 */
	boolean gen(World world, int chunkX, int chunkZ, Random rand);
}
