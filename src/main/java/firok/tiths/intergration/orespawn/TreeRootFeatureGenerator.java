package firok.tiths.intergration.orespawn;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.FeatureBase;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.os3.ISpawnEntry;
import com.mcmoddev.orespawn.api.os3.OreSpawnBlockMatcher;
import com.mcmoddev.orespawn.data.Constants;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/**
 * 树根生成器
 * @since 0.3.30.0 第四次世界生成模块修改
 */
public class TreeRootFeatureGenerator extends FeatureBase implements IFeature
{
	public TreeRootFeatureGenerator()
	{
		this(new Random());
	}
	public TreeRootFeatureGenerator(Random rand)
	{
		super(rand);
	}

	@Override
	public void generate(World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider, ISpawnEntry spawn, ChunkPos pos)
	{
		final JsonObject params = spawn.getFeature().getFeatureParameters();

		// First, load cached blocks for neighboring chunk ore spawns
		final int chunkX = pos.x;
		final int chunkZ = pos.z;

		mergeDefaults(params, getDefaultParameters());

		runCache(chunkX, chunkZ, world, spawn);

		final float freq = params.get(Constants.FormatBits.FREQUENCY).getAsFloat();

		if(!canTrigger(world.rand,freq)) return;

		int depthBase = InnerActions.range(params.get("depthBase").getAsInt(),0,5);
		int depthExtra = InnerActions.range(params.get("depthExtra").getAsInt(),0,5);

		final int posX = pos.getXStart() + 5 + random.nextInt(6);
		final int posY = 0;
		final int posZ = pos.getZStart() + 5 + random.nextInt(6);
		BlockPos posCenter = new BlockPos( posX, posY, posZ );
		BlockPos posCenterTop=world.getTopSolidOrLiquidBlock(new BlockPos(posX,0,posZ));

		Biome.TempCategory tempCate=world.getBiome(posCenterTop).getTempCategory();
		if(tempCate== Biome.TempCategory.COLD || tempCate== Biome.TempCategory.OCEAN || !spawn.biomeAllowed(world.getBiome(posCenter))) return; // 不在寒冷和海洋生物群系生成

		final OreSpawnBlockMatcher matcher = spawn.getMatcher();


		for(int ox=-2;ox<=2;ox++)
		{
			for(int oz=-2;oz<=2;oz++)
			{
				int tryPosX = posCenterTop.getX()+ox;
				int tryPosY = posCenterTop.getY();
				int tryPosZ = posCenterTop.getZ()+oz;

				int times=0;

				boolean canGen=false; // 找到第一块木头之后才开始生成

				final int depth=depthBase+(depthExtra > 0 ? random.nextInt(depthExtra) : 0);

				while(times<depth && tryPosY>1)
				{
					final BlockPos pos2get=new BlockPos(tryPosX,tryPosY,tryPosZ);
					final IBlockState state = world.getBlockState(pos2get);
					final Block block = state.getBlock();

					if(!matcher.test(state)) return; // 不再继续生成

					boolean isWood=block.isWood(world,pos2get);
					if(isWood)
					{
						canGen=true; // 找到第一块木头
					}
					else
					{
						if(canGen && (block== Blocks.DIRT || block==Blocks.STONE || block==Blocks.COBBLESTONE))
						{
							IBlockState stateOre = spawn.getBlocks().getRandomBlock(random);
							world.setBlockState(pos2get,stateOre);
						}
					}

					tryPosY--;
					times++;
				}
			}
		}

	}

	@Override
	public void setRandom(Random rand)
	{
		this.random = rand;
	}

	@Override
	public JsonObject getDefaultParameters()
	{
		final JsonObject defParams = new JsonObject();
		defParams.addProperty(Constants.FormatBits.FREQUENCY, 0.08);
		defParams.addProperty("depthBase",3);
		defParams.addProperty("depthExtra",3);
		return defParams;
	}
}
