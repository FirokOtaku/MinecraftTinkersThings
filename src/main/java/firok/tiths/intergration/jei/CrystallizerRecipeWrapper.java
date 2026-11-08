package firok.tiths.intergration.jei;

import firok.tiths.common.Blocks;
import firok.tiths.recipe.crystallizer.CrystallizerRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CrystallizerRecipeWrapper implements IRecipeWrapper
{
    private final CrystallizerRecipe recipe;

    public CrystallizerRecipeWrapper(CrystallizerRecipe recipe)
    {
        this.recipe = recipe;
    }

    public CrystallizerRecipe getRecipe()
    {
        return recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients)
    {
        List<FluidStack> fluids = recipe.getFluidStacks();
        ingredients.setInputs(VanillaTypes.FLUID, fluids);

        List<ItemStack> inputs = new ArrayList<>(1);
        inputs.add(new ItemStack(Blocks.blockCrystallizer));
        ingredients.setInputs(VanillaTypes.ITEM, inputs);

        ingredients.setOutputs(VanillaTypes.ITEM, recipe.copyOutputs());
    }
}
