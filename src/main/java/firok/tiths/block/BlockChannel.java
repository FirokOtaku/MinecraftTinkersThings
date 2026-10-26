package firok.tiths.block;

import firok.tiths.common.Blocks;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

import static firok.tiths.util.InnerActions.addWhenIntersect;

/**
 * 通道
 */
public class BlockChannel extends BlockCompressed
{
    static final AxisAlignedBB aabb1 = new AxisAlignedBB(0, 0, 0, 0.1, 1, 1);
    static final AxisAlignedBB aabb2 = new AxisAlignedBB(0.9, 0, 0, 1, 1, 1);
    static final AxisAlignedBB aabb3 = new AxisAlignedBB(0.1, 0, 0, 0.9, 1, 0.1);
    static final AxisAlignedBB aabb4 = new AxisAlignedBB(0.1, 0, 0.9, 0.9, 1, 1);
    public BlockChannel()
    {
        this.setHardness(0.1f);
        this.setResistance(0.5f);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> list, @Nullable Entity entity, boolean p_185477_7_)
    {
        if (entityBox == null) return;

        addWhenIntersect(entityBox, aabb1, pos, list);
        addWhenIntersect(entityBox, aabb2, pos, list);
        addWhenIntersect(entityBox, aabb3, pos, list);
        addWhenIntersect(entityBox, aabb4, pos, list);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack stackHeld = player.getHeldItem(hand);

        if (stackHeld.isEmpty()) return false;

        Item itemHeld = stackHeld.getItem();
        Item channel = Item.getItemFromBlock(Blocks.blockChannel);

        if (itemHeld != channel) return false;

//		System.out.printf("%s %f %f %f\n",facing.getName(),hitX,hitY,hitZ);
        int offsetY = hitY > 0.5 ? 1 : -1;
        for (int step = 1; step <= 16; step++)
        {
            BlockPos posTemp = pos.add(0, step * offsetY, 0);
            IBlockState stateTemp = world.getBlockState(posTemp);
            Block blockTemp = stateTemp.getBlock();

            if (!Predicates.isAir(blockTemp) && !Predicates.isWater(blockTemp) && !Predicates.isChannelComponent(blockTemp))
                return false;

            if (Predicates.isChannelComponent(blockTemp)) continue;

            world.setBlockState(posTemp, Blocks.blockChannel.getDefaultState());
            if (!player.isCreative()) stackHeld.shrink(1);
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
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
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
