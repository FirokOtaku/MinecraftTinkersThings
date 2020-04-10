package firok.tiths.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@SuppressWarnings("all")
public class BlockCloud extends Block
{
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	public BlockCloud()
	{
		super(Material.CLOTH);
	}
	public static final PropertyEnum<BlockSlab.EnumBlockHalf> HALF = PropertyEnum.<BlockSlab.EnumBlockHalf>create("half", BlockSlab.EnumBlockHalf.class);

	public BlockCloud(Material materialIn)
	{
		this(materialIn, materialIn.getMaterialMapColor());
	}

	public BlockCloud(Material p_i47249_1_, MapColor p_i47249_2_)
	{
		super(p_i47249_1_, p_i47249_2_);
		this.setLightOpacity(255);
	}

	protected boolean canSilkHarvest()
	{
		return true;
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return AABB_BOTTOM_HALF;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return AABB_BOTTOM_HALF;
	}

	/**
	 * Determines if the block is solid enough on the top side to support other blocks, like redstone components.
	 */
	public boolean isTopSolid(IBlockState state)
	{
		return true;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.SOLID;
//		if (((BlockSlab)state.getBlock()).isDouble())
//		{
//			return BlockFaceShape.SOLID;
//		}
//		else if (face == EnumFacing.UP && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
//		{
//			return BlockFaceShape.SOLID;
//		}
//		else
//		{
//			return face == EnumFacing.DOWN && state.getValue(HALF) == BlockSlab.EnumBlockHalf.BOTTOM ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
//		}
	}

	public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity, double yToTest, Material materialIn, boolean testingHead)
	{
		return null;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}


//	@Override
//	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
//	{
//		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
//			return super.doesSideBlockRendering(state, world, pos, face);
//
//		if ( state.isOpaqueCube() )
//			return true;
//
//		BlockSlab.EnumBlockHalf side = state.getValue(HALF);
//		return (side == BlockSlab.EnumBlockHalf.TOP && face == EnumFacing.UP) || (side == BlockSlab.EnumBlockHalf.BOTTOM && face == EnumFacing.DOWN);
//	}


	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
//		if (this.isDouble())
//		{
//			return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
//		}
//		else if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side))
//		{
//			return false;
//		}
//		else if (false) // Forge: Additional logic breaks doesSideBlockRendering and is no longer useful.
//		{
//			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
//			boolean flag = isHalfSlab(iblockstate) && iblockstate.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
//			boolean flag1 = isHalfSlab(blockState) && blockState.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP;
//
//			if (flag1)
//			{
//				if (side == EnumFacing.DOWN)
//				{
//					return true;
//				}
//				else if (side == EnumFacing.UP && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
//				{
//					return true;
//				}
//				else
//				{
//					return !isHalfSlab(iblockstate) || !flag;
//				}
//			}
//			else if (side == EnumFacing.UP)
//			{
//				return true;
//			}
//			else if (side == EnumFacing.DOWN && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
//			{
//				return true;
//			}
//			else
//			{
//				return !isHalfSlab(iblockstate) || flag;
//			}
//		}
//		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity en)
	{
		en.motionX*=0.4;
		if(en.motionY<0) en.motionY*=0.4;
		en.motionZ*=0.4;
	}
}
