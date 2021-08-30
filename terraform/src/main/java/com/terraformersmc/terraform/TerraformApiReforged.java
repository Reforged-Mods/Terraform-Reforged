package com.terraformersmc.terraform;


import com.terraformersmc.terraform.boat.TerraformBoatEntity;
import com.terraformersmc.terraform.boat.TerraformBoatItem;
import com.terraformersmc.terraform.boat.TerraformBoatRenderer;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.tree.feature.TerraformTreeFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.function.Supplier;

@Mod("terraform")
public class TerraformApiReforged {
	public TerraformApiReforged(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		TerraformTreeFeatures.init();
	}

	private void setup(FMLCommonSetupEvent event){
		TerraformDirtBlockTags.init();
	}

	private void clientSetup(FMLClientSetupEvent event){
	}

	@SubscribeEvent
	public void onRegister(final RegistryEvent.Register<Feature<?>> event){

		event.getRegistry().register(TerraformTreeFeatures.SANDY_TREE);
	}
}
