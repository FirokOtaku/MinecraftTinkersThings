package firok.tiths.recipe.crystallizer;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 结晶器合成配方: 多种流体输入 + 产物列表.
 * <p>
 * {@link #getTypeHash()} 仅由流体种类决定, 用于快速索引;
 * {@link #getRecipeHash()} 额外包含需求量, 用于配方身份标识.
 */
public final class CrystallizerRecipe
{
    private final ResourceLocation id;
    private final List<CrystallizerFluidInput> fluids;
    private final List<ItemStack> outputs;
    private final long typeHash;
    private final long recipeHash;

    public CrystallizerRecipe(ResourceLocation id, List<CrystallizerFluidInput> fluids, List<ItemStack> outputs)
    {
        if (id == null) throw new IllegalArgumentException("id");
        if (fluids == null || fluids.isEmpty()) throw new IllegalArgumentException("fluids");
        if (outputs == null || outputs.isEmpty()) throw new IllegalArgumentException("outputs");

        List<CrystallizerFluidInput> sorted = new ArrayList<>(fluids);
        sorted.sort(Comparator.comparing(CrystallizerFluidInput::getFluidId));
        // 同种流体不允许在同一配方里出现多次
        for (int i = 1; i < sorted.size(); i++)
        {
            if (sorted.get(i).getFluidId().equals(sorted.get(i - 1).getFluidId()))
            {
                throw new IllegalArgumentException("duplicate fluid in recipe: " + sorted.get(i).getFluidId());
            }
        }

        List<ItemStack> outCopy = new ArrayList<>(outputs.size());
        for (ItemStack stack : outputs)
        {
            if (stack == null || stack.isEmpty()) throw new IllegalArgumentException("empty output");
            outCopy.add(stack.copy());
        }

        this.id = id;
        this.fluids = Collections.unmodifiableList(sorted);
        this.outputs = Collections.unmodifiableList(outCopy);
        this.typeHash = CrystallizerRecipes.hashFluidTypes(this.fluids);
        this.recipeHash = CrystallizerRecipes.hashRecipe(this.fluids);
    }

    public ResourceLocation getId()
    {
        return id;
    }

    public List<CrystallizerFluidInput> getFluids()
    {
        return fluids;
    }

    public List<ItemStack> getOutputs()
    {
        return outputs;
    }

    /** 仅流体种类哈希, 用于扫描匹配 */
    public long getTypeHash()
    {
        return typeHash;
    }

    /** 流体种类 + 需求量哈希 */
    public long getRecipeHash()
    {
        return recipeHash;
    }

    public List<FluidStack> getFluidStacks()
    {
        List<FluidStack> list = new ArrayList<>(fluids.size());
        for (CrystallizerFluidInput input : fluids)
        {
            FluidStack stack = input.toFluidStack();
            if (stack != null) list.add(stack);
        }
        return list;
    }

    public List<ItemStack> copyOutputs()
    {
        List<ItemStack> list = new ArrayList<>(outputs.size());
        for (ItemStack stack : outputs)
        {
            list.add(stack.copy());
        }
        return list;
    }

    /**
     * 检查给定流体是否满足本配方需求 (种类集合一致且数量足够).
     */
    public boolean matches(List<FluidStack> stored)
    {
        if (stored == null || stored.size() != fluids.size()) return false;
        // stored 应按 fluidId 排序
        for (int i = 0; i < fluids.size(); i++)
        {
            CrystallizerFluidInput need = fluids.get(i);
            FluidStack have = stored.get(i);
            if (have == null || have.getFluid() == null) return false;
            if (!need.getFluidId().equals(CrystallizerRecipes.fluidId(have.getFluid()))) return false;
            if (have.amount < need.getAmount()) return false;
        }
        return true;
    }
}
