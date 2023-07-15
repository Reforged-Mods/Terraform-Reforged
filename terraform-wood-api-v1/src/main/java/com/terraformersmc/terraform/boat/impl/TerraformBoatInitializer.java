package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.impl.entity.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.impl.entity.TerraformChestBoatEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegisterEvent;

public final class TerraformBoatInitializer {
	private static final EntityDimensions DIMENSIONS = EntityDimensions.fixed(1.375f, 0.5625f);
	private static final Identifier BOAT_ID = new Identifier("terraform", "boat");
	@ObjectHolder(registryName = "minecraft:entity_type", value = "terraform:boat")
	public static final EntityType<TerraformBoatEntity> BOAT = null;

	private static final Identifier CHEST_BOAT_ID = new Identifier("terraform", "chest_boat");
	@ObjectHolder(registryName = "minecraft:entity_type", value = "terraform:chest_boat")
	public static final EntityType<TerraformChestBoatEntity> CHEST_BOAT = null;
	public TerraformBoatInitializer(){
		TerraformBoatTrackedData.register();
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}


	@SubscribeEvent
	public void onRegisterEntities(final RegisterEvent event){
		if (event.getRegistryKey() == ForgeRegistries.Keys.ENTITY_TYPES){
			event.register(ForgeRegistries.Keys.ENTITY_TYPES, BOAT_ID, () -> EntityType.Builder.<TerraformBoatEntity>create(TerraformBoatEntity::new, SpawnGroup.MISC)
				.setDimensions(DIMENSIONS.width, DIMENSIONS.height)
				.build(BOAT_ID.toString()));
			event.register(ForgeRegistries.Keys.ENTITY_TYPES, CHEST_BOAT_ID, () -> EntityType.Builder.<TerraformChestBoatEntity>create(TerraformChestBoatEntity::new, SpawnGroup.MISC)
				.setDimensions(DIMENSIONS.width, DIMENSIONS.height)
				.build(CHEST_BOAT_ID.toString()));
		}
	}
}
