package firok.tiths.client.renderer.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.tile.pedestal.TileAdvancedMotiaPedestal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Quaternion;

public class RenderAdvancedMotiaPedestal extends TileEntityRenderer<TileAdvancedMotiaPedestal>
{
	public RenderAdvancedMotiaPedestal(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileAdvancedMotiaPedestal te, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		Entity entity = te.getEntity();
		if(entity == null) return;

		matrixStackIn.push();
		matrixStackIn.translate(0.5D, 0.0D, 0.5D);
		long now = te.getWorld().getGameTime();
		matrixStackIn.scale(0.5f, 0.5f, 0.5f);
		matrixStackIn.rotate(new Quaternion(0, (float)(now % 400. * 360 / 400), 0, true));
		Minecraft.getInstance().getRenderManager().renderEntityStatic(entity,
				0.0D, entity.getHeight() + 1, 0.0D, 0.0F,
				0, matrixStackIn, bufferIn, combinedLightIn);

		matrixStackIn.pop();
	}
}
