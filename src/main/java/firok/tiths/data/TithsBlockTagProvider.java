package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.data.tags.BlockTagProvider;
import slimeknights.tconstruct.common.data.tags.ItemTagProvider;
import slimeknights.tconstruct.common.registration.MetalItemObject;

import java.util.function.Supplier;

public class TithsBlockTagProvider extends BlockTagProvider {
    public TithsBlockTagProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
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