package firok.tiths.gen;

import firok.tiths.TinkersThings;
import firok.tiths.common.Configs;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * 从 {@link TinkerRegistry} 解析当前品级/上下文可用的材料池.
 */
public final class MaterialPoolResolver
{
    private static final Set<String> WARNED_IDS = new HashSet<>();

    private MaterialPoolResolver() {}

    /**
     * @param includeOwnHidden 本轮是否将本模组隐藏材料纳入候选 (由上下文概率在外层掷骰)
     */
    public static List<Material> resolve(ToolTier tier, GenContext context, boolean includeOwnHidden)
    {
        GenMode mode = tier.getGenMode();
        if (mode == GenMode.CUSTOM)
        {
            return resolveCustom(tier);
        }
        return resolveDefault(tier, includeOwnHidden);
    }

    /** 按上下文概率决定本轮是否纳入本模组隐藏材料, 再解析池. */
    public static List<Material> resolveForRoll(ToolTier tier, GenContext context, Random random)
    {
        boolean includeOwnHidden = random.nextDouble() < context.getHiddenMaterialChance();
        return resolve(tier, context, includeOwnHidden);
    }

    private static List<Material> resolveCustom(ToolTier tier)
    {
        String[] ids = tier.getCustomMaterialIds();
        if (ids == null || ids.length == 0)
        {
            TinkersThings.log("Custom material list for tier " + tier + " is empty");
            return Collections.emptyList();
        }
        List<Material> out = new ArrayList<>();
        for (String id : ids)
        {
            if (id == null || id.trim().isEmpty()) continue;
            String key = id.trim();
            Material mat = TinkerRegistry.getMaterial(key);
            if (mat == null || mat == Material.UNKNOWN)
            {
                warnUnknownMaterial(key);
                continue;
            }
            out.add(mat);
        }
        return out;
    }

    private static List<Material> resolveDefault(ToolTier tier, boolean includeOwnHidden)
    {
        List<Material> out = new ArrayList<>();
        for (Material mat : TinkerRegistry.getAllMaterials())
        {
            if (mat == null || mat == Material.UNKNOWN) continue;
            if (!passesHiddenRules(mat, includeOwnHidden)) continue;
            if (!passesTierHarvestFilter(tier, mat)) continue;
            out.add(mat);
        }
        return out;
    }

    /**
     * 隐藏规则:
     * - 非 hidden: 常驻
     * - 本模组 hidden: 仅当 includeOwnHidden
     * - 外来 hidden: 仅当 allow_foreign_hidden_materials (不建议启用)
     */
    public static boolean passesHiddenRules(Material mat, boolean includeOwnHidden)
    {
        if (!mat.isHidden())
        {
            return true;
        }
        if (HiddenMaterialIds.isOwnHidden(mat))
        {
            return includeOwnHidden;
        }
        return Configs.Gameplay.allow_foreign_hidden_materials;
    }

    /**
     * 有 HEAD 属性时按品级 HL 过滤; 无 HEAD 的材料 (弦/杆等) 作为辅助材料常驻进池.
     */
    public static boolean passesTierHarvestFilter(ToolTier tier, Material mat)
    {
        if (!mat.hasStats(MaterialTypes.HEAD))
        {
            return true;
        }
        HeadMaterialStats head = mat.getStats(MaterialTypes.HEAD);
        if (head == null)
        {
            return true;
        }
        return tier.matchesHeadHarvestLevel(head.harvestLevel);
    }

    private static void warnUnknownMaterial(String id)
    {
        if (WARNED_IDS.add(id))
        {
            TinkersThings.log("Unknown material id in custom gen pool, skipped: " + id);
        }
    }
}
