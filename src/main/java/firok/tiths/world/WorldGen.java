package firok.tiths.world;

import firok.tiths.TinkersThings;
import firok.tiths.common.Blocks;
import firok.tiths.util.GenMeteoWorld;
import firok.tiths.util.GenOreWorld;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.utils.HarvestLevels;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGen implements IWorldGenerator
{
	private static WorldGen instance;
	public static WorldGen getInstance()
	{
		if(instance==null) new WorldGen();
		return instance;
	}

	private static List<IChunkGen> gensWorld=new ArrayList<>();
	public WorldGen()
	{
		MinecraftForge.ORE_GEN_BUS.register(this);
		// 开始读取Blocks里面的注解
		for(Field field: Blocks.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				if(!(obj instanceof Block)) continue;

				Block block=(Block) obj;
				GenOreWorld genOreWorld=field.getAnnotation(GenOreWorld.class);
				GenMeteoWorld genMeteoWorld= field.getAnnotation(GenMeteoWorld.class);

				if(genOreWorld!=null) // 主世界矿物
				{
					WorldGenMinableCustom gen=createOreGenWorld(block.getDefaultState(),genOreWorld);
//					TinkersThings.log("registered ore:"+block.getRegistryName());
					gensWorld.add(gen);
				}
				if(genMeteoWorld!=null) // 主世界陨石
				{
					WorldGenMeteorolite gen=createMeteoGenWorld(block.getDefaultState(),genMeteoWorld);
//					TinkersThings.log("registered meteo:"+block.getRegistryName());
					gensWorld.add(gen);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		// 陨石

		instance=this;
	}

	public WorldGenMinableCustom createOreGenWorld(IBlockState state, GenOreWorld gen)
	{
		return new WorldGenMinableCustom(
				state,
				gen.times(),
				gen.timeRate(),
				gen.size(),
				gen.minY(),
				gen.maxY(),
				Predicates::canOreGenWorld
				);
	}
	public WorldGenMeteorolite createMeteoGenWorld(IBlockState state, GenMeteoWorld gen)
	{
		return new WorldGenMeteorolite(
				state,
				gen.rateOre(),
				gen.rateChunk()
		);
	}

	@SubscribeEvent
	public void onOreGenPre(OreGenEvent.Pre event){
		BlockPos posEvent=event.getPos();

//		System.out.println(String.format("on ore gen pre {%s}",posEvent));
		gen(event.getWorld(),posEvent.getX(),posEvent.getZ(),event.getRand());
	}

	int preChunkX=Integer.MIN_VALUE, preChunkZ =Integer.MIN_VALUE;
	World preWorld=null;
	public void gen(World world,int chunkX,int chunkZ,Random rand) // note event传进来的是区块顶点坐标
	{
		if(preWorld==world&&preChunkX==chunkX&&preChunkZ==chunkZ)
		{
			return;
		}

		preWorld=world;
		preChunkX=chunkX;
		preChunkZ=chunkZ;

//		System.out.println(String.format("gen chunkX,chunkZ{%d,%d}",chunkX,chunkZ));
		int dim=world.provider.getDimension();
		SWITCH_GEN_AT_WORLD:switch(dim) // 维度id 主世界0 下界-1 末地1
		{
			case -1: // 下界
			case 0: // 主世界
			{
				for(IChunkGen genWorld:gensWorld)
				{
					genWorld.gen(world,chunkX,chunkZ,rand);
				}
				break SWITCH_GEN_AT_WORLD;
			}
			case 1: // 末地
			default: // mod世界
		}
	}

//	@SubscribeEvent
//	public void onOreGenGenerateMinable(OreGenEvent.GenerateMinable event){
//		;
//	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		gen(world,chunkX,chunkZ,random);
	}
}
