package firok.tiths.block;

import com.google.common.collect.ImmutableList;
import firok.tiths.util.InnerActions;
import net.minecraft.block.*;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLogic;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.common.property.PropertyFloat;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static net.minecraft.util.EnumFacing.*;

/**
 * 海草
 */
public class BlockSeaGrass extends BlockBush implements IGrowable
{
//	static final Material materialSeaGrass=new Material(MapColor.FOLIAGE){
//		{
//			setNoPushMobility();
//		}
//
//		public EnumPushReaction getMobilityFlag()
//		{
//			return EnumPushReaction.DESTROY;
//		}
//
//		/**
//		 * Returns true if the block is a considered solid. This is true by default.
//		 */
//		public boolean isSolid()
//		{
//			return false;
//		}
//
//		/**
//		 * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
//		 */
//		public boolean blocksLight()
//		{
//			return false;
//		}
//
//		/**
//		 * Returns if this material is considered solid or not
//		 */
//		public boolean blocksMovement()
//		{
//			return false;
//		}
//	};
	public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
	public BlockSeaGrass()
	{
		super(Material.WATER);
		this.setHardness(0.0F);
		this.setSoundType(SoundType.PLANT);
		this.setLightOpacity(1);
		this.setDefaultState(blockState.getBaseState().withProperty(LEVEL, 15));
	}

	@Override
	@Nonnull
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this)
				.add(LEVEL)
				.build();
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(@Nonnull IBlockState state)
	{
		return 0;
	}
	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	@Deprecated
	@Nonnull
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(LEVEL, 15);
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
		return state.getBlock()==this || mat==Material.ROCK || mat==Material.GRASS || mat==Material.SAND || mat==Material.GROUND;
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
	@SideOnly(Side.CLIENT)
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

//	public boolean isFullCube(IBlockState state)
//	{
//		return false;
//	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		InnerActions.addInformation(this, tooltip, advanced);
	}
}
