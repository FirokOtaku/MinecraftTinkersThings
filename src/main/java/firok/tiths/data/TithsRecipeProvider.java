package firok.tiths.data;

import firok.tiths.material.TithsMaterials;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.tools.data.ToolsRecipeProvider;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class TithsRecipeProvider extends ToolsRecipeProvider {
    public TithsRecipeProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator);
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


}
