package firok.tiths.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.TinkersThings;
import firok.tiths.tile.TithsTiles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.mantle.client.ClientEvents;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientRenderEvents
{
	static ItemStack stack;

	@SubscribeEvent
	public static void onRenderPlayerEvent(RenderPlayerEvent event)
	{
		if(stack == null)
		{
			stack = new ItemStack(Items.CHAIN.getItem());
		}

		PlayerEntity player = event.getPlayer();
		long ticks = player.ticksExisted;
		IRenderTypeBuffer buffer = event.getBuffers();

		MatrixStack matrixStack = event.getMatrixStack();
		matrixStack.push();
		try
		{
			matrixStack.translate(0.5, 1.5, 0.5);
			matrixStack.rotate(new Quaternion(0, (float)(ticks % 200. * 360 / 200), 0, true));
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
//			IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, player.world, null);
			itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED,
					15728880,
					OverlayTexture.NO_OVERLAY,
					matrixStack, buffer);
		}
		catch (Exception e)
		{
			;
		}

		matrixStack.pop();
	}

	@SubscribeEvent
	public static void onFogDistance(EntityViewRenderEvent.RenderFogEvent event)
	{
		;
	}

	@SubscribeEvent
	public static void onFogDensity(EntityViewRenderEvent.FogDensity event)
	{
		;
	}

	@SubscribeEvent
	public static void onFogColor(EntityViewRenderEvent.FogColors event)
	{
		;
	}

	@SubscribeEvent
	public static void onFOVModifier(EntityViewRenderEvent.FOVModifier event)
	{
		;
	}

	@SubscribeEvent
	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event)
	{
		;
	}
}
