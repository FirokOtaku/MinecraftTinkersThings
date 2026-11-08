package firok.tiths.recipe.crystallizer;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

/**
 * 结晶器配方注册事件. 在 JSON 资源加载完成后触发, 其它 mod 可在此注册或覆盖配方.
 */
public class CrystallizerRecipeRegisterEvent extends Event
{
    /**
     * 注册或覆盖配方. 相同 id 会覆盖已有配方.
     */
    public void register(ResourceLocation id, CrystallizerRecipe recipe)
    {
        CrystallizerRecipes.register(id, recipe);
    }

    /**
     * 移除指定 id 的配方.
     */
    public void remove(ResourceLocation id)
    {
        CrystallizerRecipes.remove(id);
    }
}
