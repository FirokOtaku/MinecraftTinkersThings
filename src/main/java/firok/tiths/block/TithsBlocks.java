package firok.tiths.block;

import firok.tiths.TithsModule;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import slimeknights.mantle.registration.object.ItemObject;

import java.util.function.Supplier;

public class TithsBlocks extends TithsModule
{
	public static RegistryObject<BlockIcelitGlow> BLOCK_ICELIT_GLOW = BLOCKS.registerNoItem("icelit_glow", BlockIcelitGlow::new);

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
}
