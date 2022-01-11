package firok.tiths.client;

import firok.tiths.client.renderer.entity.FluidSlimeRenderer;
import firok.tiths.client.renderer.tile.RenderAdvancedMotiaPedestal;
import firok.tiths.client.renderer.tile.RendererPedestal;
import firok.tiths.entity.TithsEntities;
import firok.tiths.tile.TithsTiles;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.common.TinkerModule;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientRegistryEvents
{
	@SubscribeEvent
	public static void onClientEvent(FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			ClientRegistry.bindTileEntityRenderer(TithsTiles.tePedestal.get(), RendererPedestal::new);
			ClientRegistry.bindTileEntityRenderer(TithsTiles.teAdvancedMotiaPedestal.get(), RenderAdvancedMotiaPedestal::new);

//			RenderingRegistry.registerEntityRenderingHandler(TithsEntities.etFluidSlime.get(), FluidSlimeRenderer::new);
		});
	}
}
