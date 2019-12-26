package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.client.render.entity.RendererProjectileJavelin;
import firok.tiths.common.Blocks;
import firok.tiths.common.Items;
import firok.tiths.entity.projectile.ProjectileJavelin;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID)
public class RendererRegistry
{
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event)
	{
		RenderingRegistry.registerEntityRenderingHandler(ProjectileJavelin.class,RendererProjectileJavelin::new);

		for(Field field: Items.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Item)
				{
					Item item=(Item)field.get(null);
					ModelResourceLocation mrl=new ModelResourceLocation(item.getRegistryName(),"inventory");
					ModelLoader.setCustomModelResourceLocation(item, 0,mrl);
					TinkersThings.log("registered item texture: "+mrl.toString());
				}
			}
			catch (Exception e)
			{
				TinkersThings.log("error when registering item texture");
				e.printStackTrace();
			}
		}
		for(Field field: Blocks.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Block)
				{
					Block block=(Block)obj;
					Item item=Item.getItemFromBlock(block);
					ModelResourceLocation mrl=new ModelResourceLocation(item.getRegistryName(),"normal");
					ModelLoader.setCustomModelResourceLocation(item,0,mrl);
					TinkersThings.log("registered block texture: "+mrl.toString());
				}
			}
			catch (Exception e)
			{
				TinkersThings.log("error when registering block texture");
				e.printStackTrace();
			}
		}
	}
}
