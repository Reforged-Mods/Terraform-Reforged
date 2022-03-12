package com.terraformersmc.terraform.boat.impl.client;

import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class TerraformBoatClientInitializer {

	public void onInitializeClient() {
		EntityRendererRegistry.register(TerraformBoatInitializer.BOAT, TerraformBoatEntityRenderer::new);
	}
}
