package firok.tiths.world;

import net.minecraft.world.World;

import java.util.Random;

/**
 * 世界内容生成器
 */
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

	/**
	 * 检查能否在指定维度生成
	 * @param targetDimId 指定维度id
	 * @return 是否生成
	 */
	default boolean canGenAtDim(int targetDimId)
	{
		return true;
	}
}
