package firok.tiths.material;

import firok.tiths.TinkersThings;
import firok.tiths.TithsItemGroup;
import firok.tiths.modifier.TithsModifiers;
import firok.tiths.TithsModule;
import firok.tiths.util.DevUse;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class TithsMaterials extends TithsModule
{
    public static final ArrayList<Supplier<MaterialInfo>> MATERIALS = new ArrayList<>();

    protected static final Item.Properties PROPS = GENERAL_PROPS.group(TithsItemGroup.INSTANCE);
    protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, PROPS);

    /* === item objects === */

    @DevUse(isPlaceholder = true)
    public static final MetalItemObject ITEM_OBJECT_TEST = devMetalIO("test");

    @DevUse(isPlaceholder = true)
    public static final MetalItemObject ITEM_OBJECT_AQUATIC = devMetalIO("test_aquatic");


    /* === material ids === */

    @DevUse(isPlaceholder = true)
    public static final MaterialId ID_TEST = createMaterial("test");

    @DevUse(isPlaceholder = true)
    public static final MaterialId ID_AQUATIC = createMaterial("aquatic");


    /* === materials === */

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_TEST = devMMI(ID_TEST, ITEM_OBJECT_TEST, TithsModifiers.MODIFIER_TEST);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_AQUATIC = devMMI(ID_AQUATIC, ITEM_OBJECT_AQUATIC, TithsModifiers.MODIFIER_AQUATIC);


    /* === util methods === */

    private static MaterialId createMaterial(String name) {
        return new MaterialId(new ResourceLocation(TinkersThings.MOD_ID, name));
    }

    private static Supplier<MaterialInfo> register(Supplier<MaterialInfo> material) {
        MATERIALS.add(material);
        return material;
    }

    /**
     * create metal item object
     */
    @DevUse(isPlaceholder = true)
    private static MetalItemObject devMetalIO(String name)
    {
        return devMetalIO(name, MaterialColor.ADOBE);
    }
    /**
     * create metal item object
     */
    @DevUse(isPlaceholder = true)
    private static MetalItemObject devMetalIO(String name, MaterialColor color)
    {
        return BLOCKS.registerMetal(name, metalBuilder(color), GENERAL_TOOLTIP_BLOCK_ITEM, PROPS);
    }

    /**
     * create metal material info
     */
    @SuppressWarnings("unchecked")
    @DevUse(isPlaceholder = true)
    private static Supplier<MaterialInfo> devMMI(MaterialId id, MetalItemObject mio, RegistryObject<? extends Modifier>... modifiers)
    {
        return register(() -> {
            TinkerMaterialBuilder ret = new TinkerMaterialBuilder(id, mio)
                    .addMaterial(1, AbstractMaterialDataProvider.ORDER_GENERAL, true, 0xFFD359)
                    .addMaterialStats(
                            new HeadMaterialStats(250, 7.5f, 2, 2f),
                            new HandleMaterialStats(0.9f, 1.15f, 1f, 1f),
                            ExtraMaterialStats.DEFAULT
                    )
                    .addRecipe(1, 1);

            if(modifiers != null && modifiers.length > 0)
            {
                for (RegistryObject<? extends Modifier> modifier : modifiers)
                {
                    if(modifier == null) continue;

                    ret.addModifier(modifier.get());
                }
            }

            return ret.build();
        });
    }
}
