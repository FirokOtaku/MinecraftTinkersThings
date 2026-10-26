package firok.tiths.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * 末影干涉临时方块: 仅玩家可穿过, 不可被挖掘 / 流体冲刷 / 爆炸等任何方式采集或破坏.
 */
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

        /**
         * 必须为 true, 否则原版 / Forge 流体会把本方块当作可冲刷方块并触发掉落.
         * 玩家穿行仍由 {@link #addCollisionBoxToList} 控制.
         */
        @Override
        public boolean blocksMovement()
        {
            return true;
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
            return false;
        }

        @Override
        public EnumPushReaction getPushReaction()
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
        this.disableStats();
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_)
    {
        if (entityBox == null || entityIn instanceof EntityPlayer) return;
        super.addCollisionBoxToList(state, worldIn, pos, entityBox, collidingBoxes, entityIn, p_185477_7_);
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return net.minecraft.init.Items.AIR;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.clear();
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        // 禁止任何掉落 (含流体冲刷路径)
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }

    @Override
    public boolean canDropFromExplosion(Explosion explosionIn)
    {
        return false;
    }

    @Override
    public void onBlockExploded(World world, BlockPos pos, Explosion explosion)
    {
        // 不被爆炸移除, 保留 TE 以便恢复原方块
    }

    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity)
    {
        return false;
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        // 生存 / 创造模式均不可挖掉; 由 TE 超时后自行还原
        return false;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack)
    {
        // 无掉落
    }

    @Override
    public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player)
    {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean hasTileEntity()
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new firok.tiths.tile.logic.TEEnderInterferedBlock();
    }
}

