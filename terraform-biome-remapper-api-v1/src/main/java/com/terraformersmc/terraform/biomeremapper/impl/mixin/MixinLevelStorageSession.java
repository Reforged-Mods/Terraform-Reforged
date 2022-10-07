package com.terraformersmc.terraform.biomeremapper.impl.mixin;

import com.terraformersmc.terraform.biomeremapper.impl.BiomeRemappings;
import com.terraformersmc.terraform.biomeremapper.impl.fix.BiomeIdFixData;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.util.Identifier;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

@Mixin(LevelStorage.Session.class)
public class MixinLevelStorageSession {
	@Shadow
	@Final
	private Path directory;

	@Unique
	private boolean terraformBiomeRemapper$readIdMapFile(File file) throws IOException {
		BiomeRemappings.LOGGER.debug("Reading registry data from " + file.toString());

		if (file.exists()) {
			FileInputStream fileInputStream = new FileInputStream(file);
			NbtCompound nbt = NbtIo.readCompressed(fileInputStream);
			fileInputStream.close();

			if (nbt != null) {
				BiomeIdFixData.applyFabricDynamicRegistryMap(fromNbt(nbt));
				return true;
			}
		}

		return false;
	}

	public static Map<Identifier, Object2IntMap<Identifier>> fromNbt(NbtCompound nbt) {
		NbtCompound mainNbt = nbt.getCompound("registries");
		Map<Identifier, Object2IntMap<Identifier>> map = new LinkedHashMap<>();

		for (String registryId : mainNbt.getKeys()) {
			Object2IntMap<Identifier> idMap = new Object2IntLinkedOpenHashMap<>();
			NbtCompound idNbt = mainNbt.getCompound(registryId);

			for (String id : idNbt.getKeys()) {
				idMap.put(new Identifier(id), idNbt.getInt(id));
			}

			map.put(new Identifier(registryId), idMap);
		}

		return map;
	}

	@Inject(method = "readLevelProperties", at = @At("HEAD"))
	public void terraformBiomeRemapper$readWorldProperties(CallbackInfoReturnable<SaveProperties> callbackInfo) {
		try {
			if (terraformBiomeRemapper$readIdMapFile(new File(new File(directory.toFile(), "data"), "fabricDynamicRegistry.dat"))) {
				BiomeRemappings.LOGGER.info("[Registry Sync Fix] Loaded registry data");
			}
		} catch (FileNotFoundException e) {
			// Pass
		} catch (IOException e) {
			BiomeRemappings.LOGGER.warn("[Registry Sync Fix] Reading registry file failed!", e);
		}
	}
}
