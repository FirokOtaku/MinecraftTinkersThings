package firok.tiths.material;

import net.minecraftforge.common.crafting.conditions.ICondition;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.json.MaterialJson;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.modifiers.Modifier;

import javax.annotation.Nullable;

public class MaterialInfo
{
    boolean a;

    MaterialId id;
    MetalItemObject metalItemObject;

    int tier;
    int order;
    boolean craftable;
    int color;
    boolean hidden;
    @Nullable ICondition condition;
    MaterialJson.Redirect[] redirect;

    IMaterialStats[] stats;

    int value;
    int needed;

    Modifier modifier;

    public MaterialInfo(MaterialId id, MetalItemObject metalItemObject, int tier, int order, boolean craftable, int color, boolean hidden, @Nullable ICondition condition, MaterialJson.Redirect[] redirect, IMaterialStats[] stats, int value, int needed, Modifier modifier) {
        this(id, metalItemObject, tier, order, craftable, color, stats, value, needed, modifier);
        a = true;

        this.hidden = hidden;
        this.condition = condition;
        this.redirect = redirect;
    }

    public MaterialInfo(MaterialId id, MetalItemObject metalItemObject, int tier, int order, boolean craftable, int color, IMaterialStats[] stats, int value, int needed, Modifier modifier) {
        a = false;

        this.id = id;
        this.metalItemObject = metalItemObject;
        this.tier = tier;
        this.order = order;
        this.craftable = craftable;
        this.color = color;
        this.stats = stats;
        this.value = value;
        this.needed = needed;
        this.modifier = modifier;
    }

    public Modifier getModifier() {
        return modifier;
    }

    @Nullable
    public ICondition getCondition() {
        return condition;
    }

    public IMaterialStats[] getStats() {
        return stats;
    }

    public int getColor() {
        return color;
    }

    public int getNeeded() {
        return needed;
    }

    public int getOrder() {
        return order;
    }

    public int getTier() {
        return tier;
    }

    public int getValue() {
        return value;
    }

    public MaterialId getId() {
        return id;
    }

    public MetalItemObject getMetalItemObject() {
        return metalItemObject;
    }

    public MaterialJson.Redirect[] getRedirect() {
        return redirect;
    }

    public boolean isCraftable() {
        return craftable;
    }

    public boolean isA() {
        return a;
    }

    public boolean isHidden() {
        return hidden;
    }
}
