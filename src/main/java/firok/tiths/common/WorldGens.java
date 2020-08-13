package firok.tiths.common;

import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import firok.tiths.util.reg.FieldStream;
import firok.tiths.util.reg.GenOre;
import firok.tiths.world.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.*;


public class WorldGens implements IWorldGenerator
{
	private static volatile WorldGens instance;
	public static WorldGens getInstance()
	{
		if(instance==null)
		{
			instance=new WorldGens();
			instance.reload();
		}
		return instance;
	}

	/**
	 * 生成器列表
	 */
	List<IChunkGen> generators=new ArrayList<>();

	private static int[] r(int[] ts)
	{
		return ts!=null && ts.length>0? ts : null;
	}
	private static String[] r(String[] ts)
	{
		return ts!=null && ts.length>0? ts : null;
	}
	private volatile boolean isLoading=false;
	public boolean isLoading()
	{
		return isLoading;
	}
	public void reload()
	{
		if(isLoading) return;

		isLoading=true;
		List<IChunkGen> list=new ArrayList<>();

		list.add(new WorldGenTreeRoot(defaultTreeRoot,"TREE_ROOT"));
		list.add(new WorldGenMinableBedrock(defaultBrokenBedrockInfo,"BROKEN_BEDROCK"));
		list.add(new WorldGenLavaCrystal(defaultLavaCrystal,"LAVA_CRYSTAL"));

		list.add(new WorldGenCloud(defaultCloudInfo,"CLOUD"));

		list.add(new WorldGenSeaGlass(defaultSeaGrassInfo,"SEA_GRASS"));

		FieldStream.of(Blocks.class,null,Block.class)
				.forEach((field, anno, block) -> {

					GenOre ga=field.getAnnotation(GenOre.class);
					Info infoAnno=ga!=null?Info.build(
							block.getDefaultState(),

							ga.dim(), r(ga.dimsWL()),r(ga.dimsBL()),
							ga.biome(), r(ga.biomeWL()),r(Keys.getBiomes(ga.biomeBL())),
							Predicates.getPredicateIBlockState(ga.selector(), Predicates::isStone),

							ga.minY(),ga.maxY(),
							ga.times(),(float)ga.timeRate(),
							ga.size()
					):null;
					Info infoJson= ConfigJson.getOre(block.getUnlocalizedName());
					Info info2add= null;

					if(infoAnno==null)
					{
						if(infoJson!=null && infoJson.complete())
						{
							info2add=infoJson;
						}
					}
					else
					{
						info2add=Info.build(infoAnno,infoJson);
					}

					if(info2add==null || !Info.enable(info2add,null,false))
					{
						return;
					}

					GenMinable gen=new GenMinable( info2add );
					list.add(gen);

				});

		generators=list;

		isLoading=false;
	}
	public static final Info defaultLavaCrystal=Info.build(
			Blocks.oreLavaCrystal.getDefaultState(),
			Strategy.ONLY_WHITELIST,new int[]{-1},null,
			Strategy.NONE_BLACKLIST,null,null,
			Predicates::isNetherrack,
			10,110,
			4,0.25f,1
	);
	public static final Info defaultTreeRoot=Info.build(
			Blocks.oreTreeRoot.getDefaultState(),
			Strategy.NONE_BLACKLIST,null,null,
			Strategy.NONE_BLACKLIST,null,null,
			Predicates::isDirt,
			0,255,
			1,1,1
	);
	public static final Info defaultBrokenBedrockInfo=Info.build(
			Blocks.oreBrokenBedrock.getDefaultState(),
			Strategy.NONE_BLACKLIST,null,null,
			Strategy.NONE_BLACKLIST,null,null,
			Predicates::isStone,
			0,5,
			3,0.6f,8
	);
	public static final Info defaultCloudInfo=Info.build(
			Blocks.oreBrumeJade.getDefaultState(),
			Strategy.ONLY_WHITELIST,new int[]{0},null,
			Strategy.NONE_BLACKLIST,null,null,
			Predicates::isAir,
			150,180,
			1,0.015f,7
	);
	public static final Info defaultSeaGrassInfo=Info.build(
			Blocks.blockSeaGrass.getDefaultState(),
			Strategy.ONLY_WHITELIST,new int[]{0},null,
			Strategy.ONLY_WHITELIST,Keys.biomes_sea,null,
			Predicates::isWater,
			120,80,
			4,0.8f,1
	);


	int preChunkX=Integer.MIN_VALUE, preChunkZ =Integer.MIN_VALUE;
	World preWorld=null;
	public void gen(World world, int chunkX, int chunkZ, Random rand) // note event传进来的是区块号
	{
		if(preWorld==world&&preChunkX==chunkX&&preChunkZ==chunkZ) return;

		preWorld=world;
		preChunkX=chunkX;
		preChunkZ=chunkZ;

		int chunkVX=chunkX*16,chunkVZ=chunkZ*16; // 区块顶点坐标

		int targetDimId=world.provider.getDimension();
		WorldProvider provider=world.provider;
		Biome biome=world.getBiome(new BlockPos(chunkVX+8,1,chunkVZ+8));
		String targetBiomeName=String.valueOf(biome.getRegistryName());

		if(InnerActions.has(Configs.General.blacklist_dim_generation,targetDimId) ||
			InnerActions.has(Configs.General.blacklist_biome_generation,targetBiomeName)
		){
			return; // 被全局黑名单禁用
		}

		if(Configs.General.log_chunk_generation) // 启用世界生成日志
		{
			for(IChunkGen genWorld:generators) // 检查能不能生成在指定世界
			{
				boolean canGen=genWorld.canGenAtDim(targetDimId,world,provider) &&
						genWorld.canGenAtBiome(targetBiomeName, world, biome);
				if(!canGen) continue;

				List<BlockPos> listGeneration = genWorld.genAtChunk(world, chunkVX, chunkVZ, rand);

				IBlockState state=genWorld.getMainState();
				countGenerator(targetDimId,chunkX,chunkZ,state,listGeneration);
			}
			countChunk();
		}
		else // 不启用生成日志
		{
			for(IChunkGen genWorld:generators) // 检查能不能生成在指定世界
			{
				boolean canGen=genWorld.canGenAtDim(targetDimId,world,provider) &&
						genWorld.canGenAtBiome(targetBiomeName, world, biome);
				if(!canGen) continue;

				genWorld.genAtChunk(world, chunkVX, chunkVZ, rand);
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		gen(world,chunkX,chunkZ,random);
	}

	private static List<GenerationLog> cacheLog = new ArrayList<>(500);
	public static void countGenerator(int dim,int chunkX,int chunkZ,IBlockState state, List<BlockPos> listGeneration)
	{
		try
		{
			GenerationLog log=new GenerationLog();
			log.dim=dim;
			log.state=state;
			log.size=listGeneration.size();
			log.pos=log.size>0?listGeneration.get(0):null;
			log.chunkX=chunkX;
			log.chunkZ=chunkZ;
			cacheLog.add(log);
		}
		catch (Exception ignored) { }
	}
	private static int tempChunk = 0;
	public static void countChunk()
	{
		tempChunk++;
		if(tempChunk>=Configs.General.log_chunk_generation_cache)
		{
			flush();
			tempChunk=0;
		}
	}
	private static void flush()
	{
		TRY:try
		{
			if(os==null) break TRY;

			int total=0;

			for(GenerationLog gl : cacheLog)
			{
				if(gl.size<=0) continue;
				StringBuilder str=
				new StringBuilder("generated {").append(gl.state.getBlock().getLocalizedName()).append("} × ").append(gl.size).append(" at [")
						.append(gl.pos.getX()).append(",").append(gl.pos.getY()).append(",").append(gl.pos.getZ())
						.append("](").append(gl.chunkX).append(",").append(gl.chunkZ)
						.append(") in dim ").append(gl.dim);

				total+=gl.size;

				os.println(str.toString());
			}

			Map<IBlockState,Integer> mapStateCount=new HashMap<>();
			for(GenerationLog gl : cacheLog)
			{
				if(gl.size<=0) continue;
				mapStateCount.compute(gl.state, (key,old)->(old!=null?old:0) + gl.size);
			}
			os.print("======== ");
			os.print(total);
			os.print(" in ");
			os.print(Configs.General.log_chunk_generation_cache);
			os.println(" chunk(s) ========");
			for(Map.Entry<IBlockState,Integer> entry:mapStateCount.entrySet())
			{
				IBlockState state=entry.getKey();
				int count=entry.getValue();
				os.print(state.getBlock().getLocalizedName());
				os.print(" × ");
				os.println(count);
			}

			os.flush();
		}
		catch (Exception ignored) { }
		cacheLog.clear();
	}

	private static class GenerationLog
	{
		int dim;
		int chunkX;
		int chunkZ;
		IBlockState state;
		int size;
		BlockPos pos;
	}

	/**
	 * 世界生成输出流
	 */
	private static PrintStream os;
	public static void setOutStream(OutputStream os)
	{
		cacheLog.clear();
		if(os==null) return;
		WorldGens.os=new PrintStream(os);
	}
	public static void closeOutStream()
	{
		try
		{
			cacheLog.clear();
			os.flush();
			os.close();
		}
		catch (Exception ignored) {}
	}
}
