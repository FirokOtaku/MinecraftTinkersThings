package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static firok.tiths.util.Predicates.isStone;
import static net.minecraft.util.EnumFacing.*;

/**
 * 叶绿矿
 */
public class BlockOreChloroplast extends BlockCompressed
{
	public BlockOreChloroplast()
	{
		super(Material.IRON);
		this.setTickRandomly(true);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		this.updateTick(world,pos,state, world.rand);
		return true;
	}



	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if(!world.isRemote)
		{
			scan(world, pos);
		}
	}

//	int checkBlocks(Block... blocks)
//	{
//		int ret=0;
//		for(Block block:blocks) if(block==this) ret++;
//		return ret;
//	}
//	void exe(World world,BlockPos pos,Random rand)
//	{
//		Block up=world.getBlockState(pos.up()).getBlock(),
//				down=world.getBlockState(pos.down()).getBlock(),
//				east=world.getBlockState(pos.east()).getBlock(),
//				west=world.getBlockState(pos.west()).getBlock(),
//				south=world.getBlockState(pos.south()).getBlock(),
//				north=world.getBlockState(pos.north()).getBlock();
//
//		int count=checkBlocks(up,down,east,west,south,north);
//
//		float rate=1;
//		if(down==this) rate-=0.25;
//		if(up==this) rate-=0.25;
//		if(east==this) rate-=0.25;
//		if(west==this) rate-=0.25;
//		if(north==this) rate-=0.25;
//		if(south==this) rate-=0.25;
//
//		System.out.println("rate="+rate);
//
//		if(canTrigger(rand,rate))
//		{
//			if(down==this) gen(world, pos, DOWN);
//			else if(up==this) gen(world, pos, UP);
//			else if(east==this) gen(world, pos, EAST);
//			else if(west==this) gen(world, pos, WEST);
//			else if(north==this) gen(world, pos, NORTH);
//			else if(south==this) gen(world, pos, SOUTH);
//			else gen(world, pos, UP);
//		}
//	}
//	void gen(World world,BlockPos center,EnumFacing dir)
//	{
//		System.out.println("gen");
//		final int oxMax=dir.getFrontOffsetX()==0?1:dir.getFrontOffsetX(),oxMin=-oxMax;
//		final int oyMax=dir.getFrontOffsetY()==0?1:dir.getFrontOffsetY(),oyMin=-oyMax;
//		final int ozMax=dir.getFrontOffsetZ()==0?1:dir.getFrontOffsetZ(),ozMin=-ozMax;
//
//		List<BlockPos> posAva=new ArrayList<>(11);
//
//		FOR_X:for(int ox=oxMin;ox<=oxMax;ox++)
//		{
//			FOR_Y:for(int oy=oyMin;oy<=oyMax;oy++)
//			{
//				FOR_Z:for(int oz=ozMin;oz<=ozMax;oz++)
//				{
//					BlockPos pos=center.add(ox,oy,oz);
//					Block block=world.getBlockState(pos).getBlock();
//					if(block==this) continue FOR_Z;
//
//					posAva.add(pos);
//				}
//			}
//		}
//
//		randomSpawn(world,posAva);
//	}

	void randomSpawn(World world,List<BlockPos> list)
	{
		BlockPos pos2spawn;
		final int size=list.size();
		if(size<=0) return;
		else if(size==1) pos2spawn=list.get(0);
		else pos2spawn=list.get( world.rand.nextInt(size) );

		world.setBlockState(pos2spawn,this.getDefaultState());
	}

	boolean canSpawnOn(World world,BlockPos pos,IBlockState state,Block block)
	{
		return isStone(block);
	}

	void scan(World world,BlockPos pos)
	{
		int total=0,up=0,down=0,east=0,west=0,south=0,north=0; // 各个方向上的数量
		Block[][][] blocksNearby=new Block[3][3][3]; // 考虑了一下 目前还是扫描3×3×3比较合适
		FOR_X:for(int ox=-1;ox<=1;ox++)
		{
			FOR_Y:for(int oy=-1;oy<=1;oy++)
			{
				FOR_Z:for(int oz=-1;oz<=1;oz++)
				{
					BlockPos posTemp=pos.add(ox,oy,oz);
					Block block=world.getBlockState(posTemp).getBlock();
					blocksNearby[1+ox][1+oy][1+oz]=block;

					if(block==this) // 遍历扫描周围方块类型
					{
						total++;

						if(total>4) break FOR_X; // 大于4块直接停止本次尝试

						if(oy==1) up++;
						if(oy==-1) down++;
						if(oz==1) south++;
						if(oz==-1) north++;
						if(ox==1) east++;
						if(ox==-1) west++;
					}
				}
			}
		}

		if(total<=4)
		{
			EnumFacing dir;
			if(down>0) dir=DOWN;
			else if(up>0) dir=UP;
			else if(east>0) dir=EAST;
			else if(west>0) dir=WEST;
			else if(north>0) dir=NORTH;
			else if(south>0) dir=SOUTH;
			else dir=UP;

			// 下面3组数字对应在3个方向上的生成检查范围 根据指定的dir不同 只会在某一个方向上的3×3生成新的矿物
			final int oxMax=dir.getFrontOffsetX()==0?1:dir.getFrontOffsetX(),oxMin=-oxMax;
			final int oyMax=dir.getFrontOffsetY()==0?1:dir.getFrontOffsetY(),oyMin=-oyMax;
			final int ozMax=dir.getFrontOffsetZ()==0?1:dir.getFrontOffsetZ(),ozMin=-ozMax;

			List<BlockPos> listPos=new ArrayList<>(10);
			FOR_X:for(int ox=oxMin;ox<=oxMax;ox++)
			{
				FOR_Y:for(int oy=oyMin;oy<=oyMax;oy++)
				{
					FOR_Z:for(int oz=ozMin;oz<=ozMax;oz++)
					{
						if(blocksNearby[1+ox][1+oy][1+oz]!=this) // fixme 以后改成 canSpawnOn
						{
							listPos.add(pos.add(ox,oy,oz)); // 能用的生成位置
						}
					}
				}
			}

			randomSpawn(world,listPos); // 随机找一个生成
		}
	}

}
