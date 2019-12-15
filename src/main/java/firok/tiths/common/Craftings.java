package firok.tiths.common;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;

import static slimeknights.tconstruct.library.TinkerRegistry.registerBasinCasting;
import static slimeknights.tconstruct.library.TinkerRegistry.registerTableCasting;

@SuppressWarnings("all")
public class Craftings
{
	// 铸造盆合成表
	public static void registerBasinCastings()
	{
		// 恒星黑曜石
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockStellariumObsidian),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.OBSIDIAN)),
						new FluidStack(Fluids.moltenStellarium, Material.VALUE_Ingot*4),
				true, false));
		// 血南瓜
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockBloodPumpkin),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.PUMPKIN)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot*8),
						true, false));
	}
	// 铸造台合成表
	public static void registerTableCastings()
	{
		// 硬化剂
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.hardener),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.DYE,1, EnumDyeColor.WHITE.getDyeDamage())),
						new FluidStack(TinkerFluids.blueslime, Material.VALUE_Ingot*2),
						true, false));
		// 墨染粘液
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.inkySlime),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.DYE,1,EnumDyeColor.BLACK.getDyeDamage())),
						new FluidStack(TinkerFluids.purpleSlime, Material.VALUE_Ingot*2),
						true, false));
	}
	// 无序合成
	public static void registerShapelessCraftings()
	{
		// 哭泣南瓜
	}
	// 有序合成
	public static void registerShapedCraftings()
	{
		;
	}
}
