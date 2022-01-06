package firok.tiths.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.tile.pedestal.TilePedestalBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * this renderer would render an extra item above of block itself. <br>
 * renderer takes stack data from {@code TilePedestalBase} instance
 */
public class RendererPedestal extends TileEntityRenderer<TilePedestalBase>
{
	private static RendererPedestal instance;
	public static RendererPedestal getInstance(TileEntityRendererDispatcher dispatcher)
	{
		if(instance == null)
		{
			instance = new RendererPedestal(dispatcher);
		}
		return instance;
	}

	public RendererPedestal(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TilePedestalBase tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		final long ticks = Optional.ofNullable(tileEntityIn.getWorld()).map(World::getGameTime).orElse(0L);


		ItemStack stack = tileEntityIn.getStackPedestal();
		if(stack != ItemStack.EMPTY)
		{
			matrixStackIn.push();
			matrixStackIn.translate(0.5, 1.5, 0.5);
			matrixStackIn.rotate(new Quaternion(0, (float)(ticks % 200. * 360 / 200), 0, true));
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
			itemRenderer.renderItem(
					stack, ItemCameraTransforms.TransformType.FIXED,
					true, matrixStackIn, bufferIn,
					15728880, combinedOverlayIn, ibakedmodel
			);
			matrixStackIn.pop();
		}
	}
}
