package com.terraformersmc.terraform.wood.mixin;

import com.google.common.collect.Maps;
import com.terraformersmc.terraform.wood.block.BareSmallLogBlock;
import com.terraformersmc.terraform.wood.block.QuarterLogBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mixin(AxeItem.class)
public class MixinAxeItem {
	@Mutable
	@Shadow
	@Final
	protected static Map<Block, Block> STRIPPED_BLOCKS;

	@Inject(method = "getStrippedState", at = @At("TAIL"), cancellable = true)
	private void terraform$getStrippedState(BlockState oldState, CallbackInfoReturnable<Optional<BlockState>> cir) {
		cir.getReturnValue().ifPresent(newState -> {
			Block newBlock = newState.getBlock();
			// SmallLogBlock extends BareSmallLogBlock
			if (newBlock instanceof BareSmallLogBlock || newBlock instanceof QuarterLogBlock) {
				cir.setReturnValue(Optional.of(newBlock.getStateWithProperties(oldState)));
			}
		});
	}

	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void terraform$injectStrippableMapSet(CallbackInfo ci){
		STRIPPED_BLOCKS = Maps.newHashMap(STRIPPED_BLOCKS);
	}
}
