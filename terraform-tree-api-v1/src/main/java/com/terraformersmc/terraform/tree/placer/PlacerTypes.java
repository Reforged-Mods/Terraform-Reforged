package com.terraformersmc.terraform.tree.placer;

import com.mojang.serialization.Codec;
import com.terraformersmc.terraform.tree.mixin.InvokerTrunkPlacerType;

import net.minecraft.util.Identifier;
import net.minecraft.world.gen.trunk.TrunkPlacer;
import net.minecraft.world.gen.trunk.TrunkPlacerType;

public class PlacerTypes {

	// Deprecated annotation is just a warning for non-internal references
	@SuppressWarnings("deprecation")
	public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(String id, Codec<P> codec) {
		return InvokerTrunkPlacerType.callRegister(id, codec);
	}

	public static <P extends TrunkPlacer> TrunkPlacerType<P> registerTrunkPlacer(Identifier id, Codec<P> codec) {
		return registerTrunkPlacer(id.toString(), codec);
	}
}
