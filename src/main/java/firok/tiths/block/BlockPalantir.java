package firok.tiths.block;

import firok.tiths.item.ItemPalantir;
import firok.tiths.tile.TEPalantir;
import firok.tiths.util.InnerActions;
import firok.tiths.util.PalantirData;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

/**
 * 真知晶球方块: 半格体积, 弱光, 徒手可采; 视觉浮动与远眺由 TESR 负责.
 */
public class BlockPalantir extends Block implements ITileEntityProvider
{
    /** 居中 8×8×8 */
    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.25D, 0.25D, 0.25D, 0.75D, 0.75D, 0.75D);

    public BlockPalantir()
    {
        super(Material.GLASS);
        this.setHardness(0.15F);
        this.setResistance(0.5F);
        this.setSoundType(SoundType.GLASS);
        this.setLightLevel(2f / 15f);
        this.setLightOpacity(0);
        this.hasTileEntity = true;
        this.setHarvestLevel(null, -1);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TEPalantir();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return AABB;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
    {
        return AABB;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        // 主体与浮动由 TESR 绘制, 避免与方块模型叠影
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        // 潜行交给手持物品处理标记逻辑
        if (playerIn.isSneaking()) return false;

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TEPalantir)) return false;
        TEPalantir palantir = (TEPalantir) te;
        if (!palantir.hasTarget()) return true;

        if (!worldIn.isRemote)
        {
            palantir.activateDisplay();
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TEPalantir)
        {
            ((TEPalantir) te).readTargetFromNbt(stack.hasTagCompound() ? stack.getTagCompound() : new net.minecraft.nbt.NBTTagCompound());
            if (!worldIn.isRemote)
            {
                IBlockState s = worldIn.getBlockState(pos);
                worldIn.notifyBlockUpdate(pos, s, s, 3);
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(this);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state,
                             @Nullable TileEntity te, ItemStack stack)
    {
        if (!worldIn.isRemote && !player.isCreative())
        {
            spawnAsEntity(worldIn, pos, createDropStack(te));
        }
        // removedByPlayer 在 willHarvest 时延迟移除, 这里真正拆掉方块
        worldIn.setBlockToAir(pos);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        // willHarvest=true 时延后到 harvestBlock 再移除, 以便读取 TE NBT
        if (willHarvest) return true;
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(createDropStack(world.getTileEntity(pos)));
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return createDropStack(world.getTileEntity(pos));
    }

    public static ItemStack createDropStack(@Nullable TileEntity te)
    {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(firok.tiths.common.Blocks.blockPalantir));
        if (te instanceof TEPalantir)
        {
            TEPalantir palantir = (TEPalantir) te;
            if (palantir.hasTarget())
            {
                PalantirData.setTarget(stack, palantir.getTargetPos(), palantir.getTargetDim());
            }
        }
        return stack;
    }

    @Override
    public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param)
    {
        super.eventReceived(state, worldIn, pos, id, param);
        TileEntity te = worldIn.getTileEntity(pos);
        return te != null && te.receiveClientEvent(id, param);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag advanced)
    {
        InnerActions.addInformation(this, tooltip, advanced);
        ItemPalantir.appendTargetTooltip(stack, tooltip);
    }
}
