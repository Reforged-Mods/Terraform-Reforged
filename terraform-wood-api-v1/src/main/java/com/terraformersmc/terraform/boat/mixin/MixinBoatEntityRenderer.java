package com.terraformersmc.terraform.boat.mixin;

import com.terraformersmc.terraform.boat.TerraformBoatEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Mixin(BoatEntityRenderer.class)
@OnlyIn(Dist.CLIENT)
public class MixinBoatEntityRenderer {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    private void getModelTexture(BoatEntity boat, CallbackInfoReturnable<Identifier> info) {
		if(boat instanceof TerraformBoatEntity) {
			info.setReturnValue(((TerraformBoatEntity) boat).getBoatSkin());
		}
    }
}
