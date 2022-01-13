package firok.tiths.tile;

import firok.tiths.TithsModule;
import firok.tiths.block.TithsBlocks;
import firok.tiths.tile.pedestal.TileAdvancedMotiaPedestal;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class TithsTiles extends TithsModule
{
	public static final RegistryObject<TileEntityType<TilePedestalBase>> tePedestal
			= TILE_ENTITIES.register("te_pedestal", TilePedestalBase::new, set -> {
				set.add(TithsBlocks.BLOCK_MOTIA_PEDESTAL.get());
				set.add(TithsBlocks.BLOCK_CREATIVE_PEDESTAL.get());
				set.add(TithsBlocks.BLOCK_TINKER_PEDESTAL.get());
				set.add(TithsBlocks.BLOCK_STONE_PEDESTAL.get());
	});

	public static final RegistryObject<TileEntityType<TileWithEntityType>> teWithEntityType
			= TILE_ENTITIES.register("te_with_entity_type", TileWithEntityType::new, set -> {
				set.add(TithsBlocks.BLOCK_MOTIA_PAVING_STONE.get());
	});

	public static final RegistryObject<TileEntityType<TileAdvancedMotiaPedestal>> teAdvancedMotiaPedestal
			= TILE_ENTITIES.register("te_advanced_motia_pedestal", TileAdvancedMotiaPedestal::new, TithsBlocks.BLOCK_ADVANCED_MOTIA_PEDESTAL);
}
