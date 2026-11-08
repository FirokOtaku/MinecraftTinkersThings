package firok.tiths.intergration.jei;

import firok.tiths.TinkersThings;
import firok.tiths.common.Blocks;
import firok.tiths.recipe.crystallizer.CrystallizerRecipe;
import firok.tiths.recipe.crystallizer.CrystallizerRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

@JEIPlugin
public class TithsJEIPlugin implements IModPlugin
{
    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        registry.addRecipeCategories(new CrystallizerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry)
    {
        if (!CrystallizerRecipes.isLoaded())
        {
            CrystallizerRecipes.reload();
        }

        registry.handleRecipes(CrystallizerRecipeWrapper.class, recipe -> recipe, CrystallizerRecipeCategory.UID);

        List<CrystallizerRecipeWrapper> wrappers = new ArrayList<>();
        for (CrystallizerRecipe recipe : CrystallizerRecipes.getAll())
        {
            if (!recipe.getFluidStacks().isEmpty())
            {
                wrappers.add(new CrystallizerRecipeWrapper(recipe));
            }
        }
        registry.addRecipes(wrappers, CrystallizerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(Blocks.blockCrystallizer), CrystallizerRecipeCategory.UID);
        TinkersThings.log("JEI: crystallizer recipes registered (" + wrappers.size() + ")");
    }
}
