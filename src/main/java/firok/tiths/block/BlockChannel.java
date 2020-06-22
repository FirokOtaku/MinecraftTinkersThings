package firok.tiths.block;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 通道
 */
public class BlockChannel extends BlockCompressed
{
	public BlockChannel()
	{
		this.setHardness(0.1f);
		this.setResistance(0.5f);
	}

	static final AxisAlignedBB aabb1=new AxisAlignedBB(0,0,0,0.1,1,1);
	static final AxisAlignedBB aabb2=new AxisAlignedBB(0.9,0,0,1,1,1);
	static final AxisAlignedBB aabb3=new AxisAlignedBB(0.1,0,0,0.9,1,0.1);
	static final AxisAlignedBB aabb4=new AxisAlignedBB(0.1,0,0.9,0.9,1,1);

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, @Nullable Entity entity, boolean p_185477_7_)
	{
		if (entityBox == null) return;

		addWhenIntersect(entityBox, aabb1, pos, list);
		addWhenIntersect(entityBox, aabb2, pos, list);
		addWhenIntersect(entityBox, aabb3, pos, list);
		addWhenIntersect(entityBox, aabb4, pos, list);
	}

	public static void addWhenIntersect(AxisAlignedBB box1, AxisAlignedBB box2, BlockPos offset, List<AxisAlignedBB> list)
	{
		box2=box2.offset(offset);
		if(box1.intersects(box2)) list.add(box2);
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}



	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
	{
		return true;
	}

	/**
	 * Get the geometry of the queried face at the given position and state. This is used to decide whether things like
	 * buttons are allowed to be placed on the face, or how glass panes connect to the face, among other things.
	 * <p>
	 * Common values are {@code SOLID}, which is the default, and {@code UNDEFINED}, which represents something that
	 * does not fit the other descriptions and will generally cause other things not to connect to the face.
	 *
	 * @return an approximation of the form of the given face
	 */
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}
}
