package firok.tiths.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 真知晶球物品 / TE 共用的坐标 NBT 读写.
 */
public final class PalantirData
{
    public static final String TAG_ROOT = "Palantir";
    public static final String TAG_X = "X";
    public static final String TAG_Y = "Y";
    public static final String TAG_Z = "Z";
    public static final String TAG_DIM = "Dim";
    public static final String TAG_HAS = "Has";

    private PalantirData() {}

    public static boolean hasTarget(@Nullable NBTTagCompound nbt)
    {
        if (nbt == null || !nbt.hasKey(TAG_ROOT, 10)) return false;
        NBTTagCompound root = nbt.getCompoundTag(TAG_ROOT);
        return root.getBoolean(TAG_HAS);
    }

    public static boolean hasTarget(ItemStack stack)
    {
        return stack != null && !stack.isEmpty() && hasTarget(stack.getTagCompound());
    }

    @Nullable
    public static BlockPos getPos(@Nullable NBTTagCompound nbt)
    {
        if (!hasTarget(nbt)) return null;
        NBTTagCompound root = nbt.getCompoundTag(TAG_ROOT);
        return new BlockPos(root.getInteger(TAG_X), root.getInteger(TAG_Y), root.getInteger(TAG_Z));
    }

    @Nullable
    public static BlockPos getPos(ItemStack stack)
    {
        return stack == null || stack.isEmpty() ? null : getPos(stack.getTagCompound());
    }

    public static int getDim(@Nullable NBTTagCompound nbt)
    {
        if (!hasTarget(nbt)) return 0;
        return nbt.getCompoundTag(TAG_ROOT).getInteger(TAG_DIM);
    }

    public static int getDim(ItemStack stack)
    {
        return stack == null || stack.isEmpty() ? 0 : getDim(stack.getTagCompound());
    }

    public static void setTarget(NBTTagCompound nbt, BlockPos pos, int dim)
    {
        NBTTagCompound root = new NBTTagCompound();
        root.setBoolean(TAG_HAS, true);
        root.setInteger(TAG_X, pos.getX());
        root.setInteger(TAG_Y, pos.getY());
        root.setInteger(TAG_Z, pos.getZ());
        root.setInteger(TAG_DIM, dim);
        nbt.setTag(TAG_ROOT, root);
    }

    public static void setTarget(ItemStack stack, BlockPos pos, int dim)
    {
        if (stack == null || stack.isEmpty()) return;
        if (!stack.hasTagCompound()) stack.setTagCompound(new NBTTagCompound());
        setTarget(stack.getTagCompound(), pos, dim);
    }

    public static void setTarget(ItemStack stack, World world, BlockPos pos)
    {
        setTarget(stack, pos, world.provider.getDimension());
    }

    public static void clearTarget(NBTTagCompound nbt)
    {
        if (nbt == null) return;
        nbt.removeTag(TAG_ROOT);
    }

    public static void clearTarget(ItemStack stack)
    {
        if (stack == null || stack.isEmpty() || !stack.hasTagCompound()) return;
        clearTarget(stack.getTagCompound());
        if (stack.getTagCompound().isEmpty())
        {
            stack.setTagCompound(null);
        }
    }

    public static void copyTo(NBTTagCompound from, NBTTagCompound to)
    {
        if (hasTarget(from))
        {
            to.setTag(TAG_ROOT, from.getCompoundTag(TAG_ROOT).copy());
        }
        else
        {
            to.removeTag(TAG_ROOT);
        }
    }

    public static void writeToItem(ItemStack stack, @Nullable BlockPos pos, int dim, boolean has)
    {
        if (stack == null || stack.isEmpty()) return;
        if (!has || pos == null)
        {
            clearTarget(stack);
            return;
        }
        setTarget(stack, pos, dim);
    }
}
