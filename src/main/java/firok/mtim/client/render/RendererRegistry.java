package firok.mtim.client.render;

import firok.mtim.MoreTinkersMaterials;
import firok.mtim.client.render.entity.RendererProjectileJavelin;
import firok.mtim.entity.projectile.ProjectileJavelin;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MoreTinkersMaterials.MOD_ID)
public class RendererRegistry
{
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(ProjectileJavelin.class,RendererProjectileJavelin::new);
	}
}
