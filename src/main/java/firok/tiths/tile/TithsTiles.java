package firok.tiths.tile;

import firok.tiths.TithsModule;
import firok.tiths.block.TithsBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class TithsTiles extends TithsModule
{
	static
	{
		System.out.println("static init start");
	}

	public static final
	RegistryObject<TileEntityType<TileMotiaPedestal>> teMotiaPedestal
			= TILE_ENTITIES.register("te_motia_pedestal", TileMotiaPedestal::new, TithsBlocks.BLOCK_MOTIA_PEDESTAL);

	static
	{
		System.out.println("static init end");
	}
}
