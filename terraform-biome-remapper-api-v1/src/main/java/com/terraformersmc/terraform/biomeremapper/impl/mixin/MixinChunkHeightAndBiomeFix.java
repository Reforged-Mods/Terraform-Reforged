package com.terraformersmc.terraform.biomeremapper.impl.mixin;

import com.terraformersmc.terraform.biomeremapper.impl.fix.BiomeIdFixData;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.datafixer.fix.ChunkHeightAndBiomeFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChunkHeightAndBiomeFix.class)
public class MixinChunkHeightAndBiomeFix {
	@SuppressWarnings("unchecked")
	//@Redirect(method = {"method_38804", "lambda$makeBiomeContainer$24", "m_196596_"}, at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/ints/Int2ObjectMap;getOrDefault(ILjava/lang/Object;)Ljava/lang/Object;"), remap = false)
	private static <V> V terraformBiomeRemapper$readWorldProperties(Int2ObjectMap<V> instance, int rawId, V defaultValue) {
		if (defaultValue instanceof String) {
			var map = BiomeIdFixData.ACTIVE_BIOME_RAW_ID_MAP;
			if (map != null && map.containsKey(rawId)) {
				return (V) map.get(rawId).toString();
			}
		}
		return instance.getOrDefault(rawId, defaultValue);
	}
}
