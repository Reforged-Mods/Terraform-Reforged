package com.terraformersmc.terraform.wood;

import com.terraformersmc.terraform.boat.api.item.TerraformBoatItemHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;
import com.terraformersmc.terraform.boat.impl.client.TerraformBoatClientInitializer;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod("terraform_wood_api_v1")
public class TerraformWoodApi {
	public TerraformWoodApi(){
		new TerraformBoatInitializer();
		if (FMLEnvironment.dist == Dist.CLIENT){
			FMLJavaModLoadingContext.get().getModEventBus().register(new TerraformBoatClientInitializer());
		}
	}
}
