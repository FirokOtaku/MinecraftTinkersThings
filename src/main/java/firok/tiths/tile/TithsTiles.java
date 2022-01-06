package firok.tiths.tile;

import firok.tiths.TithsModule;
import firok.tiths.block.TithsBlocks;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class TithsTiles extends TithsModule
{
	public static final RegistryObject<TileEntityType<TilePedestalBase>> tePedestal
			= TILE_ENTITIES.register("te_pedestal", TilePedestalBase::new, TithsBlocks.BLOCK_MOTIA_PEDESTAL);

}
