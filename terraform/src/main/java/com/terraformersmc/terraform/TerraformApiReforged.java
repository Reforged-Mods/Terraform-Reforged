package com.terraformersmc.terraform;


import com.google.common.eventbus.Subscribe;
import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;
import com.terraformersmc.terraform.dirt.TerraformDirtBlockTags;
import com.terraformersmc.terraform.tree.feature.TerraformTreeFeatures;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.Map;

@Mod("terraform")
public class TerraformApiReforged {
	public TerraformApiReforged(){
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		new TerraformBoatInitializer();
	}

	private void setup(FMLCommonSetupEvent event){
		//TerraformDirtBlockTags.init();
	}

	private void clientSetup(FMLClientSetupEvent event){
	}





	@SubscribeEvent
	public void onRegisterItem(final RegistryEvent.Register<Item> event){
		TerraformBoatItemHelper.REGISTRY_MAP.forEach((r, i) -> {
			if (i.getRegistryName() == null) i.setRegistryName(r);
			event.getRegistry().register(i);
		});
	}
}
