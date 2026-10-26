package firok.tiths.item;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

/**
 * 可以储存液体的物品
 */
public interface IFluid
{
    default void setFluidStack(ItemStack itemStack, FluidStack fluidStack)
    {
        if (itemStack == null) return;

        if (fluidStack == null || fluidStack.amount <= 0)
        {
            if (itemStack.hasTagCompound())
            {
                NBTTagCompound nbt = itemStack.getTagCompound();
                assert nbt != null;
                nbt.removeTag(FluidHandlerItemStack.FLUID_NBT_KEY);
                nbt.removeTag("FluidName");
                nbt.removeTag("Amount");
                nbt.removeTag("Tag");
                if (nbt.getKeySet().isEmpty()) itemStack.setTagCompound(null);
            }
            return;
        }

        NBTTagCompound nbt = itemStack.hasTagCompound() ? itemStack.getTagCompound() : new NBTTagCompound();
        assert nbt != null;
        // 清理旧版写在根上的液体 NBT, 避免与 FluidHandlerItemStack 冲突
        nbt.removeTag("FluidName");
        nbt.removeTag("Amount");
        nbt.removeTag("Tag");

        NBTTagCompound fluidTag = new NBTTagCompound();
        fluidStack.writeToNBT(fluidTag);
        nbt.setTag(FluidHandlerItemStack.FLUID_NBT_KEY, fluidTag);
        itemStack.setTagCompound(nbt);
    }

    default FluidStack getFluidStack(ItemStack itemStack)
    {
        if (itemStack == null || !itemStack.hasTagCompound()) return null;
        NBTTagCompound nbt = itemStack.getTagCompound();
        assert nbt != null;

        if (nbt.hasKey(FluidHandlerItemStack.FLUID_NBT_KEY))
        {
            return FluidStack.loadFluidStackFromNBT(nbt.getCompoundTag(FluidHandlerItemStack.FLUID_NBT_KEY));
        }

        // 兼容旧存档: 液体写在根 NBT, 读到后迁移到标准 Fluid 子标签
        FluidStack legacy = FluidStack.loadFluidStackFromNBT(nbt);
        if (legacy != null)
        {
            setFluidStack(itemStack, legacy);
        }
        return legacy;
    }
}
