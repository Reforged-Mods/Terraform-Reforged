package com.terraformersmc.terraform.boat.api;

import com.mojang.serialization.Lifecycle;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

/**
 * @see TerraformBoatTypeRegistry#INSTANCE
 */
public class TerraformBoatTypeRegistry {
	private static final Identifier REGISTRY_ID = new Identifier("terraform", "boat");
	private static final RegistryKey<Registry<TerraformBoatType>> REGISTRY_KEY = RegistryKey.ofRegistry(REGISTRY_ID);

	/**
	 * The registry for {@linkplain TerraformBoatType Terraform boats}.
	 * 
	 * 
	 * <p>To register a boat type:
	 * 
	 * <pre>{@code
	 *     Registry.register(TerraformBoatType.REGISTRY, new Identifier("examplemod", "mahogany"), boat);
	 * }</pre>
	 * 
	 * @see com.terraformersmc.terraform.boat.api.TerraformBoatType.Builder The builder for boat types
	 * @see com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper Helpers for registering the boat on the client
	 */
	public static final Registry<TerraformBoatType> INSTANCE = new SimpleRegistry<>(REGISTRY_KEY, Lifecycle.stable(), null);

	public static RegistryKey<TerraformBoatType> createKey(Identifier id) {
		return RegistryKey.of(INSTANCE.getKey(), id);
	}
}
