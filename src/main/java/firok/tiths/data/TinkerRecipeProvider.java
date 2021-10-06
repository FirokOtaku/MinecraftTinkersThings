package firok.tiths.data;

import firok.tiths.MaterialsRegisterHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;
import slimeknights.mantle.recipe.ingredient.IngredientWithout;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.tools.data.ToolsRecipeProvider;

import java.util.function.Consumer;

public class TinkerToolProvider extends ToolsRecipeProvider {
    public TinkerToolProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        addMaterialsRecipes(consumer);
    }

    private void addMaterialsRecipes(Consumer<IFinishedRecipe> consumer) {
        String folder = "tools/materials/";
        materialRecipe(consumer, MaterialProvider.TEST, Ingredient.fromItems(MaterialsRegisterHandler.TEST.getIngot()),
                1, 1, folder + "test");
    }
}
