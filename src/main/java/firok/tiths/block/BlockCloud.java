package firok.tiths.block;

import firok.tiths.common.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

//@SuppressWarnings("all")
public class BlockCloud extends BlockCompressed
{
	protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	public BlockCloud()
	{
		this(Material.CLOTH);
	}
	public static final PropertyEnum<BlockSlab.EnumBlockHalf> HALF = PropertyEnum.<BlockSlab.EnumBlockHalf>create("half", BlockSlab.EnumBlockHalf.class);

	public BlockCloud(Material materialIn)
	{
		this(materialIn, materialIn.getMaterialMapColor());
	}

	public BlockCloud(Material material, MapColor color)
	{
		super(material, color);
		this.setLightOpacity(255);
		this.setHardness(0.4F);
		this.setSoundType(SoundType.CLOTH);
		this.enableTransparent();
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


	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity en)
	{
		en.fallDistance=0;
		en.motionX*=0.4;
		if(en.motionY<0) en.motionY*=0.4;
		en.motionZ*=0.4;
	}

	@Override
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		Block block=world.getBlockState(pos.offset(side)).getBlock();
		return block != Blocks.oreBrumeJade && block != this;
	}
}
