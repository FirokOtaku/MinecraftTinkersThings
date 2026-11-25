package firok.tiths.block;

import firok.tiths.common.Blocks;
import firok.tiths.tile.TESolidCloudPassageway;
import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockSolidCloudPassageway extends BlockSolidCloud
{
    public static final PropertyBool OPEN = PropertyBool.create("open");

    public BlockSolidCloudPassageway()
    {
        setDefaultState(blockState.getBaseState().withProperty(OPEN, false));
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, OPEN);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(OPEN) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState().withProperty(OPEN, (meta & 1) != 0);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return !state.getValue(OPEN);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return !state.getValue(OPEN);
    }

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return !state.getValue(OPEN);
    }

    @Override
    public int getLightOpacity(IBlockState state)
    {
        return state.getValue(OPEN) ? 0 : super.getLightOpacity(state);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return state.getValue(OPEN) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return state.getValue(OPEN) ? NULL_AABB : FULL_BLOCK_AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess world, BlockPos pos)
    {
        return world.getBlockState(pos).getValue(OPEN);
    }

    // Closed passageways support components, but must not bridge vanilla strong power
    // into another passageway and thereby skip the custom four-tick hop.
    @Override
    public boolean shouldCheckWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        return false;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer)
    {
        return layer == (state.getValue(OPEN) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side)
    {
        if (state.getValue(OPEN))
        {
            IBlockState adjacent = world.getBlockState(pos.offset(side));
            if ((adjacent.getBlock() == this && adjacent.getValue(OPEN)) || adjacent.getBlock() == Blocks.blockCloud)
                return false;
        }
        return super.shouldSideBeRendered(state, world, pos, side);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(net.minecraft.item.ItemStack stack, World world, java.util.List<String> tooltip,
                               net.minecraft.client.util.ITooltipFlag flag)
    {
        firok.tiths.util.InnerActions.addInformation(this, tooltip, flag);
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state)
    {
        return new TESolidCloudPassageway();
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        TESolidCloudPassageway.placed(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        TESolidCloudPassageway.refreshDirectInput(world, pos);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TESolidCloudPassageway.removed(world, pos);
        super.breakBlock(world, pos, state);
    }
}
