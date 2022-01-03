package firok.tiths.tile;

import firok.tiths.TinkersThings;
import firok.tiths.block.BlockMotiaPedestal;
import firok.tiths.block.TithsBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.registration.deferred.TileEntityTypeDeferredRegister;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerModule;
import slimeknights.tconstruct.smeltery.tileentity.component.DuctTileEntity;

public class TithsTiles extends TinkerModule
{
	public static final TileEntityTypeDeferredRegister TILE_ENTITIES
			= new TileEntityTypeDeferredRegister(TinkersThings.MOD_ID);

	public static final RegistryObject<TileEntityType<TileMotiaPedestal>> teMotiaPedestal
			= TILE_ENTITIES.register("te_motia_pedestal", TileMotiaPedestal::new, set -> set.add(TithsBlocks.BLOCK_MOTIA_PEDESTAL.get()));

	static
	{
		System.out.println("static init");
	}
}
