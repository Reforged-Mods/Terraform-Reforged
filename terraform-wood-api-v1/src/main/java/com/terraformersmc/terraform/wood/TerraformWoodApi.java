package com.terraformersmc.terraform.wood;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;
import com.terraformersmc.terraform.boat.impl.client.TerraformBoatClientInitializer;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("terraform_wood_api_v1")
public class TerraformWoodApi {
	public TerraformWoodApi(){
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
		new TerraformBoatInitializer();
		if (FMLEnvironment.dist == Dist.CLIENT){
			FMLJavaModLoadingContext.get().getModEventBus().register(new TerraformBoatClientInitializer());
		}
	}

	@SubscribeEvent
	public void onRegisterItem(final RegistryEvent.Register<Item> event){
		TerraformBoatItemHelper.REGISTRY_MAP.forEach((r, i) -> {
			if (i.getRegistryName() == null) i.setRegistryName(r);
			event.getRegistry().register(i);
		});
	}
}
