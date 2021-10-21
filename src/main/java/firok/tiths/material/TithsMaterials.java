package firok.tiths.material;

import firok.tiths.TithsItemGroup;
import firok.tiths.TithsModule;
import firok.tiths.util.DevUse;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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

import static firok.tiths.material.TithsMaterialIds.*;
import static firok.tiths.material.TithsMetalItemObjects.*;
import static firok.tiths.modifier.TithsModifiers.*;

@SuppressWarnings("unused")
public class TithsMaterials extends TithsModule
{
    public static final ArrayList<Supplier<MaterialInfo>> MATERIALS = new ArrayList<>();

    protected static final Item.Properties PROPS = GENERAL_PROPS.group(TithsItemGroup.INSTANCE);
    protected static final Function<Block,? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, PROPS);



    /* === materials === */

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_TEST = devMMI(ID_TEST, ITEM_OBJECT_TEST, MODIFIER_TEST);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_AQUATIC = devMMI(ID_AQUATIC, ITEM_OBJECT_AQUATIC, MODIFIER_AQUATIC);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_BIREFRINGENT = devMMI(ID_BIREFRINGENT, ITEM_BIREFRINGENT, MODIFIER_BIREFRINGENT);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_BLOWING = devMMI(ID_BLOWING, ITEM_BLOWING, MODIFIER_BLOWING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_CARBONIZING = devMMI(ID_CARBONIZING, ITEM_OBJECT_CARBONIZING, MODIFIER_CARBONIZING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_CHAMPING = devMMI(ID_CHAMPING, ITEM_OBJECT_CHAMPING, MODIFIER_CHAMPING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_CHEMICAL_INSTABLE = devMMI(ID_CHEMICAL_INSTABLE, ITEM_OBJECT_CHEMICAL_INSTABLE, MODIFIER_CHEMICAL_INSTABLE);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_CHERISHING = devMMI(ID_CHERISHING, ITEM_OBJECT_CHERISHING, MODIFIER_CHERISHING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_COMBUSTION_SUPPORTING = devMMI(ID_COMBUSTION_SUPPORTING, ITEM_OBJECT_COMBUSTION_SUPPORTING, MODIFIER_COMBUSTION_SUPPORTING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_CREAKY = devMMI(ID_CREAKY, ITEM_OBJECT_CREAKY, MODIFIER_CREAKY);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_DECOYING = devMMI(ID_DECOYING, ITEM_OBJECT_DECOYING, MODIFIER_CREAKY);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_DEGENERATING = devMMI(ID_DEGENERATING, ITEM_OBJECT_DEGENERATING, MODIFIER_DEGENERATING);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_DICHROIC = devMMI(ID_DICHROIC, ITEM_OBJECT_DICHROIC, MODIFIER_DICHROIC);

    @DevUse(isPlaceholder = true)
    public static final Supplier<MaterialInfo> MATERIAL_DRAGON_KILLER = devMMI(ID_DRAGON_KILLER, ITEM_OBJECT_DRAGON_KILLER, MODIFIER_DRAGON_KILLER);


    /* === util methods === */

    private static Supplier<MaterialInfo> register(Supplier<MaterialInfo> material) {
        MATERIALS.add(material);
        return material;
    }

    /**
     * create metal item object
     */
    @DevUse(isPlaceholder = true)
    static MetalItemObject devMetalIO(String name)
    {
        return devMetalIO(name, MaterialColor.ADOBE);
    }
    /**
     * create metal item object
     */
    @DevUse(isPlaceholder = true)
    static MetalItemObject devMetalIO(String name, MaterialColor color)
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
