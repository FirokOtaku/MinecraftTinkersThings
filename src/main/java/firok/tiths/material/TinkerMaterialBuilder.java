package firok.tiths.material;

import net.minecraftforge.common.crafting.conditions.ICondition;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.json.MaterialJson;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.modifiers.Modifier;

import javax.annotation.Nullable;

public class TinkerMaterialBuilder {
    MaterialId id;
    MetalItemObject metalItemObject;

    public TinkerMaterialBuilder(MaterialId id, MetalItemObject metalItemObject) {
        this.id = id;
        this.metalItemObject = metalItemObject;
    }

    boolean material;
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
    public TinkerMaterialBuilder addMaterial(int tier, int order, boolean craftable, int color) {
        material = true;

        this.tier = tier;
        this.order = order;
        this.craftable = craftable;
        this.color = color;

        return this;
    }

    boolean hidden;
    ICondition condition;
    MaterialJson.Redirect[] redirect;

    public TinkerMaterialBuilder addMaterial(int tier, int order, boolean craftable, int color, boolean hidden, @Nullable ICondition condition, MaterialJson.Redirect... redirect) {
        addMaterial(tier, order, craftable, color);

        material = false;

        this.hidden = hidden;
        this.condition = condition;
        this.redirect = redirect;

        return this;
    }

    IMaterialStats[] stats;

    public TinkerMaterialBuilder addMaterialStats(IMaterialStats... stats) {
        this.stats = stats;
        return this;
    }

    int value;
    int needed;

    public TinkerMaterialBuilder addRecipe(int value, int needed) {
        this.value = value;
        this.needed = needed;
        return this;
    }

    Modifier modifier;

    public TinkerMaterialBuilder addModifier(Modifier modifier) {
        this.modifier = modifier;
        return this;
    }

    public TinkerMaterial build() {
        if (material) {
            return new TinkerMaterial(id, metalItemObject, tier, order, craftable, color, stats, value, needed, modifier);
        }
        else {
            return new TinkerMaterial(id, metalItemObject, tier, order, craftable, color, hidden, condition, redirect, stats, value, needed, modifier);
        }
    }
}
