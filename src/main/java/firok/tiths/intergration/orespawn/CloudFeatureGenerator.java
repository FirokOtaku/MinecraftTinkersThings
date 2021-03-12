package firok.tiths.intergration.orespawn;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.FeatureBase;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.os3.ISpawnEntry;
import com.mcmoddev.orespawn.data.Constants;
import firok.tiths.util.InnerActions;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

import static firok.tiths.util.InnerActions.between;
import static firok.tiths.util.Predicates.canTrigger;

/**
 * 云层生成器
 * @since 0.3.29.0 第四次世界生成模块修改
 */
public class CloudFeatureGenerator extends FeatureBase implements IFeature
{
	public CloudFeatureGenerator()
	{
		super(new Random());
	}
	public CloudFeatureGenerator(Random rand)
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

		final float freq = params.get(Constants.FormatBits.FREQUENCY).getAsFloat();

		if(!canTrigger(world.rand,freq)) return;

		final int minY = InnerActions.range(params.get(Constants.FormatBits.MIN_HEIGHT).getAsInt(),0,256);
		final int maxY = InnerActions.range(params.get(Constants.FormatBits.MAX_HEIGHT).getAsInt(),0,256);

		final int radiusX = InnerActions.range(params.get("radiusX").getAsInt(),5,20);
		final int variationX = InnerActions.range(params.get("variationX").getAsInt(),0,5);
		final int radiusY = InnerActions.range(params.get("radiusY").getAsInt(),5,20);
		final int variationY = InnerActions.range(params.get("variationY").getAsInt(),0,5);
		final int radiusZ = InnerActions.range(params.get("radiusZ").getAsInt(),5,20);
		final int variationZ = InnerActions.range(params.get("variationZ").getAsInt(),0,5);

		// now to ore spawn
		final Random rand = world.rand;
		final int posX=pos.getXStart()+2+rand.nextInt(12);
		final int posY=minY + (maxY>minY? rand.nextInt(maxY-minY): 0);
		final int posZ=pos.getZStart()+2+rand.nextInt(12);

		final int rx=between(rand,radiusX-variationX,radiusX+variationX),
				ry=between(rand,radiusY-variationY,radiusY+variationY),
				rz=between(rand,radiusZ,variationZ);
		final float a2=rx*rx,b2=ry*ry,c2=rz*rz;

		FOR_X: for(int ox=-rx; ox<=rx; ox++)
		{
			int ox2=ox*ox;
			FOR_Z: for(int oz=-rz; oz<=rz; oz++)
			{
				int oz2=oz*oz;
				FOR_Y: for(int oy=-ry; oy<=ry; oy++)
				{
					int oy2=oy*oy;
					final boolean inside=ox2/a2+oy2/b2+oz2/c2<1;

					if(inside)
					{
						BlockPos posTemp=new BlockPos(posX+ox,posY+oy,posZ+oz);
						if(world.isAirBlock(posTemp))
						{
							IBlockState stateTemp = spawnData.getBlocks().getRandomBlock(rand);
							world.setBlockState(posTemp,stateTemp);
						}
					}
				}
			}
		}
	}

	@Override
	public JsonObject getDefaultParameters() {
		final JsonObject defParams = new JsonObject();
		defParams.addProperty(Constants.FormatBits.MIN_HEIGHT, 160);
		defParams.addProperty(Constants.FormatBits.MAX_HEIGHT, 180);
		defParams.addProperty(Constants.FormatBits.FREQUENCY, 0.0185);
		defParams.addProperty("radiusX",6);
		defParams.addProperty("variationX",2);
		defParams.addProperty("radiusY",4);
		defParams.addProperty("variationY",2);
		defParams.addProperty("radiusZ",6);
		defParams.addProperty("variationZ",2);
		return defParams;
	}

	@Override
	public void setRandom(final Random rand) {
		this.random = rand;
	}
}
