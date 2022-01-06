package firok.tiths.block;

import firok.tiths.TithsModule;
import firok.tiths.block.pedestal.BlockCreativePedestal;
import firok.tiths.block.pedestal.BlockMotiaPedestal;
import firok.tiths.block.pedestal.BlockTinkerPedestal;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;

import java.util.function.Supplier;

public class TithsBlocks extends TithsModule
{
	public static RegistryObject<BlockIcelitGlow> BLOCK_ICELIT_GLOW = BLOCKS.registerNoItem("icelit_glow", BlockIcelitGlow::new);

	public static ItemObject<BlockMotiaPedestal> BLOCK_MOTIA_PEDESTAL
			= reg("motia_pedestal", BlockMotiaPedestal::new);
	public static ItemObject<BlockCreativePedestal> BLOCK_CREATIVE_PEDESTAL
			= reg("creative_pedestal", BlockCreativePedestal::new);
	public static ItemObject<BlockTinkerPedestal> BLOCK_TINKER_PEDESTAL
			= reg("seared_pedestal", BlockTinkerPedestal::new);
	public static ItemObject<BlockTinkerPedestal> BLOCK_STONE_PEDESTAL
			= reg("stone_pedestal", BlockTinkerPedestal::new);

	private static <T extends Block> ItemObject<T> reg(String name, Supplier<T> supplier)
	{
		return BLOCKS.register(name, supplier, block -> new BlockItem(block, new Item.Properties().group(ItemGroup.BUILDING_BLOCKS)));
	}
}
