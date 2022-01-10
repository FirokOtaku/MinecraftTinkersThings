package firok.tiths.client.renderer.model;

import com.google.common.collect.ImmutableList;
import firok.tiths.entity.FluidSlimeEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class FluidSlimeModel extends SegmentedModel<FluidSlimeEntity>
{

	private final ModelRenderer slimeBodies;
	private final ModelRenderer slimeRightEye;
	private final ModelRenderer slimeLeftEye;
	private final ModelRenderer slimeMouth;

	public FluidSlimeModel(int slimeBodyTexOffY) {
		this.slimeBodies = new ModelRenderer(this, 0, slimeBodyTexOffY);
		this.slimeRightEye = new ModelRenderer(this, 32, 0);
		this.slimeLeftEye = new ModelRenderer(this, 32, 4);
		this.slimeMouth = new ModelRenderer(this, 32, 8);
		if (slimeBodyTexOffY > 0) {
			this.slimeBodies.addBox(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
			this.slimeRightEye.addBox(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.slimeLeftEye.addBox(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
			this.slimeMouth.addBox(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
		} else {
			this.slimeBodies.addBox(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
		}

	}

	/**
	 * Sets this entity's model rotation angles
	 */
	public void setRotationAngles(FluidSlimeEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	public Iterable<ModelRenderer> getParts() {
		return ImmutableList.of(this.slimeBodies, this.slimeRightEye, this.slimeLeftEye, this.slimeMouth);
	}
}
