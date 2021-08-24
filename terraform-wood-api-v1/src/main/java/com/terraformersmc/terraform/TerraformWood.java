package com.terraformersmc.terraform;

import com.terraformersmc.terraform.boat.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.TerraformBoatItem;
import com.terraformersmc.terraform.boat.TerraformBoatRenderer;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import terraformersmc.terraform.TerraformMod;

import java.util.function.Supplier;

public class TerraformWood extends TerraformMod {
	@Override
	public void setup(FMLCommonSetupEvent event) {

	}

	@Override
	public void clientSetup(FMLClientSetupEvent event) {
		for (Supplier<EntityType<TerraformBoatEntity>>  supplier :TerraformBoatItem.SUPPLIERS){
			RenderingRegistry.registerEntityRenderingHandler(supplier.get(), TerraformBoatRenderer::new);
		}
	}
}
