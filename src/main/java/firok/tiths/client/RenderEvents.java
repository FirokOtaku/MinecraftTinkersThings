package firok.tiths.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.TinkersThings;
import firok.tiths.effect.TithsEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TinkersThings.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderEvents
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

//	@SubscribeEvent
//	public static void onFogRender(EntityViewRenderEvent.RenderFogEvent event)
//	{
//		;
//	}

	// todo 把相关逻辑放进TithsEffect里面 这里直接遍历activeEffects然后进行操作
	private static Effect _effectInky;
	private static Effect effInky()
	{
		if(_effectInky == null) _effectInky = TithsEffects.EFFECT_INKY.get();
		return _effectInky;
	}
	private static int effInkyPlayer()
	{
		try
		{
			EffectInstance ei = Minecraft.getInstance().player.getActivePotionEffect(effInky());
			if(ei != null) return ei.getAmplifier();
			else return -1;
		}
		catch (Exception e) { return -1; }
	}

	@SubscribeEvent
	public static void onFogDensity(EntityViewRenderEvent.FogDensity event)
	{
		float density = event.getDensity(); // 不太确定范围代表什么
		final int levelInky = effInkyPlayer();
		if(levelInky >= 0)
		{
			for(int step = 0; step <= levelInky; step++)
			{
				density *= 1.05;
			}
		}

		if(levelInky >= 0)
		{
			event.setDensity(density);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onFogColor(EntityViewRenderEvent.FogColors event)
	{
		float red = event.getRed(), green = event.getGreen(), blue = event.getBlue(); // 范围 0~1
		final int levelInky = effInkyPlayer();
		if(levelInky >= 0)
		{
			for(int step = 0; step <= levelInky; step++)
			{
				red *= 0.7;
				green *= 0.7;
				blue *= 0.7;
			}
		}
		// todo 换成专门的色彩计算 现在的太简陋了
		event.setRed(Math.min(red, 1));
		event.setGreen(Math.min(green, 1));
		event.setBlue(Math.min(blue, 1));
	}
	@SubscribeEvent
	public static void onFOVModifier(EntityViewRenderEvent.FOVModifier event)
	{
		double fov = event.getFOV(); // 单位是度数
		final int levelInky = effInkyPlayer();
		if(levelInky >= 0)
		{
			for(int step = 0; step <= levelInky; step++)
			{
				fov -= 10;
			}
			fov = Math.max(0, fov);
		}
		event.setFOV(fov);
	}

//	@SubscribeEvent
//	public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event)
//	{
//		long time = Minecraft.getInstance().world.getGameTime();
//		float roll = event.getRoll();
//		event.setRoll(time % 40 >= 20 ? roll + 0.1f: roll);
//	}
}
