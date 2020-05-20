package firok.tiths.world;


import firok.tiths.common.ConfigJson;
import firok.tiths.util.Predicates;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

public abstract class BaseChunkGen implements IChunkGen
{
	protected Info info;

	public BaseChunkGen(Info info)
	{
		this.info=info;
	}
	public BaseChunkGen(Info defaultInfo,String specialKey)
	{
		this(Info.build(defaultInfo, ConfigJson.getOre(specialKey)));
	}
//	protected BaseChunkGen()
//	{
//		;
//	}

	@Override
	public List<BlockPos> genAtChunk(World world, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		List<BlockPos> ret=new ArrayList<>();
		final int time=Info.time(info,null,1);
		final float rate=Info.rate(info,null,1);
		for(int i=0;i<time;i++)
		{
			if(!canTrigger(rand,rate)) continue;

			BlockPos realPos=genRealPos(world, chunkVertexX, chunkVertexZ, rand);
			ret.addAll(genAtRealPos(world, realPos.getX(), realPos.getY(), realPos.getZ(), rand));
		}
		return ret;
	}

	@Override
	public BlockPos genRealPos(World world, int chunkVertexX, int chunkVertexZ, Random rand, int interval)
	{
		final int posX=chunkVertexX+interval+rand.nextInt(16-interval);

		final int maxY=Info.maxY(info,null,255),minY=Info.minY(info,null,0);

		final int temp=maxY>=minY?
				1+maxY-minY:
				world.getHeight()-minY;

		final int posY=minY + rand.nextInt(temp);
		final int posZ=chunkVertexZ+interval+rand.nextInt(16-interval);

		return new BlockPos(posX,posY,posZ);
	}

	public BaseChunkGen initWithInfo(Info info1, Info info2)
	{
		this.info=Info.build(
				Info.state(info1,info2, Blocks.STONE.getDefaultState() ),
				Info.strategyDim(info1,info2, Strategy.NONE_BLACKLIST ),
				Info.whitelistDim(info1,info2, null ),
				Info.blacklistDim(info1,info2, null ),
				Info.strategyBiome(info1,info2, Strategy.NONE_BLACKLIST ),
				Info.whitelistBiomes(info1,info2, null),
				Info.blacklistBiomes(info1,info2, null),
				Info.selector(info1,info2, Predicates::isStone ),
				Info.minY(info1,info2, 0 ),
				Info.maxY(info1,info2, 255 ),
				Info.time(info1,info2, 1 ),
				Info.rate(info1,info2, 1 ),
				Info.size(info1,info2, 1 )
		);
		return this;
	}

	@Override
	public boolean canGenAtDim(int targetDimId, World world, WorldProvider provider)
	{
		return info.canGenAtDim(targetDimId);
	}

	@Override
	public boolean canGenAtBiome(String targetBiomeName, World world, Biome biome)
	{
		return info.canGenAtBiome(targetBiomeName);
	}
}
