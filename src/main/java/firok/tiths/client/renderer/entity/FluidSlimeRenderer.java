package firok.tiths.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import firok.tiths.client.renderer.model.FluidSlimeModel;
import firok.tiths.entity.FluidSlimeEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class FluidSlimeRenderer extends MobRenderer<FluidSlimeEntity, FluidSlimeModel>
{
	public FluidSlimeRenderer(EntityRendererManager renderManager)
	{
		super(renderManager, new FluidSlimeModel(16), 0.25F);
		System.out.println("初始化 FluidSlimeRenderer");
//		this.addLayer(new SlimeGelLayer<>(this));
	}

	/**
	 * cache textures for each type of fluids
	 */
	private Map<Fluid, ResourceLocation> cacheFluidTexture = new HashMap<>();

	@Nullable
	private ResourceLocation getTextureOfFluid(Fluid fluid)
	{
		try
		{
			if(cacheFluidTexture.containsKey(fluid)) return cacheFluidTexture.get(fluid);
//			FluidState stateFluid = fluid.getDefaultState();
//			BlockState stateBlock = stateFluid.getBlockState();
//			Minecraft mc = Minecraft.getInstance();
//
//			IBakedModel model = mc.getBlockRendererDispatcher().getModelForState(stateBlock);
////		    IBakedModel model2 = mc.getModelManager().getBlockModelShapes().getModel(stateBlock);

			Stream<RenderMaterial> streamTexture = ForgeHooksClient.getFluidMaterials(fluid);
			RenderMaterial renderMaterial = streamTexture.findAny().orElse(null);
			ResourceLocation rl = renderMaterial == null ? null : renderMaterial.getTextureLocation();
			cacheFluidTexture.put(fluid, rl);
			return rl;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResourceLocation getEntityTexture(FluidSlimeEntity slime)
	{
		return Optional.ofNullable(getTextureOfFluid(slime.getFluid())).orElse(ModelBakery.MODEL_MISSING);
	}


	public void render(FluidSlimeEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		this.shadowSize = 0.25F * (float)entityIn.getSlimeSize();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	protected void preRenderCallback(FluidSlimeEntity entitylivingbaseIn, MatrixStack matrixStackIn, float partialTickTime) {
		float f = 0.999F;
		matrixStackIn.scale(0.999F, 0.999F, 0.999F);
		matrixStackIn.translate(0.0D, (double)0.001F, 0.0D);
		float f1 = (float)entitylivingbaseIn.getSlimeSize();
		float f2 = MathHelper.lerp(partialTickTime, entitylivingbaseIn.prevSquishFactor, entitylivingbaseIn.squishFactor) / (f1 * 0.5F + 1.0F);
		float f3 = 1.0F / (f2 + 1.0F);
		matrixStackIn.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
	}
}
