package firok.tiths.world;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.common.Blocks.oreStellarium;
import static net.minecraft.init.Blocks.*;
import static slimeknights.tconstruct.TConstruct.random;

/**
 * 生成随机矿物球 当前仍未实装
 * @author hidfug
 * @since 0.3.19.0 第三次世界生成模块修改
 */
public class WorldGenOreBall extends AbstractChunkGen
{
	@Override
	public List<BlockPos> genAtRealPos(World world, final int posX,final int posY,final int posZ, int chunkVertexX, int chunkVertexZ, Random rand)
	{
		IBlockState stateAir = AIR.getDefaultState();
		IBlockState stateCOAL_ORE = COAL_ORE.getDefaultState();
		IBlockState stateDIAMOND_ORE = DIAMOND_ORE.getDefaultState();
		IBlockState stateEMERALD_ORE = EMERALD_ORE.getDefaultState();
		IBlockState stateIRON_ORE = IRON_ORE.getDefaultState();
		IBlockState stateGOLD_ORE = GOLD_ORE.getDefaultState();
		IBlockState stateLAPIS_ORE = LAPIS_ORE.getDefaultState();
		IBlockState stateREDSTONE_ORE = REDSTONE_ORE.getDefaultState();
		IBlockState stateStellarium_ORE = oreStellarium.getDefaultState();

		int R;
		do {
			int Max = 8; // 最大半径
			R = random.nextInt(Max);
		} while (R <= 4); // 最小半径
		int r = R * 2/3; //内球半径

		int BlockScalar = (int) (Math.PI * (R * R * R) * 3/4);

		BlockPos[] block = new BlockPos[BlockScalar];

		int i = 0;
		FOR_x:
		for (int Ix = -R; Ix <= R; Ix++) {
			for (int Iy = -R; Iy <= R; Iy++) {
				for (int Iz = -R; Iz <= R; Iz++) {
					int distance = Ix * Ix + Iy * Iy + Iz * Iz;
					if (distance > r * r && distance <= R * R) { // 球壳内方块随机填为矿物
						BlockPos posold = new BlockPos(Ix+ (double) posX,Iy+ (double) posY,Iz+ (double) posZ);
						IBlockState stateold = AbstractChunkGen.getState(world,posold,chunkVertexX,chunkVertexZ);
						Block blockold = stateold.getBlock();
						double j=Math.random();
						if (j<0.3 && blockold != AIR){ // 球壳内方块填充为矿物概率 && 非空气方块替换
							block[i] = new BlockPos(Ix,Iy,Iz);
							i++;
						}
					}
				}
			}
		}
		for (int Ix = -R; Ix < R; Ix++) {
			for (int Iy = -R; Iy < R; Iy++) {
				for (int Iz = -R; Iz < R; Iz++) {
					if (Ix * Ix + Iy * Iy + Iz * Iz <= R * R) {
						BlockPos posTemp = new BlockPos(Ix+ (double) posX,Iy+ (double) posY,Iz+ (double) posZ);
						AbstractChunkGen.setState(world,posTemp,stateAir,chunkVertexX,chunkVertexZ);
					}
				}
			}
		}

		for (int Ix = -R; Ix < R; Ix++) {
			for (int Iy = -R; Iy < R; Iy++) {
				for (int Iz = -R; Iz < R; Iz++) {
					if (Ix * Ix + Iy * Iy + Iz * Iz <= R * R/9){ // 中心生成
						BlockPos posTemp = new BlockPos(Ix+ (double) posX,Iy+ (double) posY -(R-r),Iz+ (double) posZ);
						AbstractChunkGen.setState(world,posTemp,stateStellarium_ORE,chunkVertexX,chunkVertexZ);
					}
				}
			}
		}

		for (i=0;i<=BlockScalar;i++){
			BlockPos posTemp = new BlockPos((double) posX +block[i].getX(), (double) posY +block[i].getY(), (double) posZ +block[i].getZ());
			switch (random.nextInt(8)){
				case 1:
					AbstractChunkGen.setState(world,posTemp,stateCOAL_ORE,chunkVertexX,chunkVertexZ);break;
				case 2:
					AbstractChunkGen.setState(world,posTemp,stateDIAMOND_ORE,chunkVertexX,chunkVertexZ);break ;
				case 3:
					AbstractChunkGen.setState(world,posTemp,stateEMERALD_ORE,chunkVertexX,chunkVertexZ);break ;
				case 4:
					AbstractChunkGen.setState(world,posTemp,stateGOLD_ORE,chunkVertexX,chunkVertexZ);break ;
				case 5:
					AbstractChunkGen.setState(world,posTemp,stateIRON_ORE,chunkVertexX,chunkVertexZ);break ;
				case 6:
					AbstractChunkGen.setState(world,posTemp,stateLAPIS_ORE,chunkVertexX,chunkVertexZ);break ;
				case 7:
					AbstractChunkGen.setState(world,posTemp,stateREDSTONE_ORE,chunkVertexX,chunkVertexZ);break ;
			}
		}

		return new ArrayList<>();
	}
}
