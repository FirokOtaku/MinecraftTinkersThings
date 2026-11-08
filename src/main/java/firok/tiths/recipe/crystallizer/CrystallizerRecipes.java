package firok.tiths.recipe.crystallizer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import firok.tiths.TinkersThings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 结晶器配方注册表.
 * <p>
 * 支持: 资源包 / mod assets 下 JSON、Forge 事件、直接 Java API 注册或覆盖.
 * 配方按流体种类哈希索引, 扫描时先算输入流体种类哈希再遍历候选列表.
 */
public final class CrystallizerRecipes
{
    public static final String RECIPE_DIR = "crystallizer_recipes";

    private static final Map<ResourceLocation, CrystallizerRecipe> BY_ID = new HashMap<>();
    private static final Map<Long, List<CrystallizerRecipe>> BY_TYPE_HASH = new HashMap<>();
    private static final Set<ResourceLocation> ACCEPTED_FLUIDS = new HashSet<>();
    private static boolean loaded = false;

    private CrystallizerRecipes() {}

    public static ResourceLocation fluidId(Fluid fluid)
    {
        if (fluid == null) throw new IllegalArgumentException("fluid");
        String name = fluid.getName();
        if (name.indexOf(':') >= 0) return new ResourceLocation(name);
        // 无命名空间的流体名统一落到 forge 域, 保证排序与哈希稳定
        return new ResourceLocation("forge", name);
    }

    @Nullable
    public static Fluid getFluid(ResourceLocation fluidId)
    {
        if (fluidId == null) return null;
        Fluid fluid = FluidRegistry.getFluid(fluidId.toString());
        if (fluid != null) return fluid;
        return FluidRegistry.getFluid(fluidId.getPath());
    }

    public static ResourceLocation parseFluidId(String raw)
    {
        if (raw == null || raw.isEmpty()) throw new IllegalArgumentException("empty fluid id");
        if (raw.indexOf(':') >= 0) return new ResourceLocation(raw);
        return new ResourceLocation("forge", raw);
    }

    /**
     * 仅流体种类: 数量 + 按 ResourceLocation 排序后的流体 id.
     */
    public static long hashFluidTypes(List<CrystallizerFluidInput> fluids)
    {
        long hash = fluids.size();
        for (CrystallizerFluidInput input : fluids)
        {
            hash = 31L * hash + input.getFluidId().hashCode();
        }
        return hash;
    }

    /**
     * 从已排序的 FluidStack 列表计算种类哈希.
     */
    public static long hashFluidTypesFromStacks(List<FluidStack> stacks)
    {
        long hash = stacks.size();
        for (FluidStack stack : stacks)
        {
            hash = 31L * hash + fluidId(stack.getFluid()).hashCode();
        }
        return hash;
    }

    /**
     * 完整配方哈希: 数量 + 流体 id + 需求量.
     */
    public static long hashRecipe(List<CrystallizerFluidInput> fluids)
    {
        long hash = fluids.size();
        for (CrystallizerFluidInput input : fluids)
        {
            hash = 31L * hash + input.getFluidId().hashCode();
            hash = 31L * hash + input.getAmount();
        }
        return hash;
    }

    /**
     * 注册或覆盖配方. 相同 id 覆盖.
     */
    public static void register(ResourceLocation id, CrystallizerRecipe recipe)
    {
        if (id == null || recipe == null) throw new IllegalArgumentException("id/recipe");
        if (!id.equals(recipe.getId()))
        {
            recipe = new CrystallizerRecipe(id, recipe.getFluids(), recipe.getOutputs());
        }
        BY_ID.put(id, recipe);
        rebuildIndexes();
    }

    public static void remove(ResourceLocation id)
    {
        if (BY_ID.remove(id) != null)
        {
            rebuildIndexes();
        }
    }

    public static void clear()
    {
        BY_ID.clear();
        BY_TYPE_HASH.clear();
        ACCEPTED_FLUIDS.clear();
    }

    private static void rebuildIndexes()
    {
        BY_TYPE_HASH.clear();
        ACCEPTED_FLUIDS.clear();
        for (CrystallizerRecipe recipe : BY_ID.values())
        {
            BY_TYPE_HASH.computeIfAbsent(recipe.getTypeHash(), k -> new ArrayList<>()).add(recipe);
            for (CrystallizerFluidInput input : recipe.getFluids())
            {
                ACCEPTED_FLUIDS.add(input.getFluidId());
            }
        }
    }

    public static boolean isAcceptedFluid(@Nullable Fluid fluid)
    {
        if (fluid == null) return false;
        return ACCEPTED_FLUIDS.contains(fluidId(fluid));
    }

    public static Collection<CrystallizerRecipe> getAll()
    {
        return Collections.unmodifiableCollection(BY_ID.values());
    }

    @Nullable
    public static CrystallizerRecipe get(ResourceLocation id)
    {
        return BY_ID.get(id);
    }

    /**
     * 按流体种类哈希取候选配方列表 (只读). 种类未变时可缓存此列表避免重复查表.
     */
    public static List<CrystallizerRecipe> getByTypeHash(long typeHash)
    {
        List<CrystallizerRecipe> candidates = BY_TYPE_HASH.get(typeHash);
        if (candidates == null || candidates.isEmpty()) return Collections.emptyList();
        return Collections.unmodifiableList(new ArrayList<>(candidates));
    }

    /**
     * 按输入流体种类哈希查找, 再遍历候选检查容量是否足够.
     * @param storedSorted 已按 fluidId 排序、且不含空罐的流体列表
     */
    @Nullable
    public static CrystallizerRecipe findMatch(List<FluidStack> storedSorted)
    {
        if (storedSorted == null || storedSorted.isEmpty()) return null;
        long typeHash = hashFluidTypesFromStacks(storedSorted);
        return findMatch(getByTypeHash(typeHash), storedSorted);
    }

    @Nullable
    public static CrystallizerRecipe findMatch(List<CrystallizerRecipe> candidates, List<FluidStack> storedSorted)
    {
        if (candidates == null || candidates.isEmpty() || storedSorted == null) return null;
        for (CrystallizerRecipe recipe : candidates)
        {
            if (recipe.matches(storedSorted)) return recipe;
        }
        return null;
    }

    /**
     * 重新加载全部配方: 清空 → 扫描各 mod 资源 → 触发注册事件.
     */
    public static void reload()
    {
        clear();
        loadFromResources();
        MinecraftForge.EVENT_BUS.post(new CrystallizerRecipeRegisterEvent());
        rebuildIndexes();
        loaded = true;
        TinkersThings.log("Crystallizer recipes loaded: " + BY_ID.size());
    }

    public static boolean isLoaded()
    {
        return loaded;
    }

    private static void loadFromResources()
    {
        for (ModContainer mod : Loader.instance().getActiveModList())
        {
            String base = "assets/" + mod.getModId() + "/" + RECIPE_DIR;
            JsonContext ctx = new JsonContext(mod.getModId());
            CraftingHelper.findFiles(
                    mod,
                    base,
                    null,
                    (root, file) ->
                    {
                        String relative = root.relativize(file).toString().replace('\\', '/');
                        if (!relative.endsWith(".json") || relative.startsWith("_"))
                        {
                            return true;
                        }
                        String name = relative.substring(0, relative.length() - ".json".length());
                        ResourceLocation id = new ResourceLocation(mod.getModId(), name);
                        try (BufferedReader reader = Files.newBufferedReader(file))
                        {
                            JsonElement element = new JsonParser().parse(reader);
                            if (!element.isJsonObject()) return true;
                            CrystallizerRecipe recipe = parseRecipe(id, element.getAsJsonObject(), ctx);
                            if (recipe != null)
                            {
                                BY_ID.put(id, recipe);
                            }
                        }
                        catch (Exception e)
                        {
                            TinkersThings.log("Failed to load crystallizer recipe " + id + ": " + e.getMessage());
                        }
                        return true;
                    },
                    true,
                    true
            );
        }
        rebuildIndexes();
    }

    @Nullable
    private static CrystallizerRecipe parseRecipe(ResourceLocation id, JsonObject json, JsonContext ctx)
    {
        if (json.has("disabled") && json.get("disabled").getAsBoolean())
        {
            BY_ID.remove(id);
            return null;
        }

        JsonArray fluidsJson = json.getAsJsonArray("fluids");
        if (fluidsJson == null || fluidsJson.size() == 0)
        {
            throw new IllegalArgumentException("missing fluids");
        }
        List<CrystallizerFluidInput> fluids = new ArrayList<>(fluidsJson.size());
        for (JsonElement el : fluidsJson)
        {
            JsonObject obj = el.getAsJsonObject();
            String fluidName = obj.get("fluid").getAsString();
            int amount = obj.get("amount").getAsInt();
            fluids.add(new CrystallizerFluidInput(parseFluidId(fluidName), amount));
        }

        JsonArray outputsJson = json.has("outputs") ? json.getAsJsonArray("outputs") : json.getAsJsonArray("results");
        if (outputsJson == null || outputsJson.size() == 0)
        {
            throw new IllegalArgumentException("missing outputs");
        }
        List<ItemStack> outputs = new ArrayList<>(outputsJson.size());
        for (JsonElement el : outputsJson)
        {
            ItemStack stack = CraftingHelper.getItemStack(el.getAsJsonObject(), ctx);
            if (stack == null || stack.isEmpty())
            {
                throw new IllegalArgumentException("invalid output in " + id);
            }
            outputs.add(stack);
        }

        return new CrystallizerRecipe(id, fluids, outputs);
    }

    /**
     * 将罐内流体整理为按 fluidId 排序的非空列表.
     */
    public static List<FluidStack> sortedNonEmpty(List<? extends FluidStack> tanks)
    {
        List<FluidStack> list = new ArrayList<>();
        for (FluidStack stack : tanks)
        {
            if (stack != null && stack.amount > 0 && stack.getFluid() != null)
            {
                list.add(stack);
            }
        }
        list.sort(Comparator.comparing(s -> fluidId(s.getFluid())));
        return list;
    }
}
