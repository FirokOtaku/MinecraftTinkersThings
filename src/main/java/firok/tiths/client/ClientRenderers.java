package firok.tiths.client;

import firok.tiths.tile.TithsTiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRenderers
{
	@SubscribeEvent
	public static void onClientEvent(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegistry.bindTileEntityRenderer(TithsTiles.teMotiaPedestal.get(), RendererMotiaPedestal::new);
		});
	}
}
