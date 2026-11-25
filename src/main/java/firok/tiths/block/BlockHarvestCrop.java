package firok.tiths.block;

import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Supplier;

/** A wheat-like crop whose seeds and produce are supplied once registries are ready. */
public class BlockHarvestCrop extends BlockCrops
{
    private final Supplier<Item> seed;
    private final Supplier<Item> harvest;

    public BlockHarvestCrop(Supplier<Item> seed, Supplier<Item> harvest)
    {
        this.seed = seed;
        this.harvest = harvest;
    }

    @Override
    protected Item getSeed()
    {
        return seed.get();
    }

    @Override
    protected Item getCrop()
    {
        return harvest.get();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        if (isMaxAge(state))
        {
            Random random = world instanceof World ? ((World) world).rand : RANDOM;
            drops.add(new ItemStack(getSeed(), 1 + random.nextInt(2)));
            drops.add(new ItemStack(getCrop(), 1 + random.nextInt(2)));
        }
        else
        {
            drops.add(new ItemStack(getSeed()));
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        // Show the plantable seed, not the automatically registered crop ItemBlock.
    }
}
