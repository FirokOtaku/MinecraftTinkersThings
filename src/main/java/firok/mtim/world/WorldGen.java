package firok.mtim.world;

import firok.mtim.util.StateMatcher;
import net.minecraft.block.BlockStone;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.google.common.collect.Lists.newArrayList;

public class WorldGen implements IWorldGenerator
{
	private static WorldGen INSTANCE;
	private final List<Integer> blackList = new ArrayList();

	public static WorldGen getInstance() {
		if (INSTANCE == null)
			INSTANCE = new WorldGen();

		return INSTANCE;
	}

	private void nether(Random random, int x, int z, World world) {
//		generateOre(tiberiumOre.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), random, x, z,
//				world, TIBERIUM_VAL, 32, 128, 10, 35);
//		generateOre(prometheumOre.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), random, x, z,
//				world, PROMETHEUM_VAL, 0, 32, 2, 4);
//		generateOre(valyriumOre.getDefaultState(), Blocks.NETHERRACK.getDefaultState(), random, x, z,
//				world, VALYRIUM_VAL, 0, 128, 2, 4);
//		generateOre(newArrayList(Blocks.LAVA.getDefaultState(), Blocks.FLOWING_LAVA.getDefaultState()),
//				osramOre.getDefaultState(), random, x, z, world, OSRAM_VAL, 0, 64, 15);
	}

	private void other(Random random, int x, int z, World world) {
//		int dim = world.provider.getDimension();
//		if (!meteorGenStats.containsKey(dim))
//			meteorGenStats.put(dim, 0);
//
//		if (!meteorChunkStats.containsKey(dim))
//			meteorChunkStats.put(dim, 0);
//
//		meteorChunkStats.put(dim, meteorChunkStats.get(dim) + 1);
//		meteorGenStats.put(meteorGenStats.get(dim), meteorGenStats.get(dim) + generateMeteor(duraniteOre
//				.getDefaultState(), blockMeteorite.getDefaultState(), random, x, z, world, DURANITE_VAL, 6, 16, 112));
//		generateOreDescending(newArrayList(Blocks.LAVA.getDefaultState(), Blocks.FLOWING_LAVA
//				.getDefaultState()), basaltBlock.getDefaultState(), random, x, z, world, BASALT_VAL, 0, 64);
//		generateOreDescending(newArrayList(Blocks.BEDROCK.getDefaultState()), eezoOre.getDefaultState(),
//				random, x, z, world, EEZO_VAL, 0, 10);
//		generateOreStoneVariant(karmesineOre.getDefaultState(), BlockStone.EnumType.ANDESITE, random, x, z,
//				world, KARMESINE_VAL);
//		generateOreStoneVariant(oviumOre.getDefaultState(), BlockStone.EnumType.DIORITE, random, x, z,
//				world, OVIUM_VAL);
//		generateOreStoneVariant(jauxumOre.getDefaultState(), BlockStone.EnumType.GRANITE, random, x, z,
//				world, JAUXUM_VAL);
//		generateOre(vibraniumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world,
//				VIBRANIUM_VAL, 100, 0, 64, 2, 6, newArrayList(Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes
//						.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.DESERT));
//		generateOre(dilithiumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world,
//				DILITHIUM_VAL, 100, 0, 64, 2, 8, newArrayList(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes
//						.MUTATED_DESERT, Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN, Biomes.BEACH));
//		generateOre(vibraniumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world, 1,
//				15, 0, 128, 1, 5, null);
//		if (ironGen) {
//			generateOre(Blocks.IRON_ORE.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z,
//					world, IRON_VAL, 0, 32, 2, 8);
//		}
//
//		if (meteorChunkStats.get(dim) > 100 && meteorGenStats.get(dim) == 0) {
//			blackList.add(dim);
//			TAIGA.logger.info(String.format("Detected void dimension, adding to blacklist: %d", dim));
//		}
	}

	private void world(Random random, int x, int z, World world) {
//		generateMeteor(duraniteOre.getDefaultState(), blockMeteorite.getDefaultState(), random, x, z,
//				world, DURANITE_VAL, 6, 16, 112);
//		generateOreDescending(newArrayList(Blocks.LAVA.getDefaultState(), Blocks.FLOWING_LAVA
//				.getDefaultState()), basaltBlock.getDefaultState(), random, x, z, world, BASALT_VAL, 0, 64);
//		generateOreDescending(newArrayList(Blocks.BEDROCK.getDefaultState()), eezoOre.getDefaultState(),
//				random, x, z, world, EEZO_VAL, 0, 10);
//		generateOreStoneVariant(karmesineOre.getDefaultState(), BlockStone.EnumType.ANDESITE, random, x, z,
//				world, KARMESINE_VAL);
//		generateOreStoneVariant(oviumOre.getDefaultState(), BlockStone.EnumType.DIORITE, random, x, z,
//				world, OVIUM_VAL);
//		generateOreStoneVariant(jauxumOre.getDefaultState(), BlockStone.EnumType.GRANITE, random, x, z,
//				world, JAUXUM_VAL);
//		generateOre(vibraniumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world,
//				VIBRANIUM_VAL, 100, 0, 64, 2, 6, newArrayList(Biomes.DESERT_HILLS, Biomes.EXTREME_HILLS, Biomes
//						.EXTREME_HILLS_EDGE, Biomes.EXTREME_HILLS_WITH_TREES, Biomes.DESERT));
//		generateOre(dilithiumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world,
//				DILITHIUM_VAL, 100, 0, 64, 2, 8, newArrayList(Biomes.DESERT, Biomes.DESERT_HILLS, Biomes
//						.MUTATED_DESERT, Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.FROZEN_OCEAN, Biomes.BEACH));
//		generateOre(vibraniumOre.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z, world, 1,
//				15, 0, 128, 1, 5, null);
//		if (ironGen) {
//			generateOre(Blocks.IRON_ORE.getDefaultState(), Blocks.STONE.getDefaultState(), random, x, z,
//					world, IRON_VAL, 0, 32, 2, 8);
//		}
	}

	private void end(Random random, int x, int z, World world) {
//		generateCube(true, uruOre.getDefaultState(), blockObsidiorite.getDefaultState(), random, x, z,
//				world, URU_VAL, 2, 0, 96, 3);
//		if (endGen)
//			generateOre(Blocks.AIR.getDefaultState(), Blocks.END_STONE.getDefaultState(), null, null,
//					random, x, z, world, 1, 100, 3, 64, 3, 8, null);
//		generateOre(auroriumOre.getDefaultState(), Blocks.END_STONE.getDefaultState(), random, x, z, world,
//				AURORIUM_VAL, 32, 48, 2, 4);
//		generateOre(palladiumOre.getDefaultState(), Blocks.END_STONE.getDefaultState(), random, x, z,
//				world, PALLADIUM_VAL, 48, 64, 2, 4);
//		generateOreBottom(Blocks.END_STONE.getDefaultState(), abyssumOre.getDefaultState(), random, x, z,
//				world, ABYSSUM_VAL, 4, 64);
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
	                     IChunkProvider chunkProvider) {
		int x = chunkX * 16;
		int z = chunkZ * 16;
		switch (world.provider.getDimension()) {
			case -1:
				nether(random, x, z, world);
				break;
			case 0:
				world(random, x, z, world);
				break;
			case 1:
				end(random, x, z, world);
				break;
			default:
				if (!blackList.contains(world.provider.getDimension()))
					other(random, x, z, world);
				break;
		}
	}


	public static void generateOre(IBlockState newState, IBlockState oldState, Random random, int chunkX, int chunkZ,
	                               World world, int count, int minY, int maxY, int minSize, int maxSize) {
		generateOre(newState, oldState, null, null, random, chunkX, chunkZ, world, count, 100, minY, maxY, minSize,
				maxSize, null);
	}

	public static void generateOre(IBlockState newState, IBlockState oldState, Random random, int chunkX, int chunkZ,
	                               World world, int count, int chance, int minY, int maxY, int minSize, int maxSize,
	                               List<Biome> biome) {
		generateOre(newState, oldState, null, null, random, chunkX, chunkZ, world, count, chance, minY, maxY,
				minSize, maxSize, biome);
	}

	public static void generateOre(IBlockState newState, IBlockState oldState, IProperty property, Comparable
			comparable, Random random, int chunkX, int chunkZ, World world, int count, int chance, int minY, int
			                               maxY, int minSize, int maxSize, List<Biome> biome) {
		int size = minSize + random.nextInt(maxSize - minSize);
		int height = maxY - minY;
		for (int i = 0; i < count; i++) {
			if (0.01f * chance >= random.nextFloat()) {
				int posX = chunkX + random.nextInt(16);
				int posY = random.nextInt(height) + minY;
				int posZ = chunkZ + random.nextInt(16);
				BlockPos cPos = new BlockPos(posX, posY, posZ);
				if (biome == null || biome.contains(world.getBiome(cPos))) {
					new WorldGenMinable(newState, size, StateMatcher.forState(oldState, property, comparable))
							.generate(world, random, new BlockPos(posX, posY, posZ));
				}
			}
		}
	}

	public static void generateOre(List<IBlockState> replaceBlockList, IBlockState replacementBlock, Random random,
	                               int chunkX, int chunkZ, World world, int count, int minY, int maxY, int chance) {
		if (random.nextFloat() < (float) (0.01 * chance))
			generateOreDescending(replaceBlockList, replacementBlock, random, chunkX, chunkZ, world, count, minY, maxY);
	}

	public static void generateOreDescending(List<IBlockState> replaceBlockList, IBlockState replacementBlock, Random
			random, int chunkX, int chunkZ, World world, int count, int minY, int maxY) {
		for (int i = 0; i < count; i++) {
			int posX = chunkX + random.nextInt(16);
			int posZ = chunkZ + random.nextInt(16);
			BlockPos cPos = new BlockPos(posX, maxY, posZ);
			if (replaceBlockList.contains(world.getBlockState(cPos)) && replaceBlockList.contains(world.getBlockState
					(cPos.up()))) {
				continue;
			}
			if (replaceBlockList.contains(world.getBlockState(cPos)) && !replaceBlockList.contains(world
					.getBlockState(cPos.up())))
				world.setBlockState(cPos, replacementBlock);
			while (!replaceBlockList.contains(world.getBlockState(cPos.down())) && cPos.getY() > minY) {
				cPos = cPos.down();
			}
			if (replaceBlockList.contains(world.getBlockState(cPos.down())))
				world.setBlockState(cPos.down(), replacementBlock);
		}
	}

	public static void generateOreStoneVariant(IBlockState newState, BlockStone.EnumType type, Random random, int
			chunkX, int chunkZ, World world, int count) {
		List<BlockStone.EnumType> list = newArrayList(type);
		for (int i = 0; i < count; i += 2) {
			int posX = chunkX + random.nextInt(16);
			int posZ = chunkZ + random.nextInt(16);
			BlockPos cPos = new BlockPos(posX, random.nextInt(64) + 32, posZ);
			IBlockState state = world.getBlockState(cPos);
			if (state.getBlock().equals(Blocks.STONE.getDefaultState().getBlock())) {
				if (list.contains(state.getValue(BlockStone.VARIANT))) {
					world.setBlockState(cPos, newState);
				}
			} else {
				while (cPos.getY() >= 0) {
					cPos = cPos.down();
					state = world.getBlockState(cPos);
					if (state.getBlock().equals(Blocks.STONE.getDefaultState().getBlock())) {
						if (list.contains(state.getValue(BlockStone.VARIANT))) {
							world.setBlockState(cPos, newState);
							break;
						}
					}
				}
			}
			cPos = new BlockPos(posX, random.nextInt(32), posZ);
			state = world.getBlockState(cPos);
			if (state.getBlock().equals(Blocks.STONE.getDefaultState().getBlock())) {
				if (list.contains(state.getValue(BlockStone.VARIANT))) {
					world.setBlockState(cPos, newState);
				}
			} else {
				while (cPos.getY() <= 96) {
					cPos = cPos.up();
					state = world.getBlockState(cPos);
					if (state.getBlock().equals(Blocks.STONE.getDefaultState().getBlock())) {
						if (list.contains(state.getValue(BlockStone.VARIANT))) {
							world.setBlockState(cPos, newState);
							break;
						}
					}
				}
			}
		}
	}

	public static void generateOreBottom(IBlockState oldState, IBlockState newState, Random random, int chunkX, int
			chunkZ, World world, int chance, int spread, int maxY) {
		for (int i = 0; i < chance; i++) {
			int posX = chunkX + random.nextInt(16);
			int posY = 0;
			int posZ = chunkZ + random.nextInt(16);
			BlockPos cPos = new BlockPos(posX, posY, posZ);
			if (Blocks.AIR.getDefaultState().equals(world.getBlockState(cPos))) {
				while (world.getBlockState(cPos).equals(Blocks.AIR.getDefaultState()) && cPos.getY() < maxY) {
					cPos = cPos.up();
				}
				if (world.getBlockState(cPos).equals(oldState)) {
					world.setBlockState(cPos.up(random.nextInt(spread)), newState);
				}
			}
		}
	}

	public static void generateCube(boolean fly, IBlockState centerBlock, IBlockState hullBlock, Random random, int
			chunkX, int chunkZ, World world, int count, int chance, int minY, int maxY, int maxS) {
		for (int i = 0; i < count; i++) {
			if (random.nextFloat() < 0.01 * chance) {
				int outer = MathHelper.getInt(random, 1, maxS);
				int inner = random.nextInt(2);
				int posX = chunkX + random.nextInt(16);
				int posY = MathHelper.getInt(random, minY, maxY);
				int posZ = chunkZ + random.nextInt(16);
				BlockPos cPos = new BlockPos(posX, posY, posZ);
				if (!fly) {
					if (world.getBlockState(cPos).equals(Blocks.AIR.getDefaultState()) && world.getBlockState(cPos
							.down()).equals(Blocks.AIR.getDefaultState())) {
						// we are in mid air, go down
						while (world.getBlockState(cPos.down()).equals(Blocks.AIR.getDefaultState())) {
							cPos = cPos.down();
						}
					}
				}
				cPos.down((random.nextInt(4) + 2) * outer);
				for (int x = -inner; x <= inner; x++) {
					for (int y = -inner; y <= inner; y++) {
						for (int z = -inner; z <= inner; z++) {
							if (!world.getBlockState(cPos).equals(Blocks.AIR.getDefaultState()))
								continue;
							world.setBlockState(new BlockPos(cPos.getX() + x, cPos.getY() + y, cPos.getZ() + z),
									centerBlock);
						}
					}
				}
				for (int x = -outer; x <= outer; x++) {
					for (int y = -outer; y <= outer; y++) {
						for (int z = -outer; z <= outer; z++) {
							BlockPos nPos = new BlockPos(cPos.getX() + x, cPos.getY() + y, cPos.getZ() + z);
							if (world.getBlockState(nPos).equals(centerBlock) || !world.getBlockState(nPos).equals
									(Blocks.AIR.getDefaultState()))
								continue;
							world.setBlockState(nPos, hullBlock);
						}
					}
				}

			}
		}
	}

}
