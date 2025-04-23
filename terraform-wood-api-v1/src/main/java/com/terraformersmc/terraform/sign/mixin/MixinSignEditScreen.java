package com.terraformersmc.terraform.sign.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.terraformersmc.terraform.sign.TerraformSign;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.SignEditScreen;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SignEditScreen.class)
@OnlyIn(Dist.CLIENT)
public class MixinSignEditScreen {
	@WrapOperation(
			method = "renderSignBackground",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getSignTextureId(Lnet/minecraft/block/WoodType;)Lnet/minecraft/client/util/SpriteIdentifier;")
	)
	@SuppressWarnings("unused")
	private SpriteIdentifier getTerraformSignTextureId(WoodType type, Operation<SpriteIdentifier> original, DrawContext drawContext, BlockState state) {
		if (state.getBlock() instanceof TerraformSign signBlock) {
			return new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, signBlock.getTexture());
		}

		return original.call(type);
	}
}
