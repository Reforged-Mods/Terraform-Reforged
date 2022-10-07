package com.terraformersmc.terraform.biomeremapper.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.terraformersmc.terraform.biomeremapper.api.BiomeRemapperApi;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.forgespi.language.IModInfo;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.util.TriConsumer;
import org.objectweb.asm.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BiomeRemappings {
	public static final Hashtable<String, RemappingRecord> BIOME_REMAPPING_REGISTRY = new Hashtable<>(8);
	public static final String MOD_ID = "terraform-biome-remapper";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static void invokeEndpoints() {
		BiomeRemappings.<com.terraformersmc.terraform.biomeremapper.api.BiomeRemapper, BiomeRemapperApi>scanAnnotation(com.terraformersmc.terraform.biomeremapper.api.BiomeRemapper.class, BiomeRemapperApi.class::isAssignableFrom, (modid, plugin, clazz) -> plugin.get().init());
	}

	public static <A, T> void scanAnnotation(Class<A> clazz, Predicate<Class<T>> predicate, TriConsumer<List<String>, Supplier<T>, Class<T>> consumer) {
		scanAnnotation(Type.getType(clazz), predicate, consumer);
	}

	public static <T> void scanAnnotation(Type annotationType, Predicate<Class<T>> predicate, TriConsumer<List<String>, Supplier<T>, Class<T>> consumer) {
		List<Triple<List<String>, Supplier<T>, Class<T>>> instances = Lists.newArrayList();
		for (ModFileScanData data : ModList.get().getAllScanData()) {
			List<String> modIds = data.getIModInfoData().stream()
					.flatMap(info -> info.getMods().stream())
					.map(IModInfo::getModId)
					.collect(Collectors.toList());
			out:
			for (ModFileScanData.AnnotationData annotation : data.getAnnotations()) {
				Object value = annotation.annotationData().get("value");
				boolean enabled;

				if (value instanceof Dist[]) {
					enabled = Arrays.asList((Dist[]) value).contains(FMLEnvironment.dist);
				} else {
					enabled = true;
				}

				if (enabled && annotationType.equals(annotation.annotationType())) {
					try {
						Class<T> clazz = (Class<T>) Class.forName(annotation.memberName());
						if (predicate.test(clazz)) {
							instances.add(new ImmutableTriple<>(modIds, () -> {
								try {
									return clazz.getDeclaredConstructor().newInstance();
								} catch (Throwable throwable) {
									LOGGER.error("Failed to load remapper: " + annotation.memberName(), throwable);
									return null;
								}
							}, clazz));
						}
					} catch (Throwable throwable) {
						LOGGER.error("Failed to load remapper: " + annotation.memberName(), throwable);
					}
				}
			}
		}

		for (Triple<List<String>, Supplier<T>, Class<T>> pair : instances) {
			consumer.accept(pair.getLeft(), pair.getMiddle(), pair.getRight());
		}
	}

	public static void register(String modId, int dataVersion, ImmutableMap<String, String> remapping) {
		String key = dataVersion + "_" + modId;
		if (BIOME_REMAPPING_REGISTRY.containsKey(key)) {
			LOGGER.debug("Ignored duplicate remapping: " + key);
		} else {
			LOGGER.debug("Added remapping: " + key);
			BIOME_REMAPPING_REGISTRY.put(key, new RemappingRecord(modId, dataVersion, remapping));
		}
	}

	public record RemappingRecord(String modId, int dataVersion, ImmutableMap<String, String> remapping) {}
}
