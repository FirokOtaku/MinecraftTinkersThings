package firok.tiths;

import firok.tiths.material.TinkerMaterial;
import firok.tiths.material.TinkerMaterialBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.ArrayList;
import java.util.function.Function;

public class MaterialsRegisterHandler extends TinkerModule {
    public static final ArrayList<TinkerMaterial> MATERIALS = new ArrayList<>();

    protected static final Item.Properties PROPS = GENERAL_PROPS.group(TinkersThingsItemGroup.INSTANCE);
    protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, PROPS);

    public static final MetalItemObject TEST = BLOCKS.registerMetal("test",
            metalBuilder(MaterialColor.ADOBE), GENERAL_TOOLTIP_BLOCK_ITEM, PROPS);

    public static final MaterialId TEST_ID = createMaterial("test");

    public static final TinkerMaterial TEST_MATERIAL = register(new TinkerMaterialBuilder(TEST_ID, TEST)
            .addMaterial(1, AbstractMaterialDataProvider.ORDER_GENERAL, true, 0xFFD359)
            .addMaterialStats(
                    new HeadMaterialStats(250, 7.5f, 2, 2f),
                    new HandleMaterialStats(0.9f, 1.15f, 1f, 1f),
                    ExtraMaterialStats.DEFAULT
            )
            .addRecipe(1, 1).build());

    private static MaterialId createMaterial(String name) {
        return new MaterialId(new ResourceLocation(TinkersThings.MOD_ID, name));
    }

    private static TinkerMaterial register(TinkerMaterial material) {
        MATERIALS.add(material);
        return material;
    }
}
