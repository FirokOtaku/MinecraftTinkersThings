package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.client.render.entity.RendererProjectileJavelin;
import firok.tiths.common.Blocks;
import firok.tiths.common.Items;
import firok.tiths.entity.projectile.ProjectileJavelin;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
				if(obj instanceof Item && !(obj instanceof ItemBlock))
				{
					Item item=(Item)field.get(null);
					ModelLoader.setCustomModelResourceLocation(
							item, 0,
							new ModelResourceLocation(item.getRegistryName(),"inventory")
					);
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
					ModelLoader.setCustomModelResourceLocation(
							item,0,
							new ModelResourceLocation(item.getRegistryName(),"inventory")
					);
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
