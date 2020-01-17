package firok.tiths.common;

import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import static slimeknights.tconstruct.library.TinkerRegistry.registerBasinCasting;
import static slimeknights.tconstruct.library.TinkerRegistry.registerTableCasting;

@SuppressWarnings("all")
public class Craftings
{
	// 注册所有合成表
	public static void registerAllCraftings()
	{
		registerBindings();
		registerBasinCastings();
		registerTableCastings();
	}

	// 原矿 矿块 矿锭 矿粒 之间的合成关系
	private static void registerBindings()
	{
		createBinding(null,Blocks.blockStellarium,Fluids.moltenStellarium,Items.ingotStellarium,Items.nuggetStellarium);
		createBinding(null,Blocks.blockRoyalAlloy,Fluids.moltenRoyalAlloy,Items.ingotRoyalAlloy,Items.nuggetRoyalAlloy);
		createBinding(Blocks.oreImmersedSilver,Blocks.blockImmersedSilver,Fluids.moltenImmersedSilver,Items.ingotImmersedSilver,Items.nuggetImmersedSilver);
		createBinding(Blocks.oreMithril,Blocks.blockMithril,Fluids.moltenMithril,Items.ingotMithril,Items.nuggetMithril);
		createBinding(Blocks.oreAdamantine,Blocks.blockAdamantine,Fluids.moltenAdamantine,Items.ingotAdamantine,Items.nuggetAdamantine);
		createBinding(Blocks.oreInertWitherium,Blocks.blockInertWitherium,Fluids.moltenInertWitherium,Items.ingotInertWitherium,Items.nuggetInertWitherium);
		createBinding(Blocks.oreWitherium,Blocks.blockWitherium,Fluids.moltenWitherium,Items.ingotWitherium,Items.nuggetWitherium);
		createBinding(Blocks.oreTitanium,Blocks.blockTitanium,Fluids.moltenTitanium,Items.ingotTitanium,Items.nuggetTitanium);
		createBinding(Blocks.orePolarium,Blocks.blockPolarium,Fluids.moltenPolarium,Items.ingotPolarium,Items.nuggetPolarium);
		createBinding(Blocks.oreHalleium,Blocks.blockHalleium,Fluids.moltenHalleium,Items.ingotHalleium,Items.nuggetHalleium);
		createBinding(Blocks.oreAltairium,Blocks.blockAltairium,Fluids.moltenAltairium,Items.ingotAltairium,Items.nuggetAltairium);
		createBinding(Blocks.oreHothium,Blocks.blockHothium,Fluids.moltenHothium,Items.ingotHothium,Items.nuggetHothium);
		createBinding(Blocks.oreStellarium,Blocks.blockStellarium,Fluids.moltenStellarium,Items.ingotStellarium,Items.nuggetStellarium);
		createBinding(null,Blocks.blockCocoa,Fluids.moltenCocoa,Items.ingotCocoa,Items.nuggetCocoa);
		createBinding(null,Blocks.blockIrisia,Fluids.moltenIrisia,Items.ingotIrisia,Items.nuggetIrisia);

		TinkerRegistry.registerMelting(
				new ItemStack(net.minecraft.init.Items.DYE,1,EnumDyeColor.BROWN.getDyeDamage()),
				Fluids.moltenCocoa,
				Material.VALUE_Nugget
		);
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
//		// 血南瓜
//		registerBasinCasting(
//				new CastingRecipe(
//						new ItemStack(Blocks.blockBloodPumpkin),
//						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.PUMPKIN)),
//						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot*8),
//						true, false));
		// 血沙
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockBloodSand),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.SOUL_SAND)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot * 4),
						true, false));
		// 凝结血沙
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockCoagulatedBloodSand),
						RecipeMatch.of(new ItemStack(Blocks.blockBloodSand)),
						new FluidStack(TinkerFluids.glass, Material.VALUE_Block),
						true, false));
		// 强化玻璃
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockConsolidatedGlass),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.IRON_BARS)),
						new FluidStack(TinkerFluids.glass, Material.VALUE_Block),
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
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.polisher),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.DYE,1, EnumDyeColor.WHITE.getDyeDamage())),
						new FluidStack(TinkerFluids.purpleSlime, Material.VALUE_Ingot*2),
						true, false));
		// 墨染粘液
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.inkySlime),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.DYE,1,EnumDyeColor.BLACK.getDyeDamage())),
						new FluidStack(TinkerFluids.purpleSlime, Material.VALUE_Ingot*2),
						true, false));
		// 末影之石
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.enderGem),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.ENDER_PEARL)),
						new FluidStack(TinkerFluids.emerald, Material.VALUE_Ingot*1),
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

		// 奢华纸张
//		registerTableCasting(
//				new CastingRecipe(
//						new ItemStack(Items.royalPaper),
//						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.PAPER)),
//						new FluidStack(Fluids.moltenRoyalAlloy, Material.VALUE_Nugget*1),
//						true, false));

		// 灼热面包
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.hotBread),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.BREAD)),
						new FluidStack(FluidRegistry.LAVA, Material.VALUE_Nugget*1),
						true, false));
		// 灼热鱼
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.hotFish),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.FISH)),
						new FluidStack(FluidRegistry.LAVA, Material.VALUE_Nugget*1),
						true, false));
	}

	private static void createBinding(
			Block ore,Block block,Fluid fluid,
			Item ingot,Item nugget
	){
		createBinding(ore,block,fluid,
				ingot,nugget,
				1,2,
				true,true);
	}
	/**
	 * 注册材料不同状态之间的合成表
	 * @param ore 矿石方块
	 * @param block 矿块
	 * @param fluid 液体
	 * @param ingot 锭物品
	 * @param nugget 粒物品
	 * @param amountSmeltOre2Ingot 直接冶炼矿石能得到的锭数量 如果小于等于0则不注册合成表
	 * @param amountMetlOre2Fluid 通过匠魂焦黑熔炉熔化矿石得到的液体数量(几锭mb) 如果小于等于0则不注册合成表
	 * @param enableMeltOther2Fluid 是否允许锭粒和液体之间的合成
	 * @param enableFluid2Other 是否允许液体和锭粒之间的合成
	 */
	private static void createBinding(
			Block ore,Block block,Fluid fluid,
			Item ingot,Item nugget,
			int amountSmeltOre2Ingot,
			int amountMetlOre2Fluid,
			boolean enableMeltOther2Fluid,
			boolean enableFluid2Other
	){
		// 矿 -> 锭 冶炼
		if(amountSmeltOre2Ingot>0 && ore!=null && ingot!=null)
			GameRegistry.addSmelting(ore,new ItemStack(ingot,amountSmeltOre2Ingot,0),4);

		// 矿 -> 熔融液体
		if(amountMetlOre2Fluid>0 && ore!=null && fluid!=null)
			TinkerRegistry.registerMelting(ore,fluid,Material.VALUE_Ingot * amountMetlOre2Fluid);

		// 块 -> 熔融液体
		if(enableMeltOther2Fluid && block!=null && fluid!=null)
			TinkerRegistry.registerMelting(block,fluid,Material.VALUE_Block);
		// 锭 -> 熔融液体
		if(enableMeltOther2Fluid && ingot!=null && fluid!=null)
			TinkerRegistry.registerMelting(ingot,fluid,Material.VALUE_Ingot);
		// 粒 -> 熔融液体
		if(enableMeltOther2Fluid && nugget!=null && fluid!=null)
			TinkerRegistry.registerMelting(nugget,fluid,Material.VALUE_Nugget);
		// 熔融液体 -> 块
		if(enableFluid2Other && block!=null && fluid!=null)
			registerBasinCasting(
					new CastingRecipe(
							new ItemStack(block),
							null,
							new FluidStack(fluid, Material.VALUE_Block),
							false, false));
		// 熔融液体 -> 锭
		if(enableFluid2Other && ingot!=null && fluid!=null)
			TinkerRegistry.registerTableCasting(new CastingRecipe(
					new ItemStack(ingot),
					RecipeMatch.of(TinkerSmeltery.castIngot), // fixme
					new FluidStack(fluid, Material.VALUE_Ingot * 1),
					false, false));
		// 熔融液体 -> 粒
		if(enableFluid2Other && nugget!=null && fluid!=null)
			TinkerRegistry.registerTableCasting(new CastingRecipe(
					new ItemStack(nugget),
					RecipeMatch.of(TinkerSmeltery.castNugget), // fixme
					new FluidStack(fluid, Material.VALUE_Nugget * 1),
					false, false));
	}

	// 下面这个以后可能用到
	// TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(pattern), sharpeningKit));
}
