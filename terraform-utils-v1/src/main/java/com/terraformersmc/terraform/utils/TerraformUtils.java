package com.terraformersmc.terraform.utils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("terraform_utils")
public class TerraformUtils {

	public TerraformUtils(){
		MinecraftForge.EVENT_BUS.addListener(this::onBurnTimeEvent);
	}

	private void onBurnTimeEvent(FurnaceFuelBurnTimeEvent event){
		Item item = event.getItemStack().getItem();
		if (TerraformFuelRegistry.FUEL_MAP.containsKey(item)){
			event.setBurnTime(TerraformFuelRegistry.FUEL_MAP.get(item));
		}
	}
}
