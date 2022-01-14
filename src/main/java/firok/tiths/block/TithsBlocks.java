package firok.tiths.block;

import firok.tiths.TithsModule;
import firok.tiths.block.logic.IcelitGlowBlock;
import firok.tiths.block.logic.RuneBarrierBlock;
import firok.tiths.block.paving.MoonlightPavingStoneBlock;
import firok.tiths.block.paving.SunlightPavingStoneBlock;
import firok.tiths.block.paving.MotiaPavingStoneBlock;
import firok.tiths.block.pedestal.AdvancedMotiaPedestalBlock;
import firok.tiths.block.pedestal.CreativePedestalBlock;
import firok.tiths.block.pedestal.MotiaPedestalBlock;
import firok.tiths.block.pedestal.TinkerPedestalBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;

import java.util.function.Supplier;

public class TithsBlocks extends TithsModule
{
	public static RegistryObject<IcelitGlowBlock> BLOCK_ICELIT_GLOW = BLOCKS.registerNoItem("icelit_glow", IcelitGlowBlock::new);

	public static ItemObject<MotiaPedestalBlock> MOTIA_PEDESTAL
			= reg("motia_pedestal", MotiaPedestalBlock::new);
	public static ItemObject<CreativePedestalBlock> CREATIVE_PEDESTAL
			= reg("creative_pedestal", CreativePedestalBlock::new);
	public static ItemObject<TinkerPedestalBlock> TINKER_PEDESTAL
			= reg("seared_pedestal", TinkerPedestalBlock::new);
	public static ItemObject<TinkerPedestalBlock> STONE_PEDESTAL
			= reg("stone_pedestal", TinkerPedestalBlock::new);
	public static ItemObject<AdvancedMotiaPedestalBlock> ADVANCED_MOTIA_PEDESTAL
			= reg("advanced_motia_pedestal", AdvancedMotiaPedestalBlock::new);

	public static ItemObject<SunlightPavingStoneBlock> SUNLIGHT_PAVING_STONE
			= reg("sunlight_paving_stone", SunlightPavingStoneBlock::new);
	public static ItemObject<MoonlightPavingStoneBlock> MOONLIGHT_PAVING_STONE
			= reg("moonlight_paving_stone", MoonlightPavingStoneBlock::new);
	public static ItemObject<MotiaPavingStoneBlock> MOTIA_PAVING_STONE
			= reg("motia_paving_stone", MotiaPavingStoneBlock::new);

	public static ItemObject<RuneBarrierBlock> RUNE_BARRIER
			= reg("rune_barrier", RuneBarrierBlock::new);

	private static <T extends Block> ItemObject<T> reg(String name, Supplier<T> supplier)
	{
		return BLOCKS.register(name, supplier, block -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
