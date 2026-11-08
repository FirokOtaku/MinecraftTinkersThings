package firok.tiths.intergration.jei;

import firok.tiths.TinkersThings;
import firok.tiths.recipe.crystallizer.CrystallizerRecipe;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiFluidStackGroup;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.List;

public class CrystallizerRecipeCategory implements IRecipeCategory<CrystallizerRecipeWrapper>
{
    public static final String UID = TinkersThings.MOD_ID + ".crystallizer";

    private final IDrawable background;
    private final IDrawable icon;
    private final String title;

    public CrystallizerRecipeCategory(IGuiHelper guiHelper)
    {
        // 宽背景以容纳可变数量的流体槽
        this.background = guiHelper.createBlankDrawable(176, 60);
        this.icon = guiHelper.createDrawableIngredient(new ItemStack(firok.tiths.common.Blocks.blockCrystallizer));
        this.title = I18n.format("jei.tiths.category.crystallizer");
    }

    @Override
    public String getUid()
    {
        return UID;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public String getModName()
    {
        return TinkersThings.MOD_NAME;
    }

    @Override
    public IDrawable getBackground()
    {
        return background;
    }

    @Nullable
    @Override
    public IDrawable getIcon()
    {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, CrystallizerRecipeWrapper recipeWrapper, IIngredients ingredients)
    {
        IGuiFluidStackGroup fluids = recipeLayout.getFluidStacks();
        IGuiItemStackGroup items = recipeLayout.getItemStacks();

        CrystallizerRecipe recipe = recipeWrapper.getRecipe();
        List<FluidStack> fluidInputs = recipe.getFluidStacks();

        int fluidCount = fluidInputs.size();
        for (int i = 0; i < fluidCount; i++)
        {
            int capacity = Math.max(1, fluidInputs.get(i).amount);
            fluids.init(i, true, 8 + i * 22, 14, 16, 32, capacity, true, null);
        }

        int itemX = 8 + Math.max(fluidCount, 1) * 22 + 8;
        items.init(0, true, itemX, 22);

        List<ItemStack> outputs = recipe.copyOutputs();
        for (int i = 0; i < outputs.size(); i++)
        {
            items.init(1 + i, false, itemX + 36 + i * 20, 22);
        }

        fluids.set(ingredients);
        items.set(ingredients);
    }
}
