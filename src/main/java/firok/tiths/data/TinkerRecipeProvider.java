package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import firok.tiths.TinkersThings;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.common.registration.MetalItemObject;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.tools.data.ToolsRecipeProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TinkerRecipeProvider extends ToolsRecipeProvider {
    public TinkerRecipeProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator);
        generator.addProvider(new MaterialProvider(generator));
        generator.addProvider(new TinkerBlockTagProvider(generator, existingFileHelper));
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        addMaterialsRecipes(consumer);
    }

    private void addMaterialsRecipes(Consumer<IFinishedRecipe> consumer) {
        String folder = "tools/materials/";
        // materialRecipe(consumer, MaterialProvider.TEST, Ingredient.fromItems(MaterialsRegisterHandler.TEST.getIngot()),
        //         1, 1, folder + "test");
        TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
            materialRecipe(consumer, material.getId(), Ingredient.fromItems(material.getMetalItemObject().getIngot()),
                    material.getValue(), material.getNeeded(), folder + material.getId().getPath());
        });
    }



    public static class MaterialProvider extends AbstractMaterialDataProvider {
        public MaterialProvider(DataGenerator gen) {
            super(gen);
            gen.addProvider(new MaterialStatsProvider(gen, this));
            generator.addProvider(new TinkerTraitDataProvider(generator, this));
        }

        @Override
        protected void addMaterials() {
            // addMaterial(TEST, 3, ORDER_GENERAL, true, 0xFFD359);
            TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
                if (material.isA()) {
                    addMaterial(material.getId(), material.getTier(), material.getOrder(), material.isCraftable(), material.getColor());
                }
                else {
                    addMaterial(material.getId(), material.getTier(), material.getOrder(), material.isCraftable(), material.getColor(),
                            material.isHidden(), material.getCondition(), material.getRedirect());
                }
            });
        }

        public static class MaterialStatsProvider extends AbstractMaterialStatsDataProvider {
            public MaterialStatsProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
                super(gen, materials);
            }

            @Override
            protected void addMaterialStats() {
                // addMaterialStats(TEST,
                //         new HeadMaterialStats(250, 7.5f, 2, 2f),
                //         new HandleMaterialStats(0.9f, 1.15f, 1f, 1f),
                //         ExtraMaterialStats.DEFAULT
                // );

                TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
                    addMaterialStats(material.getId(), material.getStats());
                });
            }

            @Override
            public String getName() {
                return "Material Stats Provider";
            }
        }

        public static class TinkerTraitDataProvider extends AbstractMaterialTraitDataProvider {
            public TinkerTraitDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
                super(gen, materials);
            }

            @Override
            protected void addMaterialTraits() {
                TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
                    addDefaultTraits(material.getId(), material.getModifier());
                });
            }

            @Override
            public String getName() {
                return "Trait Data Provider";
            }
        }

//        private static MaterialId createMaterial(String name) {
//            return new MaterialId(new ResourceLocation(TinkersThings.MOD_ID, name));
//        }

        @Override
        public String getName() {
            return "Material Provider";
        }
    }

    public static class TinkerBlockTagProvider extends BlockTagProvider {
        public TinkerBlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
            super(generatorIn, existingFileHelper);
            generatorIn.addProvider(new TinkerItemTagProvider(generatorIn, this, existingFileHelper));
        }

        @Override
        protected void registerTags() {
            TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
                addMetalTags(material.getMetalItemObject());
            });
            // addMetalTags(MaterialsRegisterHandler.TEST);
        }

        private void addMetalTags(MetalItemObject metal) {
            this.getOrCreateBuilder(metal.getBlockTag()).add(metal.get());
            this.getOrCreateBuilder(BlockTags.BEACON_BASE_BLOCKS).addTag(metal.getBlockTag());
            this.getOrCreateBuilder(Tags.Blocks.STORAGE_BLOCKS).addTag(metal.getBlockTag());
        }

        public static class TinkerItemTagProvider extends ItemTagProvider {
            public TinkerItemTagProvider(DataGenerator generatorIn, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
                super(generatorIn, blockTagProvider, existingFileHelper);
            }

            @Override
            protected void registerTags() {
                TithsMaterials.MATERIALS.stream().map(Supplier::get).forEach(material -> {
                    addMetalTags(material.getMetalItemObject());
                });
                // addMetalTags(MaterialsRegisterHandler.TEST);
            }

            private void addMetalTags(MetalItemObject metal) {
                this.getOrCreateBuilder(metal.getIngotTag()).add(metal.getIngot());
                this.getOrCreateBuilder(Tags.Items.INGOTS).addTag(metal.getIngotTag());
                this.getOrCreateBuilder(metal.getNuggetTag()).add(metal.getNugget());
                this.getOrCreateBuilder(Tags.Items.NUGGETS).addTag(metal.getNuggetTag());
                this.copy(metal.getBlockTag(), metal.getBlockItemTag());
            }
        }
    }
}
