package firok.tiths.recipe.crystallizer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 结晶器配方的单个流体输入: 流体 id + 需求量.
 */
public final class CrystallizerFluidInput implements Comparable<CrystallizerFluidInput>
{
    private final ResourceLocation fluidId;
    private final int amount;

    public CrystallizerFluidInput(ResourceLocation fluidId, int amount)
    {
        this.fluidId = Objects.requireNonNull(fluidId, "fluidId");
        if (amount <= 0) throw new IllegalArgumentException("amount must be > 0");
        this.amount = amount;
    }

    public CrystallizerFluidInput(Fluid fluid, int amount)
    {
        this(CrystallizerRecipes.fluidId(fluid), amount);
    }

    public ResourceLocation getFluidId()
    {
        return fluidId;
    }

    public int getAmount()
    {
        return amount;
    }

    public FluidStack toFluidStack()
    {
        Fluid fluid = CrystallizerRecipes.getFluid(fluidId);
        if (fluid == null) return null;
        return new FluidStack(fluid, amount);
    }

    @Override
    public int compareTo(@Nonnull CrystallizerFluidInput o)
    {
        int c = this.fluidId.compareTo(o.fluidId);
        if (c != 0) return c;
        return Integer.compare(this.amount, o.amount);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof CrystallizerFluidInput)) return false;
        CrystallizerFluidInput that = (CrystallizerFluidInput) o;
        return amount == that.amount && fluidId.equals(that.fluidId);
    }

    @Override
    public int hashCode()
    {
        return 31 * fluidId.hashCode() + amount;
    }
}
