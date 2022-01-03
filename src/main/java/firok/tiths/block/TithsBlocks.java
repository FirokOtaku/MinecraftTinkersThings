package firok.tiths.block;

import firok.tiths.TithsModule;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;
import slimeknights.mantle.registration.object.ItemObject;

import java.util.function.Supplier;

public class TithsBlocks extends TithsModule
{
	public static RegistryObject<BlockIcelitGlow> BLOCK_ICELIT_GLOW = BLOCKS.registerNoItem("icelit_glow", BlockIcelitGlow::new);

	public static ItemObject<BlockMotiaPedestal> BLOCK_MOTIA_PEDESTAL
			= reg("motia_pedestal", BlockMotiaPedestal::new);

//	private static <T extends Block> ItemObject<T> register(String id, Supplier<T> block)
//	{
//		return BLOCKS.register(id, block, TithsBlocks::getItemBlock);
//	}
//
////	private static final AbstractBlock.Properties propDefault =
////			AbstractBlock.Properties.create(Material.ROCK, MaterialColor.DIRT);
//	private static <T extends Block> BlockItem getItemBlock(Block block)
//	{
//		;
//	}

	private static <T extends Block> ItemObject<T> reg(String name, Supplier<T> supplier)
	{
		return BLOCKS.register(name, supplier, block -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
