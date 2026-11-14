package firok.tiths.gen;

import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.ToolCore;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 按品级与上下文随机组装匠魂工具 / 武器 / ConArm 护甲.
 */
public final class TinkerEquipmentGenerator
{
    private static final Set<String> WARNED_MODIFIERS = new HashSet<>();

    private TinkerEquipmentGenerator() {}

    /**
     * 生成一件成品; 品级全禁用时回退为一件原版道具.
     */
    public static ItemStack generateOne(GenContext context, ToolTier requestedTier, EquipKind kind, Random random)
    {
        return generate(context, requestedTier, kind, random).firstOrEmpty();
    }

    public static GenResult generate(GenContext context, ToolTier requestedTier, EquipKind kind, Random random)
    {
        ToolTier tier = TierGeneratorSelector.resolve(requestedTier);
        if (tier == null)
        {
            return GenResult.ofVanillaFallback(VanillaLootFallback.roll(random));
        }

        if (kind == EquipKind.ARMOR)
        {
            if (!TinkersThings.enableConarm())
            {
                // 无护甲联动时降级为工具
                kind = EquipKind.TOOL;
            }
            else
            {
                ItemStack armor = buildRandomArmor(tier, context, random);
                if (!armor.isEmpty())
                {
                    tryApplyBonusModifier(armor, EquipKind.ARMOR, random);
                    return GenResult.ofEquipment(tier, armor);
                }
                return GenResult.ofVanillaFallback(VanillaLootFallback.roll(random));
            }
        }

        ItemStack tool = buildRandomTool(tier, context, kind, random);
        if (!tool.isEmpty())
        {
            tryApplyBonusModifier(tool, kind, random);
            return GenResult.ofEquipment(tier, tool);
        }
        return GenResult.ofVanillaFallback(VanillaLootFallback.roll(random));
    }

    private static ItemStack buildRandomTool(ToolTier tier, GenContext context, EquipKind kind, Random random)
    {
        List<ToolCore> tools = collectTools(kind);
        if (tools.isEmpty())
        {
            TinkersThings.log("No ToolCore registered for kind " + kind);
            return ItemStack.EMPTY;
        }

        List<Material> pool = MaterialPoolResolver.resolveForRoll(tier, context, random);
        if (pool.isEmpty())
        {
            TinkersThings.log("Material pool empty for tier " + tier + " / " + context);
            return ItemStack.EMPTY;
        }

        // 多试几次, 避免某把工具部件需求过严导致整轮失败
        for (int attempt = 0; attempt < 8; attempt++)
        {
            ToolCore tool = tools.get(random.nextInt(tools.size()));
            ItemStack built = buildWithMaterials(tool, pool, random);
            if (!built.isEmpty())
            {
                return built;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack buildRandomArmor(ToolTier tier, GenContext context, Random random)
    {
        List<ArmorCore> armors = new ArrayList<>(ArmoryRegistry.getArmor());
        if (armors.isEmpty())
        {
            TinkersThings.log("No ArmorCore registered");
            return ItemStack.EMPTY;
        }

        List<Material> pool = MaterialPoolResolver.resolveForRoll(tier, context, random);
        if (pool.isEmpty())
        {
            return ItemStack.EMPTY;
        }

        for (int attempt = 0; attempt < 8; attempt++)
        {
            ArmorCore armor = armors.get(random.nextInt(armors.size()));
            ItemStack built = buildArmorWithMaterials(armor, pool, random);
            if (!built.isEmpty())
            {
                return built;
            }
        }
        return ItemStack.EMPTY;
    }

    private static ItemStack buildWithMaterials(ToolCore tool, List<Material> pool, Random random)
    {
        List<Material> chosen = pickMaterials(tool.getRequiredComponents(), pool, random);
        return chosen == null ? ItemStack.EMPTY : tool.buildItem(chosen);
    }

    private static ItemStack buildArmorWithMaterials(ArmorCore armor, List<Material> pool, Random random)
    {
        List<Material> chosen = pickMaterials(armor.getRequiredComponents(), pool, random);
        return chosen == null ? ItemStack.EMPTY : armor.buildItem(chosen);
    }

    @Nullable
    private static List<Material> pickMaterials(List<PartMaterialType> components, List<Material> pool, Random random)
    {
        if (components == null || components.isEmpty())
        {
            return null;
        }
        List<Material> chosen = new ArrayList<>(components.size());
        for (PartMaterialType pmt : components)
        {
            Material mat = pickValidMaterial(pmt, pool, random);
            if (mat == null)
            {
                return null;
            }
            chosen.add(mat);
        }
        return chosen;
    }

    @Nullable
    private static Material pickValidMaterial(PartMaterialType pmt, List<Material> pool, Random random)
    {
        List<Material> valid = new ArrayList<>();
        for (Material mat : pool)
        {
            if (pmt.isValidMaterial(mat))
            {
                valid.add(mat);
            }
        }
        if (valid.isEmpty())
        {
            return null;
        }
        return valid.get(random.nextInt(valid.size()));
    }

    private static List<ToolCore> collectTools(EquipKind kind)
    {
        List<ToolCore> out = new ArrayList<>();
        for (ToolCore tool : TinkerRegistry.getTools())
        {
            if (tool == null) continue;
            if (matchesKind(tool, kind))
            {
                out.add(tool);
            }
        }
        return out;
    }

    private static boolean matchesKind(ToolCore tool, EquipKind kind)
    {
        switch (kind)
        {
            case TOOL:
                return tool.hasCategory(Category.HARVEST);
            case WEAPON:
                return tool.hasCategory(Category.WEAPON) || tool.hasCategory(Category.LAUNCHER);
            case ARMOR:
            default:
                return false;
        }
    }

    private static void tryApplyBonusModifier(ItemStack stack, EquipKind kind, Random random)
    {
        if (stack.isEmpty()) return;
        if (random.nextDouble() >= Configs.Gameplay.bonus_modifier_chance) return;

        String[] ids = kind.getBonusModifierIds();
        if (ids == null || ids.length == 0) return;

        List<String> candidates = new ArrayList<>();
        for (String id : ids)
        {
            if (id != null && !id.trim().isEmpty())
            {
                candidates.add(id.trim());
            }
        }
        if (candidates.isEmpty()) return;

        Collections.shuffle(candidates, random);
        for (String id : candidates)
        {
            IModifier mod = resolveModifier(id, kind);
            if (mod == null)
            {
                warnUnknownModifier(id);
                continue;
            }
            try
            {
                if (mod.canApply(stack, stack))
                {
                    mod.apply(stack);
                    return;
                }
            }
            catch (TinkerGuiException ignored)
            {
                // 冲突或不可用, 试下一个
            }
            catch (Exception e)
            {
                TinkersThings.log("Failed applying bonus modifier " + id + ": " + e.getMessage());
            }
        }
    }

    @Nullable
    private static IModifier resolveModifier(String id, EquipKind kind)
    {
        if (kind == EquipKind.ARMOR && TinkersThings.enableConarm())
        {
            IModifier armorMod = ArmoryRegistry.getArmorModifier(id);
            if (armorMod != null)
            {
                return armorMod;
            }
        }
        return TinkerRegistry.getModifier(id);
    }

    private static void warnUnknownModifier(String id)
    {
        if (WARNED_MODIFIERS.add(id))
        {
            TinkersThings.log("Unknown bonus modifier id, skipped: " + id);
        }
    }
}
