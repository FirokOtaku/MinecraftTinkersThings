package firok.tiths.common;

import firok.tiths.TinkersThings;
import net.minecraft.block.Block;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.HeadMaterialStats;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.materials.MaterialTypes;
import slimeknights.tconstruct.library.smeltery.CastingRecipe;
import slimeknights.tconstruct.library.utils.HarvestLevels;
import slimeknights.tconstruct.shared.TinkerCommons;
import slimeknights.tconstruct.shared.TinkerFluids;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;
import slimeknights.tconstruct.tools.TinkerTools;

import java.util.function.Function;
import java.util.function.Predicate;

import static firok.tiths.common.Keys.*;
import static net.minecraftforge.fml.common.registry.GameRegistry.addSmelting;
import static slimeknights.tconstruct.library.TinkerRegistry.registerBasinCasting;
import static slimeknights.tconstruct.library.TinkerRegistry.registerTableCasting;

@SuppressWarnings("all")
public final class Craftings
{
	private Craftings() {}

	private static boolean hasRegistered=false;

	private static ItemStack[] stacksSharpeningKitAll = null;
	private static ItemStack[] stacksSharpeningKitGreaterIron = null;

	private static ItemStack[] getStacksOfPart(String materialType, Predicate<Material> preMaterial, Function<Material,ItemStack> stackMapper)
	{
		return TinkerRegistry.getAllMaterialsWithStats(materialType)
				.stream()
				.filter(preMaterial)
				.map(stackMapper)
				.toArray(ItemStack[]::new);
	}

	// 注册所有合成表
	public static void registerAllCraftings()
	{
		if(hasRegistered) throw new RuntimeException("duplicated registering crafting");
		hasRegistered=true;

		stacksSharpeningKitAll = getStacksOfPart(
				MaterialTypes.HEAD,
				m->!Material.UNKNOWN.equals(m),
				TinkerTools.sharpeningKit::getItemstackWithMaterial
		);
		stacksSharpeningKitGreaterIron = getStacksOfPart(
				MaterialTypes.HEAD,
				m-> ((HeadMaterialStats)m.getStats(MaterialTypes.HEAD)).harvestLevel >= HarvestLevels.IRON,
				TinkerTools.sharpeningKit::getItemstackWithMaterial
		);

		registerCustom();
		registerBindings();
		registerBasinCastings();
		registerTableCastings();
		registerFuels();
		registerSmelting();
	}

	private static void registerCustom()
	{
		// 任意磨制工具 + 煤 = 煤屑
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(TinkersThings.MOD_ID,"special_cinder"),
				(ResourceLocation) null,
				new ItemStack(Items.cinder,8),
				Ingredient.fromItem(net.minecraft.init.Items.COAL),
				Ingredient.fromStacks(stacksSharpeningKitAll)
		);
		// 任意磨制工具 + 黑石 = 黑石粉
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(TinkersThings.MOD_ID,"special_dust_blackrock"),
				(ResourceLocation) null,
				new ItemStack(Items.dustBlackrock,1),
				Ingredient.fromItem(Items.blackrock),
				Ingredient.fromStacks(stacksSharpeningKitAll)
		);
		// 任意磨制工具 + 石头 = 石粉
		GameRegistry.addShapelessRecipe(
				new ResourceLocation(TinkersThings.MOD_ID,"special_dust_stone"),
				(ResourceLocation) null,
				new ItemStack(Items.dustStone,1),
				Ingredient.fromItems(Item.getItemFromBlock(net.minecraft.init.Blocks.STONE),Item.getItemFromBlock(net.minecraft.init.Blocks.COBBLESTONE)),
				Ingredient.fromStacks(stacksSharpeningKitAll)

		);
	}

	// 燃料
	private static void registerFuels()
	{
		GameRegistry.registerFuelHandler(itemstack->{
			Item item=itemstack.getItem();
			if(item==Items.cinder)
			{
				return fuelTimeCinder;
			}
			else if(item==Items.lavaCrystal)
			{
				return fuelTimeLavaCrystal;
			}
			else if(item==Items.treeRoot)
			{
				return fuelTimeTreeRoot;
			}
			return 0;
		});
	}

	// 原矿 矿块 矿锭 矿粒 之间的合成关系
	private static void registerBindings()
	{
		createBinding(Blocks.oreStellarium,Blocks.blockStellarium,Fluids.moltenStellarium,Items.ingotStellarium,Items.nuggetStellarium);
		createBinding(null,Blocks.blockRoyalAlloy,Fluids.moltenRoyalAlloy,Items.ingotRoyalAlloy,Items.nuggetRoyalAlloy);
		createBinding(Blocks.oreImmersedSilver,Blocks.blockImmersedSilver,Fluids.moltenImmersedSilver,Items.ingotImmersedSilver,Items.nuggetImmersedSilver);
		createBinding(null,Blocks.blockSolita,Fluids.moltenSolita,Items.ingotSolita,Items.nuggetSolita);
		createBinding(null,Blocks.blockMagiga,Fluids.moltenMagiga,Items.ingotMagiga,Items.nuggetMagiga);
		createBinding(Blocks.oreInertWitherium,Blocks.blockInertWitherium,Fluids.moltenInertWitherium,Items.ingotInertWitherium,Items.nuggetInertWitherium);
		createBinding(Blocks.oreWitherium,Blocks.blockWitherium,Fluids.moltenWitherium,Items.ingotWitherium,Items.nuggetWitherium);
		createBinding(Blocks.oreTitanium,Blocks.blockTitanium,Fluids.moltenTitanium,Items.ingotTitanium,Items.nuggetTitanium);
		createBinding(Blocks.orePolarium,Blocks.blockPolarium,Fluids.moltenPolarium,Items.ingotPolarium,Items.nuggetPolarium);
		createBinding(Blocks.oreHalleium,Blocks.blockHalleium,Fluids.moltenHalleium,Items.ingotHalleium,Items.nuggetHalleium);
		createBinding(Blocks.oreAltairium,Blocks.blockAltairium,Fluids.moltenAltairium,Items.ingotAltairium,Items.nuggetAltairium);
		createBinding(Blocks.oreHothium,Blocks.blockHothium,Fluids.moltenHothium,Items.ingotHothium,Items.nuggetHothium);
		createBinding(Blocks.oreStellarium,Blocks.blockStellarium,Fluids.moltenStellarium,Items.ingotStellarium,Items.nuggetStellarium);
		createBinding(Blocks.oreTonium,Blocks.blockTonium,Fluids.moltenTonium,Items.ingotTonium,Items.nuggetTonium);
		createBinding(null,Blocks.blockCocoa,Fluids.moltenCocoa,Items.ingotCocoa,Items.nuggetCocoa);
		createBinding(null,Blocks.blockIrisia,Fluids.moltenIrisia,Items.ingotIrisia,Items.nuggetIrisia);
		createBinding(Blocks.oreSteamium,Blocks.blockSteamium,Fluids.moltenSteamium,Items.ingotSteamium,Items.nuggetSteamium);
		createBinding(Blocks.oreChloroplast,Blocks.blockChloroplast,Fluids.moltenChloroplast,Items.ingotChloroplast,Items.nuggetChloroplast);
		createBinding(null,Blocks.blockTanatonium,Fluids.moltenTanatonium,Items.ingotTanatonium,Items.nuggetTanatonium);

		TinkerRegistry.registerMelting(
				new ItemStack(Blocks.oreTanatonium),
				Fluids.moltenTanatonium,
				Material.VALUE_Nugget
		);
		addSmelting(Blocks.oreTanatonium,new ItemStack(Items.nuggetTanatonium),8);

		TinkerRegistry.registerMelting(
				new ItemStack(net.minecraft.init.Items.DYE,1,EnumDyeColor.BROWN.getDyeDamage()),
				Fluids.moltenCocoa,
				Material.VALUE_Nugget
		);
		TinkerRegistry.registerMelting(
				new ItemStack(Items.enderCreviceShard),
				Fluids.moltenEnderTurbulence,
				Material.VALUE_Ingot
		);
		TinkerRegistry.registerMelting(
				new ItemStack(Blocks.blockCloud),
				FluidRegistry.WATER,
				100
		);

		TinkerRegistry.registerMelting(
				new ItemStack(Items.brokenIce),
				FluidRegistry.WATER,
				250
		);

		TinkerRegistry.registerMelting(
				new ItemStack(Blocks.oreSolidDirt),
				TinkerFluids.dirt,
				Material.VALUE_Block * 8
		);
		TinkerRegistry.registerMelting(
				new ItemStack(Blocks.oreSolidSand),
				TinkerFluids.glass,
				8000
		);
		TinkerRegistry.registerMelting(
				new ItemStack(Blocks.oreSolidStone),
				TinkerFluids.searedStone,
				Material.VALUE_Block * 8
		);

		// 液体球
//		TinkerRegistry.registerMelting(
//				new ItemStack(Items.fluidBall),
//				TinkerFluids.blueslime,
//				1000
//		);
//		TinkerRegistry.registerMelting(
//				new MeltingRecipe(new RecipeMatch.Item(new ItemStack(Items.fluidBall),1),
//				new FluidStack(TinkerFluids.blueslime,1000))
//		);
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
		// 血腥石英块
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockBloodyQuartz),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.QUARTZ_BLOCK,1,0)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot),
						true, false));
		// 錾制血腥石英块
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockBloodyChiseledQuartz),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.QUARTZ_BLOCK,1,1)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot),
						true, false));
		// 竖纹血腥石英块
		registerBasinCasting(
				new CastingRecipe(
						new ItemStack(Blocks.blockBloodyPillarQuartz),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Blocks.QUARTZ_BLOCK,1,2)),
						new FluidStack(TinkerFluids.blood, Material.VALUE_Ingot),
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
//		registerTableCasting(
//				new CastingRecipe(
//						new ItemStack(Items.enderGem),
//						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.ENDER_PEARL)),
//						new FluidStack(TinkerFluids.emerald, Material.VALUE_Ingot*1),
//						true, false));
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
		// 凋零锭
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.ingotWitherium),
						RecipeMatch.of(new ItemStack(Items.ingotInertWitherium)),
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

		// 碎冰
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.brokenIce),
						RecipeMatch.of(new ItemStack(Items.nitre)),
						new FluidStack(FluidRegistry.WATER, 250),
						true, false));

		// 叶绿粉末
//		registerTableCasting(
//				new CastingRecipe(
//						new ItemStack(Items.dustChloroplast),
//						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.SUGAR)),
//						new FluidStack(Fluids.moltenChloroplast,Material.VALUE_Nugget*1),
//						true,false));
		// 叶绿敷料
		registerTableCasting(
				new CastingRecipe(
						new ItemStack(Items.chloroplastDressing),
						RecipeMatch.of(new ItemStack(net.minecraft.init.Items.CLAY_BALL)),
						new FluidStack(Fluids.moltenChloroplast, Material.VALUE_Ingot * 2),
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
			addSmelting(ore,new ItemStack(ingot,amountSmeltOre2Ingot,0),4);

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

	private static void registerSmelting()
	{
		addSmelting(new ItemStack(Items.shell), new ItemStack(Items.shellCooked), 1);
		addSmelting(new ItemStack(Items.flesh), new ItemStack(Items.fleshCooked), 5);
		addSmelting(new ItemStack(Items.dustStoneCoalMixed), new ItemStack(TinkerCommons.materials,1,0), 1);

	}

	// 下面这个以后可能用到
	// TinkerRegistry.registerStencilTableCrafting(Pattern.setTagForPart(new ItemStack(pattern), sharpeningKit));
	// 这个可能是发射器用的
	// BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY
}
