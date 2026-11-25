package firok.tiths.block;

import firok.tiths.common.Configs;
import firok.tiths.util.Predicates;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemShears;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static firok.tiths.util.Predicates.canTrigger;

/** A freestanding vine that spreads upward from any randomly ticked segment. */
public class BlockToughvine extends Block
{
    private static final AxisAlignedBB BOUNDS = new AxisAlignedBB(0.125, 0, 0.125, 0.875, 1, 0.875);
    // A narrow core provides the horizontal collision needed to climb while the player's feet are in this block.
    private static final AxisAlignedBB COLLISION_BOUNDS = new AxisAlignedBB(0.375, 0, 0.375, 0.625, 1, 0.625);

    public BlockToughvine()
    {
        super(Material.PLANTS);
        setSoundType(SoundType.PLANT);
        setHardness(1.6f);
        setTickRandomly(true);
    }

    private boolean hasSupport(World world, BlockPos pos)
    {
        BlockPos below = pos.down();
        IBlockState support = world.getBlockState(below);
        return support.getBlock() == this || support.getBlock() == Blocks.FARMLAND
                || support.isSideSolid(world, below, EnumFacing.UP);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        // Manual stacking is deliberately independent of the natural growth cap.
        return super.canPlaceBlockAt(world, pos) && hasSupport(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if (!world.isRemote && !hasSupport(world, pos))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public void randomTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if (world.isRemote || canTrigger(world.rand, 0.35f)) return;

        int maxHeight = Configs.Gameplay.toughvine_max_height;
        int height = 1;
        BlockPos bottom = pos;
        BlockPos top = pos;
        while (bottom.getY() > 0 && world.getBlockState(bottom.down()).getBlock() == this)
        {
            bottom = bottom.down();
            height++;
        }
        while (top.getY() + 1 < world.getHeight() && world.getBlockState(top.up()).getBlock() == this)
        {
            top = top.up();
            height++;
        }

        BlockPos target = top.up();
        if (height < maxHeight && target.getY() < world.getHeight() && world.isAirBlock(target))
            world.setBlockState(target, getDefaultState());
    }

    @Override
    public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos)
    {
        float progress = super.getPlayerRelativeBlockHardness(state, player, world, pos);
        return player.getHeldItemMainhand().getItem() instanceof ItemShears ? progress * 12.0f : progress;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return BOUNDS;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return COLLISION_BOUNDS;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity)
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
