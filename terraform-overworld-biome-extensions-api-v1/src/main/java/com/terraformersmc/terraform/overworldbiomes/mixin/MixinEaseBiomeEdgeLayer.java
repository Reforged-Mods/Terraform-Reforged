package com.terraformersmc.terraform.overworldbiomes.mixin;

import java.util.Optional;
import java.util.function.IntConsumer;

import com.terraformersmc.terraform.overworldbiomes.OverworldBiomesExt;
import net.minecraft.world.biome.BuiltinBiomes;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.EaseBiomeEdgeLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;

@Mixin(EaseBiomeEdgeLayer.class)
@SuppressWarnings("unused") // it's a mixin
public class MixinEaseBiomeEdgeLayer {
	@Inject(method = "sample", at = @At("HEAD"), cancellable = true)
	public void terraform_onSample(LayerRandomnessSource rand, int neighbor1, int neighbor2, int neighbor3, int neighbor4, int center, CallbackInfoReturnable<Integer> info) {
		//predicated borders
		RegistryKey<Biome> biome = terraform_fromRawId(center);

		for (OverworldBiomesExt.PredicatedBiomeEntry entry : OverworldBiomesExt.getPredicatedBorders(biome)) {
			if (entry.predicate.test(terraform_fromRawId(neighbor1)) || entry.predicate.test(terraform_fromRawId(neighbor2)) || entry.predicate.test(terraform_fromRawId(neighbor3)) || entry.predicate.test(terraform_fromRawId(neighbor4))) {
				info.setReturnValue(terraform_toRawId(entry.biome));
			}
		}

		//border biomes
		boolean replaced =
				terraform_tryReplace(center, neighbor1, info::setReturnValue) ||
						terraform_tryReplace(center, neighbor2, info::setReturnValue) ||
						terraform_tryReplace(center, neighbor3, info::setReturnValue) ||
						terraform_tryReplace(center, neighbor4, info::setReturnValue);

		if (replaced) {
			return;
		}

		//center biomes
		if (terraform_surrounded(neighbor1, neighbor2, neighbor3, neighbor4, center)) {
			Optional<RegistryKey<Biome>> target = OverworldBiomesExt.getCenter(terraform_fromRawId(center));

			target.ifPresent(value -> info.setReturnValue(terraform_toRawId(value)));
		}
	}

	private static boolean terraform_tryReplace(int center, int neighbor, IntConsumer consumer) {
		if (center == neighbor) {
			return false;
		}

		Optional<RegistryKey<Biome>> border = OverworldBiomesExt.getBorder(terraform_fromRawId(neighbor));
		if (border.isPresent()) {
			consumer.accept(terraform_toRawId(border.get()));

			return true;
		}

		return false;
	}

	private static boolean terraform_surrounded(int neighbor1, int neighbor2, int neighbor3, int neighbor4, int biome) {
		return neighbor1 == biome && neighbor2 == biome && neighbor3 == biome && neighbor4 == biome;
	}

	private static RegistryKey<Biome> terraform_fromRawId(int raw) {
		return BuiltinBiomes.fromRawId(raw);
	}

	private static int terraform_toRawId(RegistryKey<Biome> key) {
		return BuiltinRegistries.BIOME.getRawId(terraform_getOrThrow(key));
	}

	private static Biome terraform_getOrThrow(RegistryKey<Biome> key){
		if (ForgeRegistries.BIOMES.containsKey(key.getValue())){
			return ForgeRegistries.BIOMES.getValue(key.getValue());
		}
		throw new IllegalStateException("Missing: " + key + "from forge Registry, did you register the biome?");
	}
}
