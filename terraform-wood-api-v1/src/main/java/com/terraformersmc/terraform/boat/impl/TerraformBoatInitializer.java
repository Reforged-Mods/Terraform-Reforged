package com.terraformersmc.terraform.boat.impl;

import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ObjectHolder;

public final class TerraformBoatInitializer {
	private static final EntityDimensions DIMENSIONS = EntityDimensions.fixed(1.375f, 0.5625f);
	private static final Identifier BOAT_ID = new Identifier("terraform", "boat");
	@ObjectHolder("terraform:boat")
	public static final EntityType<TerraformBoatEntity> BOAT = null;

	private static final Identifier CHEST_BOAT_ID = new Identifier("terraform", "chest_boat");
	public static final EntityType<TerraformChestBoatEntity> CHEST_BOAT = FabricEntityTypeBuilder.<TerraformChestBoatEntity>create(SpawnGroup.MISC, TerraformChestBoatEntity::new)
		.dimensions(DIMENSIONS)
		.build();
	public TerraformBoatInitializer(){
		TerraformBoatTrackedData.register();
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}


	@SubscribeEvent
	public void onRegisterEntities(final RegistryEvent.Register<EntityType<?>> event){
		event.getRegistry().register(EntityType.Builder.<TerraformBoatEntity>create(TerraformBoatEntity::new, SpawnGroup.MISC)
				.setDimensions(1.375f, 0.5625f)
				.build(BOAT_ID.toString()).setRegistryName(BOAT_ID));
	}
}
