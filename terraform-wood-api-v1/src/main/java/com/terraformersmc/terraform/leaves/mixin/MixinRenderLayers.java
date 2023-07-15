package com.terraformersmc.terraform.leaves.mixin;

import com.terraformersmc.terraform.wood.block.SmallLogBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;

import com.terraformersmc.terraform.leaves.block.ExtendedLeavesBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayers.class)
@OnlyIn(Dist.CLIENT)
public class MixinRenderLayers {
	@Shadow
	private static boolean fancyGraphicsOrBetter;

	@Inject(method = "getBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void onGetBlockRenderLayer(BlockState state, CallbackInfoReturnable<RenderLayer> info) {
		if (state.getBlock() instanceof ExtendedLeavesBlock || (state.getBlock() instanceof SmallLogBlock && state.get(SmallLogBlock.HAS_LEAVES))) {
			info.setReturnValue(fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid());
		}
	}

	@Inject(method = "getMovingBlockLayer", at = @At("HEAD"), cancellable = true)
	private static void onGetMovingBlockRenderLayer(BlockState state, CallbackInfoReturnable<RenderLayer> info) {
		if (state.getBlock() instanceof ExtendedLeavesBlock || (state.getBlock() instanceof SmallLogBlock && state.get(SmallLogBlock.HAS_LEAVES))) {
			info.setReturnValue(fancyGraphicsOrBetter ? RenderLayer.getCutoutMipped() : RenderLayer.getSolid());
		}
	}
}
