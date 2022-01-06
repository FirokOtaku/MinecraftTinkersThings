package firok.tiths.client;

import firok.tiths.client.renderer.tile.RendererPedestal;
import firok.tiths.tile.TithsTiles;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistryEvents
{
	@SubscribeEvent
	public static void onClientEvent(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegistry.bindTileEntityRenderer(TithsTiles.tePedestal.get(), RendererPedestal::getInstance);
		});
	}

}
