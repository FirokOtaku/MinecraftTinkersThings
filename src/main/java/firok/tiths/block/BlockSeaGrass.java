package firok.tiths.block;

import net.minecraft.block.*;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fluids.IFluidBlock;

import java.util.Random;

import static net.minecraft.util.EnumFacing.*;

/**
 * 海草
 */
public class BlockSeaGrass extends BlockBush implements IGrowable
{
	static final Material materialSeaGrass=new Material(MapColor.FOLIAGE){
		{
			setNoPushMobility();
			setBurning();
			setImmovableMobility();
		}

		public boolean isSolid()
		{
			return false;
		}
	};
	public BlockSeaGrass()
	{
		super(materialSeaGrass);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.setLightOpacity(1);
	}

	// 水生植物
	@Override
	public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Water;
	}

	// 海草的生长
	public boolean canGrow(World world, BlockPos pos, IBlockState state, boolean isClient)
	{
		Block blockUp=world.getBlockState(pos.up()).getBlock();
		return blockUp==Blocks.WATER || blockUp==Blocks.FLOWING_WATER;
	}

	public boolean canUseBonemeal(World world, Random rand, BlockPos pos, IBlockState state)
	{
		return world.getBlockState(pos.up()).getBlock()==Blocks.WATER &&
		       world.getBlockState(pos.up(2)).getBlock()==Blocks.WATER;
	}

	public void grow(World world, Random rand, BlockPos pos, IBlockState state)
	{
		Block blockUp= world.getBlockState(pos.up()).getBlock();
		if(blockUp==Blocks.WATER || blockUp==Blocks.FLOWING_WATER)
		{
			world.setBlockState(pos.up(),this.getDefaultState());
		}
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity en)
	{
		en.fallDistance=0;
		en.motionX*=0.6;
		en.motionY*=0.6;
		en.motionZ*=0.6;
	}

	// 用来模拟海草的连接
	public boolean canPlaceBlockAt(World world, BlockPos pos)
	{
		if(!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) return false;

		BlockPos posDown=pos.down(),posUp=pos.up();
		IBlockState stateDown = world.getBlockState(posDown);
		Block blockUp = world.getBlockState(posUp).getBlock();
		return (blockUp == Blocks.WATER || blockUp == Blocks.FLOWING_WATER) &&
		       canSustainBush(stateDown);
	}

	protected boolean canSustainBush(IBlockState state)
	{
//		if(state.getBlock()==this) return true;
		Material mat=state.getMaterial();
		return mat==materialSeaGrass || mat==Material.ROCK || mat==Material.GRASS || mat==Material.SAND || mat==Material.GROUND;
	}

	public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
	{
		Block blockUp=world.getBlockState(pos.up()).getBlock();
		return (blockUp == Blocks.WATER || blockUp == Blocks.FLOWING_WATER || blockUp == this) &&
		       canSustainBush(world.getBlockState(pos.down()));
//		return world.getBlockState(pos.up()).getBlock()==this ||
//		       world.getBlockState(pos.down()).getBlock()==this ;
	}

	// 用来屏蔽掉水方块的显示
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
	{
		IBlockState stateFacing=world.getBlockState(pos.offset(face));
		Block blockFacing=stateFacing.getBlock();
		if(isLiquid(blockFacing,false))
		{
			for(EnumFacing faceTemp: VALUES)
			{
				if(faceTemp==face) continue;
				IBlockState stateTemp=world.getBlockState(pos.offset(faceTemp));
				if ( !stateTemp.isOpaqueCube() || !isLiquid(stateTemp,true))
				{
					return true;
				}
			}
			return false;
		}
		return false;
	}
	private boolean isLiquid(IBlockState state,boolean checkSelf)
	{
		return isLiquid(state.getBlock(),checkSelf);
	}
	private boolean isLiquid(Block block,boolean checkSelf)
	{
		return (checkSelf && block==this ) || block instanceof BlockLiquid || block instanceof IFluidBlock;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
//	public boolean isOpaqueCube(IBlockState state)
//	{
//		return false;
//	}

	public boolean isFullCube(IBlockState state)
	{
		return true;
	}
}
