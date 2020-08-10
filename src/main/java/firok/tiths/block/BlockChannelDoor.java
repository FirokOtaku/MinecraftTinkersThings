package firok.tiths.block;

import firok.tiths.common.Blocks;
import firok.tiths.util.InnerActions;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static firok.tiths.util.InnerActions.addWhenIntersect;

public class BlockChannelDoor extends BlockHorizontal
{
	public BlockChannelDoor()
	{
		super(Material.GLASS);
		this.setHardness(0.1f);
		this.setResistance(0.5f);
	}

	static final AxisAlignedBB aabbN=new AxisAlignedBB(0,0,0,1,1,0.1);
	static final AxisAlignedBB aabbS=new AxisAlignedBB(0,0,0.9,1,1,1);
	static final AxisAlignedBB aabbW=new AxisAlignedBB(0,0,0,0.1,1,1);
	static final AxisAlignedBB aabbE=new AxisAlignedBB(0.9,0,0,1,1,1);

	@Override
	public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, @Nullable Entity entity, boolean p_185477_7_)
	{
		if (entityBox == null) return;

		EnumFacing facing = state.getValue(FACING);
		int dir=facing.getIndex();
		//DOWN 0
		//UP 1
		//NORTH 2
		//SOUTH 3
		//WEST 4
		//EAST 5

		if(dir!=2) addWhenIntersect(entityBox, aabbN, pos, list);
		if(dir!=3) addWhenIntersect(entityBox, aabbS, pos, list);
		if(dir!=4) addWhenIntersect(entityBox, aabbW, pos, list);
		if(dir!=5) addWhenIntersect(entityBox, aabbE, pos, list);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		ItemStack stackHeld=player.getHeldItem(hand);

		if(stackHeld.isEmpty()) return false;

		Item itemHeld=stackHeld.getItem();
		Item channel=Item.getItemFromBlock(Blocks.blockChannel);

		if(itemHeld!=channel) return false;

//		System.out.printf("%s %f %f %f\n",facing.getName(),hitX,hitY,hitZ);
		int offsetY=hitY>0.5?1:-1;
		for(int step=1;step<=16;step++)
		{
			BlockPos posTemp = pos.add(0,step*offsetY,0);
			IBlockState stateTemp = world.getBlockState(posTemp);
			Block blockTemp = stateTemp.getBlock();

			if(!Predicates.isAir(blockTemp) && !Predicates.isWater(blockTemp) && !Predicates.isChannelComponent(blockTemp))
				return false;

			if(Predicates.isChannelComponent(blockTemp)) continue;

			world.setBlockState(posTemp,Blocks.blockChannel.getDefaultState());
			if(!player.isCreative()) stackHeld.shrink(1);
			return true;
		}

		return true;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		InnerActions.addInformation(this, tooltip, advanced);
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the
	 * IBlockstate
	 */
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state)
	{
		return (state.getValue(FACING)).getHorizontalIndex();
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, FACING);
	}

}
