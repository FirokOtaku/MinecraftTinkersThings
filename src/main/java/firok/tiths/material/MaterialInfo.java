package firok.tiths.material;

import lombok.Data;
import net.minecraftforge.common.crafting.conditions.ICondition;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.json.MaterialJson;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.modifiers.Modifier;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

@Data
public class MaterialInfo
{
    /**
     * 是否为普通材料 (非隐藏, 无条件注册)
     */
    boolean direct;

    /**
     * 材料id
     */
    MaterialId id;

    MetalItemObject metalItemObject;

    /**
     * 材料等级
     */
    int tier;

    /**
     * 材料分类
     * @see AbstractMaterialDataProvider
     */
    int order;
    boolean craftable;
    int color;
    boolean hidden;
    @Nullable ICondition condition;
    MaterialJson.Redirect[] redirect;

    int value;
    int needed;
    List<Modifier> modifierDefault;
    Map<IMaterialStats, Modifier[]> modifierStat;
}
