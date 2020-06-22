package firok.tiths.block;

import firok.tiths.common.Items;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * 拟素矿
 */
public class BlockOreImitatium extends BlockOre
{
	public BlockOreImitatium()
	{
		super(Material.ROCK, Items.nuggetImitatium, 3, 6, 2, 6, 10);
		this.setEpicOre();
		this.setLightOpacity(0);
		this.setLightLevel(0.1f);
	}

	final static AxisAlignedBB aabb=new AxisAlignedBB(0,0,0,1,0.375,1);
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn,
	                                  BlockPos pos, AxisAlignedBB entityBox,
	                                  List<AxisAlignedBB> collidingBoxes, Entity entityIn,
	                                  boolean p_185477_7_)
	{
		if(entityBox!=null) addCollisionBoxToList(pos,entityBox,collidingBoxes,aabb);
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}


	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockstateThis, IBlockAccess world,
	                                    BlockPos pos, EnumFacing side)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos)
	{
		return aabb;
	}
}
