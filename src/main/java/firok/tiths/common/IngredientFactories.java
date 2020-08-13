package firok.tiths.common;

import com.google.gson.JsonObject;
import firok.tiths.util.ToolRodIngredient;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.IIngredientFactory;
import net.minecraftforge.common.crafting.JsonContext;
import slimeknights.tconstruct.library.materials.Material;

@SuppressWarnings("all")
public class IngredientFactories
{
	public static class ToolRodIngredientFactory implements IIngredientFactory
	{
		@Override
		public Ingredient parse(JsonContext context, JsonObject json) {
			return new ToolRodIngredient(json.has("material")? json.get("material").getAsString() : Material.UNKNOWN.identifier );
		}
	}
}
