package firok.tiths.common;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerFluids;

import static slimeknights.tconstruct.library.TinkerRegistry.registerBasinCasting;
import static slimeknights.tconstruct.library.TinkerRegistry.registerTableCasting;

@SuppressWarnings("all")
public class Craftings
{
	// 注册所有合成表
	public static void registerAllCraftings()
	{
		registerBasinCastings();
		registerTableCastings();
		registerShapelessCraftings();
		registerShapedCraftings();
	}

	// 铸造盆合成表
	private static void registerBasinCastings()
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
	private static void registerTableCastings()
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
		// 岩浆膏
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(net.minecraft.init.Items.MAGMA_CREAM),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.BLAZE_POWDER)),
						new FluidStack(TinkerFluids.purpleSlime, Material.VALUE_Ingot*1),
						true, false));
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(net.minecraft.init.Items.MAGMA_CREAM),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.BLAZE_POWDER)),
						new FluidStack(TinkerFluids.blueslime, Material.VALUE_Ingot*1),
						true, false));
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(net.minecraft.init.Items.MAGMA_CREAM),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.BLAZE_POWDER)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot*1),
						true, false));

		// 皇家纸张
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.royalPaper),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.PAPER)),
						new FluidStack(Fluids.moltenRoyalAlloy, Material.VALUE_Nugget*1),
						true, false));
	}
	// 无序合成
	private static void registerShapelessCraftings()
	{
		// 哭泣南瓜
	}
	// 有序合成
	private static void registerShapedCraftings()
	{
		;
	}
}
