package firok.tiths.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


/**
 * 塔拉特妮姆矿
 */
public class BlockOreTanatonium extends BlockOre
{
	public BlockOreTanatonium()
	{
		this.setEpicOre();
		this.setLightLevel(0.2f);
	}
// fixme 碰撞箱需要自定义
//	final static AxisAlignedBB aabb=new AxisAlignedBB(0,0,0,1,0.375,1);
//	@Override
//	public void addCollisionBoxToList(IBlockState state, World worldIn,
//	                                  BlockPos pos, AxisAlignedBB entityBox,
//	                                  List<AxisAlignedBB> collidingBoxes, Entity entityIn,
//	                                  boolean p_185477_7_)
//	{
//		AxisAlignedBB offset=aabb.offset(pos);
//		if(entityBox!=null && entityBox.intersects(offset))
//		{
//			collidingBoxes.add(offset);
//		}
//	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}


	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world,
	                                    BlockPos pos, EnumFacing side)
	{
		return side!=EnumFacing.UP && side!=EnumFacing.DOWN;
	}

//	@Override
//	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
//	{
//		return aabb;
//	}
}
