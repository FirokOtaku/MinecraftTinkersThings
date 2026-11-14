package firok.tiths.gen;

import firok.tiths.TinkersThings;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * 本模组隐藏材料 ID 集合. 内容定稿后由注册逻辑写入;
 * 运行期也可通过 {@link TinkerRegistry#getTrace} 判断材料是否归属本模组.
 */
public final class HiddenMaterialIds
{
    private static final Set<String> OWN_HIDDEN = new HashSet<>();

    private HiddenMaterialIds() {}

    public static void register(String materialId)
    {
        if (materialId != null && !materialId.isEmpty())
        {
            OWN_HIDDEN.add(materialId);
        }
    }

    public static void clear()
    {
        OWN_HIDDEN.clear();
    }

    public static Set<String> view()
    {
        return Collections.unmodifiableSet(OWN_HIDDEN);
    }

    public static boolean isOwnHidden(Material material)
    {
        if (material == null || !material.isHidden())
        {
            return false;
        }
        if (OWN_HIDDEN.contains(material.identifier))
        {
            return true;
        }
        ModContainer trace = TinkerRegistry.getTrace(material);
        return trace != null && TinkersThings.MOD_ID.equals(trace.getModId());
    }

    public static boolean isForeignHidden(Material material)
    {
        return material != null && material.isHidden() && !isOwnHidden(material);
    }
}
