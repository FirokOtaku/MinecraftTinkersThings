package firok.tiths.client.render;

import firok.tiths.TinkersThings;
import firok.tiths.client.render.item.RendererEntityItemSoul;
import firok.tiths.common.Blocks;
import firok.tiths.common.Fluids;
import firok.tiths.common.Items;
import firok.tiths.common.Potions;
import firok.tiths.entity.item.EntityItemSoul;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;

import static firok.tiths.common.Potions.forcibleFocused;
import static net.minecraft.init.MobEffects.SLOWNESS;

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
	public static <T extends EntityLivingBase> void onRenderEntity(RenderLivingEvent.Pre<T> event)
	{
		EntityLivingBase enlb=event.getEntity();
		EntityPlayerSP playerSP=Minecraft.getMinecraft().player;
		if(enlb==null || playerSP==null) return;

		if(enlb.getActivePotionEffect(Potions.bruming)!=null && enlb.getDistanceSqToEntity(playerSP)> 25)
		{
			event.setCanceled(true);
		}
	}

	@SubscribeEvent()
	public static void onRenderGameOverlayEvent(RenderGameOverlayEvent.Pre event)
	{
		if(true) return;
		renderPumpkinOverlay(event.getResolution());
	}

	private static ResourceLocation TEXTURE_LIGHT_RING=new ResourceLocation(TinkersThings.MOD_ID,"misc/light_ring.png");

	protected static void renderPumpkinOverlay(ScaledResolution scaledRes)
	{
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableAlpha();
		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE_LIGHT_RING);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
		bufferbuilder.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
		bufferbuilder.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

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

		RenderingRegistry.registerEntityRenderingHandler(
				EntityItemSoul.class,
				RendererEntityItemSoul::new
		);

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
				TinkersThings.log("field:"+field);
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
				TinkersThings.log("field:"+field);
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
						Item item = Item.getItemFromBlock(block);
						FluidStateMapper mapper = new FluidStateMapper(fluid);

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
				TinkersThings.log("field:"+field);
				e.printStackTrace();
			}
		}

	}

	public static class FluidStateMapper extends StateMapperBase implements ItemMeshDefinition
	{
		public final Fluid fluid;
		public final ModelResourceLocation location;

		public FluidStateMapper(Fluid fluid) {
			this.fluid = fluid;

			// have each block hold its fluid per nbt? hm
			this.location = new ModelResourceLocation(new ResourceLocation(TinkersThings.MOD_ID, "fluid_block"), fluid.getName());
		}

		@Override
		protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
			return location;
		}

		@Override
		public ModelResourceLocation getModelLocation(ItemStack stack) {
			return location;
		}
	}
}
