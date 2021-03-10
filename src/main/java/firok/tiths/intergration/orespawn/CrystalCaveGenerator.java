package firok.tiths.intergration.orespawn;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.FeatureBase;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.os3.ISpawnEntry;
import com.mcmoddev.orespawn.data.Constants;
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
 * @see com.mcmoddev.orespawn.impl.features.DefaultFeatureGenerator
 */
public class CrystalCaveGenerator extends FeatureBase implements IFeature
{
	public CrystalCaveGenerator()
	{
		super(new Random());
	}
	public CrystalCaveGenerator(Random rand)
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

		final int blockX = chunkX * 16 + 8;
		final int blockZ = chunkZ * 16 + 8;

		final int minY = params.get(Constants.FormatBits.MIN_HEIGHT).getAsInt();
		final int maxY = params.get(Constants.FormatBits.MAX_HEIGHT).getAsInt();
		final int vari = params.get(Constants.FormatBits.VARIATION).getAsInt();
		final float freq = params.get(Constants.FormatBits.FREQUENCY).getAsFloat();
		final int size = params.get(Constants.FormatBits.NODE_SIZE).getAsInt();

		if(!canTrigger(world.rand,freq)) return;

		IBlockState stateDiamond = Blocks.DIAMOND_BLOCK.getDefaultState();
		BlockPos posTop = world.getTopSolidOrLiquidBlock(new BlockPos(blockX,0,blockZ));
		world.setBlockState(posTop,stateDiamond);
	}

	@Override
	public JsonObject getDefaultParameters() {
		final JsonObject defParams = new JsonObject();
		defParams.addProperty(Constants.FormatBits.MIN_HEIGHT, 0);
		defParams.addProperty(Constants.FormatBits.MAX_HEIGHT, 256);
		defParams.addProperty(Constants.FormatBits.VARIATION, 16);
		defParams.addProperty(Constants.FormatBits.FREQUENCY, 0.5);
		defParams.addProperty(Constants.FormatBits.NODE_SIZE, 8);
		return defParams;
	}

	@Override
	public void setRandom(final Random rand) {
		this.random = rand;
	}
}
