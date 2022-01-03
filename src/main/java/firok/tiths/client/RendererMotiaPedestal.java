package firok.tiths.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.tile.TileMotiaPedestal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.data.EmptyModelData;

public class RendererMotiaPedestal extends TileEntityRenderer<TileMotiaPedestal>
{
	public RendererMotiaPedestal(TileEntityRendererDispatcher rendererDispatcherIn)
	{
		super(rendererDispatcherIn);
	}

	@Override
	public void render(TileMotiaPedestal tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn)
	{
		matrixStackIn.push();
		matrixStackIn.translate(1, 0, 0);
		BlockRendererDispatcher blockRenderer = Minecraft.getInstance().getBlockRendererDispatcher();
		BlockState state = Blocks.CHEST.getDefaultState();
		blockRenderer.renderBlock(state, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, EmptyModelData.INSTANCE);
		matrixStackIn.pop();

		matrixStackIn.push();
		matrixStackIn.translate(0, 1, 0);
		ItemStack stack = tileEntityIn.getStackPhoto();
		if(stack != ItemStack.EMPTY)
		{
			ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
			IBakedModel ibakedmodel = itemRenderer.getItemModelWithOverrides(stack, tileEntityIn.getWorld(), null);
			itemRenderer.renderItem(stack, ItemCameraTransforms.TransformType.FIXED, true, matrixStackIn, bufferIn, combinedLightIn, combinedOverlayIn, ibakedmodel);
		}
		matrixStackIn.pop();
	}
}
