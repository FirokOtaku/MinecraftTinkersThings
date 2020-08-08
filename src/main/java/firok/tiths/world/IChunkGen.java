package firok.tiths.world;

import firok.tiths.common.Configs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 世界内容生成器
 */
public interface IChunkGen
{
	/**
	 * 获取此生成器主要生成的{@link IBlockState}
	 */
	IBlockState getMainState();

	/**
	 * 在指定区块生成结构
	 * @param world 世界
	 * @param chunkVertexX 区块顶点坐标
	 * @param chunkVertexZ 区块顶点坐标
	 * @param rand 随机数生成器
	 * @return 本次生成所替换的方块坐标
	 */
	@Deprecated // 尽量不要重写这个方法
	default List<BlockPos> genAtChunk(World world, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		return new ArrayList<>();
	}

	/**
	 * 根据区块坐标和其它信息生成一个随机真实坐标
	 * @param world 世界
	 * @param chunkVertexX 区块顶点坐标
	 * @param chunkVertexZ 区块顶点坐标
	 * @param rand 随机数生成器
	 * @param interval 间隔
	 * @return 真实坐标
	 */
	@Deprecated // 尽量不要重写这个方法
	default BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand, int interval)
	{
		final int posX=chunkVertexX+interval+rand.nextInt(16-interval*2);
		final int posY=rand.nextInt(world.getHeight());
		final int posZ=chunkVertexZ+interval+rand.nextInt(16-interval*2);
		return new BlockPos(posX,posY,posZ);
	}

	/**
	 * 根据区块坐标和其它信息生成一个随机真实坐标
	 */
	@Deprecated // 尽量不要重写这个方法
	default BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		return genRealPos(world, chunkVertexX, chunkVertexZ, rand, 4);
	}

	/**
	 * 在指定真实坐标生成结构, 这个方法是最主要的方法<br>
	 * 请注意, 请不要直接读写世界数据, 而是调用{@link #setState}和{@link #getState}, 这两个方法会根据配置文件自动限制跨区块读写
	 * @param world 世界
	 * @param posX 真实坐标
	 * @param posY 真实坐标
	 * @param posZ 真实坐标
	 * @param chunkVertexX 区块顶点X坐标
	 * @param chunkVertexZ 区块顶点Z坐标
	 * @param rand 随机数生成器
	 * @return 本次生成所替换的方块坐标
	 * @see #setState
	 * @see #getState
	 */
	// 尽量只重写这个方法
	default List<BlockPos> genAtRealPos(World world, int posX, int posY, int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		return new ArrayList<>();
	}

	/**
	 * 检查能否在指定维度生成
	 * @param targetDimId 指定维度id
	 * @param world 世界
	 * @param provider 维度元
	 * @return 是否生成
	 */
	@Deprecated // 尽量不要重写这个方法
	default boolean canGenAtDim(int targetDimId, World world, WorldProvider provider)
	{
		return true;
	}

	/**
	 * 检查能否在指定生物群系生成
	 * @param targetBiomeId 指定生物群系名称
	 * @param world 世界
	 * @param biome 生物群系
	 * @return 是否生成
	 */
	@Deprecated // 尽量不要重写这个方法
	default boolean canGenAtBiome(String targetBiomeId, World world, Biome biome)
	{
		return true;
	}

	static boolean crossChunk(BlockPos pos,int chunkVX,int chunkVZ)
	{
		return pos.getX()<= chunkVX || pos.getX()> chunkVX+15 || pos.getZ()<=chunkVZ || pos.getZ()>chunkVZ+15;
	}

	/**
	 * 使用这个方法进行世界数据的读写
	 */
	static IBlockState getState(World world, BlockPos pos, int chunkVX, int chunkVZ)
	{
		return Configs.General.block_cross_chunk_generation && crossChunk(pos, chunkVX, chunkVZ)?
				Blocks.AIR.getDefaultState():
				world.getBlockState(pos);
	}
	/**
	 * 使用这个方法进行世界数据的读写
	 */
	static void setState(World world,BlockPos pos,IBlockState state,int chunkVX,int chunkVZ)
	{
		setState(world, pos, state, 2, chunkVX, chunkVZ);
	}
	static void setState(World world,BlockPos pos,IBlockState state,int flag,int chunkVX,int chunkVZ)
	{
		if (!Configs.General.block_cross_chunk_generation || !crossChunk(pos, chunkVX, chunkVZ))
		{
			world.setBlockState(pos,state,flag);
		}
	}

	static boolean isAirBlock(World world,BlockPos pos,int chunkVX,int chunkVZ)
	{
		IBlockState state=getState(world, pos, chunkVX, chunkVZ);
		return state.getBlock().isAir(state,world,pos);
	}
}
