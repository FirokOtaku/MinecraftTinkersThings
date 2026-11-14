package firok.tiths.gen;

import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * 一次生成请求的结果.
 */
public final class GenResult
{
    @Nullable
    private final ToolTier effectiveTier;
    private final List<ItemStack> stacks;
    private final boolean vanillaFallback;

    public GenResult(@Nullable ToolTier effectiveTier, List<ItemStack> stacks, boolean vanillaFallback)
    {
        this.effectiveTier = effectiveTier;
        this.stacks = stacks == null ? Collections.emptyList() : stacks;
        this.vanillaFallback = vanillaFallback;
    }

    public static GenResult ofEquipment(ToolTier tier, ItemStack stack)
    {
        return new GenResult(tier, Collections.singletonList(stack), false);
    }

    public static GenResult ofVanillaFallback(List<ItemStack> stacks)
    {
        return new GenResult(null, stacks, true);
    }

    @Nullable
    public ToolTier getEffectiveTier()
    {
        return effectiveTier;
    }

    public List<ItemStack> getStacks()
    {
        return stacks;
    }

    public boolean isVanillaFallback()
    {
        return vanillaFallback;
    }

    public ItemStack firstOrEmpty()
    {
        return stacks.isEmpty() ? ItemStack.EMPTY : stacks.get(0);
    }
}
