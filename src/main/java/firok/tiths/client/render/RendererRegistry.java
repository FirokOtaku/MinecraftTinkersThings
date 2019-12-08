package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.client.render.entity.RendererProjectileJavelin;
import firok.tiths.entity.projectile.ProjectileJavelin;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public class RendererRegistry
{
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(ProjectileJavelin.class,RendererProjectileJavelin::new);
	}
}
