package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.common.Blocks;
import firok.tiths.common.Fluids;
import firok.tiths.common.Items;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import slimeknights.tconstruct.shared.FluidsClientProxy;

import java.lang.reflect.Field;

import static firok.tiths.common.RegistryHandler.mapFluidBlock2Item;

@Mod.EventBusSubscriber(value = Side.CLIENT,modid = TinkersThings.MOD_ID)
public class RendererRegistry
{
//	@SubscribeEvent
//	public static void onClientRender(EntityViewRenderEvent.FogDensity event)
//	{
//		Entity entity=event.getEntity();
//
//		if(entity instanceof EntityPlayer)
//		{
//			float den=event.getDensity();
//			event.setDensity( 0.05f );
//		}
//
//	}
//	@SubscribeEvent
//	public static void onClientRenderFogColor(EntityViewRenderEvent.FogColors event)
//	{
//		event.setBlue(10);
//		event.setGreen(50);
//		event.setRed(10);
//	}
//	@SubscribeEvent
//	public static void onClient(EntityViewRenderEvent.RenderFogEvent event)
//	{
//		;
//	}
	@SubscribeEvent
	public static void registerRenderers(ModelRegistryEvent event)
	{
//		RenderingRegistry.registerEntityRenderingHandler(
//				ProjectileJavelin.class,
//				RendererProjectileJavelin::new
//		);
//		RenderingRegistry.registerEntityRenderingHandler(
//				ProjectileItemPotionTransform.class,
//				RendererProjectileItemPotionTransform::new
//		);

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
//					TinkersThings.log("registered item texture: "+mrl.toString());
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
					ModelResourceLocation mrl=new ModelResourceLocation(item.getRegistryName(),"inventory");
					ModelLoader.setCustomModelResourceLocation(item,0,mrl);
//					TinkersThings.log("registered block texture: "+mrl.toString());
				}
			}
			catch (Exception e)
			{
				TinkersThings.log("error when registering block texture");
				e.printStackTrace();
			}
		}
		// 下面是匠魂的代码 // fixme high !
		for(Field field: Fluids.class.getDeclaredFields())
		{
			try
			{
				Object obj=field.get(null);
				if(obj instanceof Fluid)
				{
					Fluid fluid=(Fluid)obj;
					Block block=fluid.getBlock();
					if(block != null) {
						Item item = mapFluidBlock2Item.get(block);
						FluidsClientProxy.FluidStateMapper mapper = new FluidsClientProxy.FluidStateMapper(fluid);

						// item-model
						if(item != net.minecraft.init.Items.AIR) {
							ModelLoader.registerItemVariants(item);
							ModelLoader.setCustomMeshDefinition(item, mapper);
						}
						// block-model
						ModelLoader.setCustomStateMapper(block, mapper);
					}
				}
			}
			catch (Exception e)
			{
				TinkersThings.log("error when registering fluid texture");
				e.printStackTrace();
			}
		}

	}
}
