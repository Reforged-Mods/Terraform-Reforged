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
		matrixStackIn.multiply(Vector3f.YP.rotationDegrees(180.0F - entityYaw));
		float f = (float) entityIn.getHurtTime() - partialTicks;
		float f1 = entityIn.getDamage() - partialTicks;
		if (f1 < 0.0F) {
			f1 = 0.0F;
		}

		if (f > 0.0F) {
			matrixStackIn.multiply(Vector3f.XP.rotationDegrees(MathHelper.sin(f) * f * f1 / 10.0F * (float) entityIn.getHurtDir()));
		}

		float f2 = entityIn.getBubbleAngle(partialTicks);
		if (!MathHelper.equal(f2, 0.0F)) {
			matrixStackIn.multiply(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), entityIn.getBubbleAngle(partialTicks), true));
		}

		matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
		matrixStackIn.multiply(Vector3f.YP.rotationDegrees(90.0F));
		this.modelBoat.setupAnim(entityIn, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
		VertexConsumer ivertexbuilder = bufferIn.getBuffer(this.modelBoat.renderType(this.getTextureLocation(entityIn)));
		this.modelBoat.renderToBuffer(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
		VertexConsumer ivertexbuilder1 = bufferIn.getBuffer(RenderLayer.getWaterMask());
		this.modelBoat.waterPatch().render(matrixStackIn, ivertexbuilder1, packedLightIn, OverlayTexture.NO_OVERLAY);
		matrixStackIn.pop();
		super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
	}

	@Override
	public Identifier getTexture(TerraformBoatEntity boat) {
		return boat.getBoatSkin();
	}
}
