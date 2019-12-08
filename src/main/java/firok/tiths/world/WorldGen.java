package firok.tiths.world;

import firok.tiths.TinkersThings;
import firok.tiths.common.Blocks;
import firok.tiths.util.Predicates;
import firok.tiths.util.RegOre;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGen implements IWorldGenerator
{
	private static WorldGen instance;
	public static WorldGen getInstance() {
		if (instance == null)
			instance = new WorldGen();
		return instance;
	}

	private List<WorldGenMinableRandom> genWorld=new ArrayList<>();
	private List<WorldGenMinableRandom> genNether=new ArrayList<>();
	private List<WorldGenMinableRandom> genEnd=new ArrayList<>();
	private List<WorldGenMinableRandom> genOther=new ArrayList<>();

	public WorldGen()
	{
		Field[] fields=Blocks.class.getDeclaredFields();
		for(Field field:fields)
		{
			try
			{
				RegOre regOre=field.getAnnotation(RegOre.class);
				if(regOre==null) continue;

				IBlockState state=((Block)field.get(null)).getDefaultState();

				if(regOre.minWorldAmount()>0) genWorld.add(new WorldGenMinableRandom(state,regOre.minWorldAmount(),regOre.maxWorldAmount(),regOre.minWorldY(),regOre.maxWorldY(), Predicates::canOreGenWorld));
				if(regOre.minEndAmount()>0) genEnd.add(new WorldGenMinableRandom(state,regOre.minEndAmount(),regOre.maxEndAmount(),regOre.minEndY(),regOre.maxEndY(), Predicates::canOreGenEnd));
				if(regOre.minNetherAmount()>0) genNether.add(new WorldGenMinableRandom(state,regOre.minNetherAmount(),regOre.maxNetherAmount(),regOre.minNetherY(),regOre.maxNetherY(), Predicates::canOreGenNether));
				if(regOre.minOtherAmount()>0) genOther.add(new WorldGenMinableRandom(state,regOre.minOtherAmount(),regOre.maxOtherAmount(),regOre.minOtherY(),regOre.maxOtherY(), Predicates::canOreGenOther));
			}
			catch (Exception e)
			{
				TinkersThings.log("register world gen error:"+field);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
	                     IChunkProvider chunkProvider) {
		int x = chunkX * 16;
		int z = chunkZ * 16;
		switch (world.provider.getDimension()) {
			case -1:
//				nether(random, x, z, world);
				for (WorldGenMinableRandom gen : genNether) gen.generate(world, random, x, z);
				break;
			case 0:
//				world(random, x, z, world);
				for (WorldGenMinableRandom gen : genWorld) gen.generate(world, random, x, z);
				break;
			case 1:
//				end(random, x, z, world);
				for (WorldGenMinableRandom gen : genEnd) gen.generate(world, random, x, z);
				break;
			default:
				for (WorldGenMinableRandom gen : genOther) gen.generate(world, random, x, z);
//				if (!blackList.contains(world.provider.getDimension()))
//					other(random, x, z, world);
				break;
		}
	}

}
