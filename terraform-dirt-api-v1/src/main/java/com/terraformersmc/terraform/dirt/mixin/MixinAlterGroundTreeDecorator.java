package com.terraformersmc.terraform.dirt.mixin;

import java.util.Random;

import com.terraformersmc.terraform.dirt.DirtBlocks;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.dirt.TerraformDirtRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.tree.AlterGroundTreeDecorator;

@Mixin(AlterGroundTreeDecorator.class)
public class MixinAlterGroundTreeDecorator {
	// prepareGroundColumn
	@Inject(method = "method_23463(Lnet/minecraft/world/ModifiableTestableWorld;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;)V", at = @At("HEAD"), cancellable = true)
	private void terraform$allowCustomPodzolPlacement(ModifiableTestableWorld world, Random random, BlockPos pos, CallbackInfo callback) {
		for(int i = 2; i >= -3; --i) {
			BlockPos posUp = pos.up(i);

			// Test if there's a custom soil block at this location
			if (world.testBlockState(posUp, state -> state.isIn(TerraformDirtBlockTags.SOIL))) {
				// Try to determine if the soil block is registered, and if so, replace it with custom podzol.
				// Fall back to vanilla podzol if the soil block is unregistered.
				Block podzol = TerraformDirtRegistry.getFromWorld(world, posUp).map(DirtBlocks::getPodzol).orElse(Blocks.PODZOL);

				world.setBlockState(posUp, podzol.getDefaultState(), 18);
				callback.cancel();
				return;
			}

			if (!world.testBlockState(posUp, BlockState::isAir) && i < 0) {
				break;
			}
		}
	}
}
