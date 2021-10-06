package firok.tiths.material;

import firok.tiths.TinkersThings;
import firok.tiths.TinkersThingsItemGroup;
import firok.tiths.modifier.ModifierRegisterHandler;
import firok.tiths.TithsModule;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

public class TithsMaterials extends TithsModule
{
    public static final ArrayList<Supplier<MaterialInfo>> MATERIALS = new ArrayList<>();

    protected static final Item.Properties PROPS = GENERAL_PROPS.group(TinkersThingsItemGroup.INSTANCE);
    protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, PROPS);

    /* === item objects === */

    public static final MetalItemObject TEST = BLOCKS.registerMetal("test",
            metalBuilder(MaterialColor.ADOBE), GENERAL_TOOLTIP_BLOCK_ITEM, PROPS);


    /* === material ids === */

    public static final MaterialId TEST_ID = createMaterial("test");


    /* === materials === */

    public static final Supplier<MaterialInfo> TEST_MATERIAL = register(() -> new TinkerMaterialBuilder(TEST_ID, TEST)
            .addMaterial(1, AbstractMaterialDataProvider.ORDER_GENERAL, true, 0xFFD359)
            .addMaterialStats(
                    new HeadMaterialStats(250, 7.5f, 2, 2f),
                    new HandleMaterialStats(0.9f, 1.15f, 1f, 1f),
                    ExtraMaterialStats.DEFAULT
            )
            .addModifier(ModifierRegisterHandler.TEST.get())
            .addRecipe(1, 1).build());


    /* === util methods === */

    private static MaterialId createMaterial(String name) {
        return new MaterialId(new ResourceLocation(TinkersThings.MOD_ID, name));
    }

    private static Supplier<MaterialInfo> register(Supplier<MaterialInfo> material) {
        MATERIALS.add(material);
        return material;
    }
}
