package firok.tiths;

import firok.tiths.material.MaterialsRegisterHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TinkersThingsItemGroup extends ItemGroup {
    public static final TinkersThingsItemGroup INSTANCE = new TinkersThingsItemGroup();

    public TinkersThingsItemGroup() {
        super("tinkers_things");
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(MaterialsRegisterHandler.TEST.get());
    }
}
