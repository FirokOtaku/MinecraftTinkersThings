package firok.tiths.block;

import firok.tiths.common.Items;
import firok.tiths.util.InnerActions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.BlockStem;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockActivatedFarmland extends BlockFarmland
{
    public BlockActivatedFarmland()
    {
        setHardness(0.6F);
        setSoundType(SoundType.GROUND);
    }

    @Override
    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plant)
    {
        // Forge checks vanilla farmland by identity, not by BlockFarmland's class.
        IBlockState farmland = Blocks.FARMLAND.getDefaultState().withProperty(MOISTURE, state.getValue(MOISTURE));
        return Blocks.FARMLAND.canSustainPlant(farmland, world, pos, direction, plant);
    }

    @Override
    public boolean isFertile(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock() == this && state.getValue(MOISTURE) > 0;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
    {
        if (world.isRemote) return;

        int moisture = state.getValue(MOISTURE);
        int nextMoisture = hasWater(world, pos) || world.isRainingAt(pos.up()) ? 7 : Math.max(moisture - 1, 0);
        if (moisture != nextMoisture)
        {
            world.setBlockState(pos, state.withProperty(MOISTURE, nextMoisture), 2);
        }
        // Dry, unplanted activated soil deliberately does not revert to dirt.

        BlockPos cropPos = pos.up();
        IBlockState cropState = world.getBlockState(cropPos);
        Block crop = cropState.getBlock();
        if (crop instanceof IGrowable && isCrop(world, cropPos, crop))
        {
            IGrowable growable = (IGrowable) crop;
            if (growable.canGrow(world, cropPos, cropState, false)
                    && growable.canUseBonemeal(world, random, cropPos, cropState))
            {
                growable.grow(world, random, cropPos, cropState);
            }
        }
    }

    private static boolean isCrop(World world, BlockPos pos, Block block)
    {
        return block instanceof BlockCrops || block instanceof BlockStem
                || block instanceof IPlantable && ((IPlantable) block).getPlantType(world, pos) == EnumPlantType.Crop;
    }

    private static boolean hasWater(World world, BlockPos pos)
    {
        for (BlockPos nearby : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4)))
        {
            if (world.getBlockState(nearby).getMaterial() == Material.WATER) return true;
        }
        return FarmlandWaterManager.hasBlockWaterTicket(world, pos);
    }

    @Override
    public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
    {
        // Keep normal fall damage without calling BlockFarmland's trampling logic.
        entity.fall(fallDistance, 1.0F);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        revertIfCovered(world, pos);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        revertIfCovered(world, pos);
    }

    private void revertIfCovered(World world, BlockPos pos)
    {
        if (!world.isRemote && world.getBlockState(pos).getBlock() == this
                && world.getBlockState(pos.up()).getMaterial().isSolid())
        {
            turnToDirt(world, pos);
            if (world.getBlockState(pos).getBlock() == Blocks.DIRT)
            {
                spawnAsEntity(world, pos, new ItemStack(Items.activatedCrystal));
            }
        }
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        drops.add(new ItemStack(Blocks.DIRT));
        drops.add(new ItemStack(Items.activatedCrystal));
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        // Recover both materials even when an explosion supplies a reduced drop chance.
        super.dropBlockAsItemWithChance(world, pos, state, 1.0F, fortune);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        return false;
    }

    @Override
    public EnumPushReaction getPushReaction(IBlockState state)
    {
        // Pistons execute the normal drops path before clearing the source for the piston head.
        return EnumPushReaction.DESTROY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag)
    {
        InnerActions.addInformation(this, tooltip, flag);
    }
}
