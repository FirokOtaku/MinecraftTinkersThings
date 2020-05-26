//package firok.tiths.world;
//
//import firok.tiths.common.Blocks;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.World;
//
//import java.util.Random;
//
//import static firok.tiths.util.Predicates.canMeteoGen;
//import static firok.tiths.util.Predicates.canTrigger;
//
//// 生成陨石
//public class WorldGenMeteorolite implements IChunkGen
//{
//	static IBlockState stateMeteo=Blocks.blockMeteorolite.getDefaultState();
//	public IBlockState stateOre;
//	public float rateOre;
//	public float rateChunk;
//	public int[] dimBanned;
//	public WorldGenMeteorolite(IBlockState stateOre, float rateOre, float rateChunk, int[] dimBanned)
//	{
//		this.stateOre=stateOre;
//		this.rateOre=rateOre;
//		this.rateChunk=rateChunk;
//		this.dimBanned=dimBanned;
//	}
//
//	@Override
//	public boolean canGenAtDim(int targetDimId)
//	{
//		for(int dimId:dimBanned)
//		{
//			if(targetDimId==dimId) return false;
//		}
//		return true;
//	}
//
//	@Override
//	public boolean gen(World world, int chunkX, int chunkZ, Random rand)
//	{
//		int posX=chunkX+5+rand.nextInt(11),posZ=chunkZ+5+rand.nextInt(11);
//		return generate(world, world.getTopSolidOrLiquidBlock(new BlockPos(posX,0,posZ)).down(2),stateOre, rateOre, rateChunk);
//	}
//
//	public static boolean generate(World world, BlockPos pos, IBlockState stateOre, float rateOre, float rateChunk)
//	{
//		Random rand=world.rand;
//		if(!canTrigger(rand,0.04f)) return true;
//
//		for(int ox=-4;ox<=4;ox++)
//		{
//			for(int oy=-4;oy<=4;oy++)
//			{
//				for(int oz=-4;oz<=4;oz++)
//				{
//					int distance=(int)( MathHelper.sqrt(ox*ox)+MathHelper.sqrt(oy*oy)+MathHelper.sqrt(oz*oz) );
//					float tempRate=rand.nextFloat();
//					if(tempRate<0.15*distance) continue;
//
//					boolean isOre=canTrigger(world,rateOre);
//					BlockPos tempPos=pos.add(ox,oy,oz);
////					Block blockTemp=world.getBlockState(tempPos).getBlock();
//					IBlockState stateTemp=world.getBlockState(tempPos);
//					boolean isReplaceable=canMeteoGen(stateTemp);
//					if(isReplaceable) world.setBlockState(tempPos,isOre?stateOre:stateMeteo);
//				}
//			}
//		}
//		return true;
//	}
//}
