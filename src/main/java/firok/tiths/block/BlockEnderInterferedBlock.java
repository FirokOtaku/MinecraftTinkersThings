package firok.tiths.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockEnderInterferedBlock
	extends BlockCompressed
	implements ITileEntityProvider
{
	static final Material magic = new Material(MapColor.PURPLE)
	{
		@Override
		public boolean isLiquid()
		{
			return false;
		}

		@Override
		public boolean isSolid()
		{
			return false;
		}

		@Override
		public boolean blocksLight()
		{
			return true;
		}

		@Override
		public boolean blocksMovement()
		{
			return false;
		}

		@Override
		public boolean isReplaceable()
		{
			return false;
		}

		@Override
		public boolean isOpaque()
		{
			return false;
		}

		@Override
		public boolean isToolNotRequired()
		{
			return true;
		}

		@Override
		public EnumPushReaction getMobilityFlag()
		{
			return EnumPushReaction.BLOCK;
		}
	};
	public BlockEnderInterferedBlock()
	{
		super(magic);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		this.setLightLevel(0.25f);
		this.setLightOpacity(15);
		this.enableTransparent();
	}

//	/**
//	 * The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only,
//	 * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
//	 */
//	public EnumBlockRenderType getRenderType(IBlockState state)
//	{
//		return EnumBlockRenderType.MODEL;
//	}
//
//	@SideOnly(Side.CLIENT)
//	public BlockRenderLayer getBlockLayer()
//	{
//		return BlockRenderLayer.TRANSLUCENT;
//	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

//	@Override
//	@SideOnly(Side.CLIENT)
//	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
//	{
//		return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
////		return true;
//	}
//
//	@Override
//	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
//	{
//		return super.doesSideBlockRendering(state, world, pos, face);
////		return true;
//	}



	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
	{
//		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
		if(entityBox == null || entityIn instanceof EntityPlayer) return;
		super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
	}

	@Override
	public boolean hasTileEntity()
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta)
	{
		return null;
	}


}
