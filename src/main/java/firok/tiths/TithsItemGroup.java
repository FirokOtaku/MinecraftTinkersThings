package firok.tiths;

import firok.tiths.material.TithsMaterials;
import firok.tiths.util.DevUse;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * todo we don't keep a ItemGroup and all stuffs would be
 *   added into ItemGroup of Tinkers' Construct.
 */
@DevUse(isPlaceholder = true)
public class TithsItemGroup extends ItemGroup {
    public static final TithsItemGroup INSTANCE = new TithsItemGroup();

    public TithsItemGroup() {
        super("tinkers_things");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(TithsMaterials.ITEM_OBJECT_TEST.get());
    }
}
