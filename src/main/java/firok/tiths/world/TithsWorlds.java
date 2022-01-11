package firok.tiths.world;

import firok.tiths.TinkersThings;
import firok.tiths.TithsModule;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.ConfiguredPlacement;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = TinkersThings.MOD_ID)
public class TithsWorlds extends TithsModule
{
	public static final OreType genIronBlock = new OreType(Lazy.of(()->Blocks.IRON_BLOCK),12,80,0, 90);
//	public static final RegistryObject<Structure<NoFeatureConfig>> grottoOfUnderworld;
//	public static StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> GROTTO_OF_UNDERWORLD;
//	static
//	{
//		grottoOfUnderworld = STRUCTURE_FEATURES.register("grotto_of_underworld", EarthSlimeIslandStructure::new);
//		GROTTO_OF_UNDERWORLD = (StructureFeature) WorldGenRegistries.register(WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE, resource("blood_slime_island"), ((Structure)bloodSlimeIsland.get()).withConfiguration(NoFeatureConfig.INSTANCE));
//	}

	/**
	 * @see Features mc自带feature列表
	 */
	@SubscribeEvent
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event)
	{
		OreFeatureConfig config = new OreFeatureConfig(
				OreFeatureConfig.FillerBlockType.BASE_STONE_OVERWORLD,
				genIronBlock.block.get().getDefaultState(),
				genIronBlock.maxAmount
		);

		ConfiguredPlacement<TopSolidRangeConfig> configuredPlacement = Placement.RANGE.configure(
				new TopSolidRangeConfig(genIronBlock.bottomOffset, genIronBlock.topOffset, genIronBlock.topMaximum)
		);
	}

	private static ConfiguredFeature<?, ?> registerFeatureForOre(OreType oreType, OreFeatureConfig config, ConfiguredPlacement placement)
	{
		// todo
//		return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,   )
	}
}
