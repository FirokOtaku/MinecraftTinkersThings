package firok.tiths.intergration.orespawn;

import com.google.gson.JsonObject;
import com.mcmoddev.orespawn.api.FeatureBase;
import com.mcmoddev.orespawn.api.IFeature;
import com.mcmoddev.orespawn.api.os3.ISpawnEntry;
import com.mcmoddev.orespawn.data.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;
import static firok.tiths.util.Predicates.isWater;

/**
 * 海床生成器
 * @since 0.3.30.0 第四次世界生成模块修改
 */
public class SeabedFeatureGenerator extends FeatureBase implements IFeature
{
	public SeabedFeatureGenerator()
	{
		this(new Random());
	}
	public SeabedFeatureGenerator(Random rand)
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

		final int posX = pos.getXStart() + 5 + random.nextInt(6);
		final int posY = 0;
		final int posZ = pos.getZStart() + 5 + random.nextInt(6);
		BlockPos posCenter = new BlockPos( posX, posY, posZ );

		final BlockPos posTopBlock=world.getTopSolidOrLiquidBlock(posCenter);

		int startY=-1;
		IBlockState stateTempCheckTop= world.getBlockState(posTopBlock);
		if(!isWater(stateTempCheckTop)) return; // 如果顶部直接就不是水 那直接返回
		// 顶部是水 向下搜寻不是水的方块
		int tempCheckY;
		for(tempCheckY=posTopBlock.getY()-5;tempCheckY>0;tempCheckY-=5)
		{
			IBlockState stateTempCheck= world.getBlockState(new BlockPos(posX,tempCheckY,posZ));
			if( !isWater(stateTempCheck)) break;
			// 只要break了说明现在的tempCheckY已经不是水了
		}
		// 向上寻找是水的方块
		for(int tempCheckOffsetY=1;tempCheckOffsetY<=5;tempCheckOffsetY++)
		{
			IBlockState stateTempCheck= world.getBlockState(new BlockPos(posX,tempCheckY+tempCheckOffsetY,posZ));
			if(isWater(stateTempCheck))
			{
				startY=tempCheckOffsetY+tempCheckY;
				break;
			}
		}

		if(startY<=0) return;

		IBlockState stateOre=spawn.getBlocks().getRandomBlock(random);

		BlockPos posTryGen=new BlockPos(posX,startY,posZ);
		if(stateOre.getBlock().canPlaceBlockAt(world,posTryGen))
		{
			world.setBlockState(posTryGen,stateOre);
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
		return defParams;
	}
}
