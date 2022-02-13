package firok.tiths.material;

import net.minecraftforge.common.crafting.conditions.ICondition;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.json.MaterialJson;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.modifiers.Modifier;

import javax.annotation.Nullable;
import java.util.*;

public class TithsMaterialBuilder
{
    private TithsMaterialBuilder() { }

    MaterialId id;
    MetalItemObject metalItemObject;

    public static TithsMaterialBuilder create(MaterialId id, MetalItemObject metalItemObject)
    {
        TithsMaterialBuilder ret = new TithsMaterialBuilder();
        ret.id = id;
        ret.metalItemObject = metalItemObject;
        return ret;
    }

    boolean isCommonMaterial = true;
    int tier;
    int order;
    boolean craftable;
    int color;

    /**
     * @param tier      排序用的
     * @param order     {@link slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider}
     * @param craftable 是否让 Part Builder 可以做它的部件
     * @param color     颜色
     */
    public TithsMaterialBuilder withProperties(int tier, int order, boolean craftable, int color)
    {
        isCommonMaterial = true;

        this.tier = tier;
        this.order = order;
        this.craftable = craftable;
        this.color = color;

        return this;
    }

    boolean hidden;
    ICondition condition;
    MaterialJson.Redirect[] redirect;
    public TithsMaterialBuilder withConditions(
            boolean hidden,
            @Nullable ICondition condition,
            MaterialJson.Redirect... redirect
    )
    {
        isCommonMaterial = false;
        this.hidden = hidden;
        this.condition = condition;
        this.redirect = redirect;
        return this;
    }

    int value, needed;
    public TithsMaterialBuilder withRecipe(int value, int needed)
    {
        this.value = value;
        this.needed = needed;
        return this;
    }

    List<Modifier> modifierDefault = new ArrayList<>(4);
    public TithsMaterialBuilder withDefaultModifiers(Modifier... modifiers)
    {
        if(modifiers != null) Collections.addAll(modifierDefault, modifiers);
        return this;
    }
    public TithsMaterialBuilder withDefaultModifiers(List<Modifier> modifiers)
    {
        if(modifiers != null) modifierDefault.addAll(modifiers);
        return this;
    }

    Map<IMaterialStats, Modifier[]> modifierStat = new HashMap<>();
    public TithsMaterialBuilder withStat(IMaterialStats stat, Modifier... modifiers)
    {
        this.modifierStat.put(stat, modifiers == null ? new Modifier[0] : modifiers);
        return this;
    }

    public MaterialInfo build()
    {
        MaterialInfo ret = new MaterialInfo();
        ret.id = id;
        ret.tier = tier;
        ret.order = order;
        ret.craftable = craftable;
        ret.color = color;
        ret.value = value;
        ret.needed = needed;

        ret.modifierDefault = modifierDefault;
        ret.modifierStat = modifierStat;

        ret.metalItemObject = metalItemObject;
        return ret;
    }
}
