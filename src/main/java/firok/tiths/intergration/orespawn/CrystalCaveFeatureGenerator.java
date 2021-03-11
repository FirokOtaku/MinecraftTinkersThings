package firok.tiths.intergration.orespawn;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.FeatureBase;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.os3.ISpawnEntry;
import com.mcmoddev.orespawn.api.os3.OreSpawnBlockMatcher;
import com.mcmoddev.orespawn.data.Constants;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 晶洞生成器
 * @see com.mcmoddev.orespawn.impl.features.DefaultFeatureGenerator
 * @since 0.3.29.0 第四次世界生成模块修改
 */
public class CrystalCaveFeatureGenerator extends FeatureBase implements IFeature
{
	public CrystalCaveFeatureGenerator()
	{
		super(new Random());
	}
	public CrystalCaveFeatureGenerator(Random rand)
	{
		super(rand);
	}

	@Override
	public void generate(World world, IChunkGenerator chunkGenerator,
	                     IChunkProvider chunkProvider, ISpawnEntry spawnData, ChunkPos pos)
	{
		final JsonObject params = spawnData.getFeature().getFeatureParameters();

		// First, load cached blocks for neighboring chunk ore spawns
		final int chunkX = pos.x;
		final int chunkZ = pos.z;

		mergeDefaults(params, getDefaultParameters());

		runCache(chunkX, chunkZ, world, spawnData);

		// now to ore spawn
		final int minY = InnerActions.range(params.get(Constants.FormatBits.MIN_HEIGHT).getAsInt(),0,256);
		final int maxY = InnerActions.range(params.get(Constants.FormatBits.MAX_HEIGHT).getAsInt(),0,256);
		final float freq = InnerActions.range(params.get(Constants.FormatBits.FREQUENCY).getAsFloat(),0,256);

		final int radiusXZ = InnerActions.range(params.get("radiusXZ").getAsInt(),5,20);
		final int radiusY = InnerActions.range(params.get("radiusY").getAsInt(),5,20);
		final float factorSplit = InnerActions.range(params.get("factorSplit").getAsFloat(),0,1);
		final float rateOrePillar = InnerActions.range(params.get("rateOrePillar").getAsFloat(),0,1);
		final int topDownMax = InnerActions.range(params.get("topDown").getAsInt(),0,8);
		final int bottomUpMax = InnerActions.range(params.get("bottomUp").getAsInt(),0,8);
		final int maxWaterDepth = InnerActions.range(params.get("maxWaterDepth").getAsInt(),0,5);

		if(!canTrigger(world.rand,freq)) return;

		final Random rand = world.rand;

		final BlockPos posCenter = new BlockPos(pos.getXStart()+8,InnerActions.between(rand,minY,maxY),pos.getZStart()+8);

		int[][][] cache = new int[radiusXZ*2+1][radiusY*2+1][radiusXZ*2+1];
		int[][] cacheTops = InnerActions.initFillArray(radiusXZ*2+1,radiusXZ*2+1,-1);
		int[][] cacheDowns = InnerActions.initFillArray(radiusXZ*2+1,radiusXZ*2+1,-1);

		final IBlockState stateStone = Blocks.STONE.getDefaultState();
//		final IBlockState stateOre = Blocks.GOLD_ORE.getDefaultState(); // 现在使用Ore Spawn 3随机生成矿石类型
		final IBlockState stateAir = Blocks.AIR.getDefaultState();

		final OreSpawnBlockMatcher matcher = spawnData.getMatcher();

		FOR_X: for(int ox=-radiusXZ; ox <= radiusXZ; ox++)
		{
			FOR_Z: for(int oz=-radiusXZ; oz <= radiusXZ; oz++)
			{
				FOR_Y: for(int oy=-radiusY; oy <= radiusY; oy++)
				{
					float factor = (1f*ox*ox)/(radiusXZ*radiusXZ) + (1f*oy*oy)/(radiusY*radiusY) + (1f*oz*oz)/(radiusXZ*radiusXZ);

					// 根据椭球公式计算当前位置应该是什么方块
					if(factor > 1)
					{
						cache[radiusXZ+ox][radiusY+oy][radiusXZ+oz] = 1; // stone // 其实现在是直接不处理这块的内容
					}
					else if(factor >= factorSplit)
					{
						cache[radiusXZ+ox][radiusY+oy][radiusXZ+oz] = 2; // ore
					}
					else
					{
						cache[radiusXZ+ox][radiusY+oy][radiusXZ+oz] = 3; // air

						// 计算当前横向坐标方位 顶部和底部的空气方块在哪
						if(cacheTops[ox+radiusXZ][oz+radiusXZ] == -1 || oy+radiusY > cacheTops[ox+radiusXZ][oz+radiusXZ])
						{
							cacheTops[ox+radiusXZ][oz+radiusXZ] = oy;
						}
						if(cacheDowns[ox+radiusXZ][oz+radiusXZ] == -1 || oy+radiusY < cacheDowns[ox+radiusXZ][oz+radiusXZ])
						{
							cacheDowns[ox+radiusXZ][oz+radiusXZ] = oy;
						}
					}
				}
			}
		}

		FOR_X: for(int ox=-radiusXZ; ox <= radiusXZ; ox++)
		{
			FOR_Z: for(int oz=-radiusXZ; oz <= radiusXZ; oz++)
			{

				int top = cacheTops[ox+radiusXZ][oz+radiusXZ], down = cacheDowns[ox+radiusXZ][oz+radiusXZ];

				// 计算当前横向坐标方位的上下方偏移量
				final int topDown = topDownMax > 0 && canTrigger(rand,rateOrePillar) ? rand.nextInt(topDownMax) : 0;
				final int bottomUp = bottomUpMax > 0 && canTrigger(rand,rateOrePillar) ? rand.nextInt(bottomUpMax) : 0;

				FOR_Y: for(int oy=-radiusY; oy <= radiusY; oy++)
				{
					int factor = cache[radiusXZ+ox][radiusY+oy][radiusXZ+oz];

					IBlockState state = stateStone;
					BlockPos posOffset = posCenter.add(ox,oy,oz);

					if(factor == 1 || !matcher.test(world.getBlockState(posOffset)) ) // stone
					{
						continue FOR_Y;
//						state = stateStone;
					}
					else if(factor == 2) // ore
					{
						state = spawnData.getBlocks().getRandomBlock(rand);
					}
					else if(factor == 3) // air
					{
						state = oy >= top - topDown || oy <= down + bottomUp ? spawnData.getBlocks().getRandomBlock(rand) : stateAir;
					}

					world.setBlockState(posOffset,state);
				}
			}
		}
	}

	@Override
	public JsonObject getDefaultParameters() {
		final JsonObject defParams = new JsonObject();
		defParams.addProperty(Constants.FormatBits.MIN_HEIGHT, 0);
		defParams.addProperty(Constants.FormatBits.MAX_HEIGHT, 256);
		defParams.addProperty(Constants.FormatBits.FREQUENCY, 0.5);

		defParams.addProperty("radiusXZ", 8);
		defParams.addProperty("radiusY",6);
		defParams.addProperty("factorSplit",0.85);
		defParams.addProperty("rateOrePillar",0.55);
		defParams.addProperty("topDown",4);
		defParams.addProperty("bottomUp",4);
		defParams.addProperty("maxWaterDepth",0);
		return defParams;
	}

	@Override
	public void setRandom(final Random rand) {
		this.random = rand;
	}
}
