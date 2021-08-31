package com.terraformersmc.terraform.boat;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;

public class TerraformBoatRenderer extends EntityRenderer<TerraformBoatEntity> {
	protected final BoatEntityModel modelBoat = new BoatEntityModel();
	public TerraformBoatRenderer(EntityRenderDispatcher renderDispatcher) {
		super(renderDispatcher);
	}

	@Override
	public void render(TerraformBoatEntity entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, VertexConsumerProvider bufferIn, int packedLightIn) {
		matrixStackIn.push();
		matrixStackIn.translate(0.0D, 0.375D, 0.0D);
		matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(180.0F - entityYaw));
		float f = (float) entityIn.getDamageWobbleTicks() - partialTicks;
		float f1 = entityIn.getDamageWobbleStrength() - partialTicks;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f > 0.0F) {
			matrixStackIn.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(MathHelper.sin(f) * f * f1 / 10.0F * (float) entityIn.getDamageWobbleSide()));
		}

		float f2 = entityIn.interpolateBubbleWobble(partialTicks);
		if (!MathHelper.approximatelyEquals(f2, 0.0F)) {
			matrixStackIn.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.interpolateBubbleWobble(partialTicks), true));
		}

		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		matrixStackIn.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
		this.modelBoat.setAngles(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.modelBoat.getLayer(this.getTexture(entityIn)));
		this.modelBoat.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderLayer.getWaterMask());
		this.modelBoat.getBottom().render(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.DEFAULT_UV);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public Identifier getTexture(TerraformBoatEntity boat) {
		return boat.getBoatSkin();
	}
}
