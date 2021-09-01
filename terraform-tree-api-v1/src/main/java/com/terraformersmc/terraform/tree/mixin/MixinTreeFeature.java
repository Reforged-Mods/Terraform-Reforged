package com.terraformersmc.terraform.tree.mixin;

import com.terraformersmc.terraform.tree.feature.ExtendedTreeGeneration;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.TestableWorld;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TreeFeature.class)
public abstract class MixinTreeFeature<FC extends FeatureConfig> extends Feature<FC> {
	@Shadow
	protected abstract int method_29963(TestableWorld testableWorld, int i, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig);

	@Shadow
	private static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
		throw new AssertionError();
	}

	// Bypasses the "no default constructor" error, will never actually get used
	@SuppressWarnings("ConstantConditions")
	private MixinTreeFeature() {
		super(null);
		throw new UnsupportedOperationException();
	}

	// TODO: Not sure if the redirect is better or worse here
	@Redirect(method = "generate(Lnet/minecraft/world/ModifiableTestableWorld;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/util/math/BlockBox;Lnet/minecraft/world/gen/feature/TreeFeatureConfig;)Z", at = @At(value = "INVOKE", target = "net/minecraft/world/gen/feature/TreeFeature.isDirtOrGrass (Lnet/minecraft/world/TestableWorld;Lnet/minecraft/util/math/BlockPos;)Z"))
	private boolean terrestria$allowSandyTreeGeneration(TestableWorld world, BlockPos pos) {
		if ((this instanceof ExtendedTreeGeneration)) {
			return ((ExtendedTreeGeneration) this).canGenerateOn(world, pos);
		} else {
			return isDirtOrGrass(world, pos);
		}
	}
}
