package com.terraformersmc.terraform.leaves.mixin;

import com.terraformersmc.terraform.leaves.block.ExtendedLeavesBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.OptionalInt;

@Mixin(LeavesBlock.class)
public class MixinLeavesBlock {
	@Inject(method = "getOptionalDistanceFromLog", at = @At("HEAD"), cancellable = true)
	private static void terraform_wood$injectExtendedLeavesCheck(BlockState state, CallbackInfoReturnable<OptionalInt> cir){
		if (state.contains(ExtendedLeavesBlock.DISTANCE) && state.getBlock() instanceof ExtendedLeavesBlock) {
			cir.setReturnValue(OptionalInt.of(state.get(ExtendedLeavesBlock.DISTANCE)));
		}
	}
}
