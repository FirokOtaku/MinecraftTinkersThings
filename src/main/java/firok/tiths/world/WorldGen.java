package firok.tiths.world;

import firok.tiths.common.Blocks;
import firok.tiths.common.ConfigJson;
import firok.tiths.util.conf.OreGenInfo;
import firok.tiths.util.reg.GenMeteo;
import firok.tiths.util.reg.GenOre;
import firok.tiths.util.Predicates;
import firok.tiths.util.reg.Reg;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.TinkersThings.log;
import static firok.tiths.util.conf.Values.__;
import static firok.tiths.util.conf.Values.arr;

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

		gensWorld.add(new WorldGenTreeRoot(Blocks.oreTreeRoot.getDefaultState(),4,0.3f));
		gensWorld.add(new WorldGenMinableBedrock(Blocks.oreBrokenBedrock.getDefaultState(),1,0.25f,0.8f,7));
		gensWorld.add(new WorldGenLavaCrystal());

		instance=this;
	}

	/**
	 * 读取Blocks类底下的东西
	 */
	private static void loadBlockOres()
	{
		gensWorld.clear(); // 先清空一下

		// 开始读取Blocks里面的注解
		for(Field field: Blocks.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				Reg reg=field.getAnnotation(Reg.class);

				if(reg==null || !(obj instanceof Block)) continue;

				Block block=(Block) obj;

				OreGenInfo info= ConfigJson.getOre(reg.value());
				boolean i=__(info);
				GenOre genOre =field.getAnnotation(GenOre.class);
				GenMeteo genMeteo = field.getAnnotation(GenMeteo.class);

				// 1 是否提供注解 2 是否提供完整自定义信息
				boolean b11=genOre!=null,b12=i&&info.checkOreInfoComplete();
				boolean b21=genMeteo!=null,b22=i&&info.checkMeteoInfoComplete();

				if(b11||b12) // 矿物
				{
					WorldGenMinableCustom gen=new WorldGenMinableCustom(
							block.getDefaultState(),
							i&&__(info.times)? info.times:genOre.times(),
							i&&__(info.timeRate)? info.timeRate:genOre.timeRate(),
							i&&__(info.size)? info.size:genOre.size(),
							i&&__(info.minY)? info.minY:genOre.minY(),
							i&&__(info.maxY)? info.maxY:genOre.maxY(),
							i&&__(info.dims)? arr(info.dims):genOre.dimsBanned(),
							Predicates::isStone
					);
//					TinkersThings.log("registered ore:"+block.getRegistryName());
					gensWorld.add(gen);
				}
				if(b21||b22) // 陨石
				{
					WorldGenMeteorolite gen=new WorldGenMeteorolite(
							block.getDefaultState(),
							i&&__(info.meteoRateOre)? info.meteoRateOre:genMeteo.rateOre(),
							i&&__(info.meteoRateChunk)? info.meteoRateChunk:genMeteo.rateChunk(),
							i&&__(info.meteoDims)? arr(info.meteoDims):genMeteo.dimsBanned()
					);
//					TinkersThings.log("registered meteo:"+block.getRegistryName());
					gensWorld.add(gen);
				}
			}
			catch (Exception e)
			{
				log("error when creating world generator");
				log(e);
			}
		}
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

		for(IChunkGen genWorld:gensWorld) // 检查能不能生成在指定世界
		{
			if(genWorld.canGenAtDim(dim)) genWorld.gen(world,chunkX,chunkZ,rand);
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
