package com.terraformersmc.terraform.utils;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

public class TerraformFuelRegistry {
	static final Map<Item, Integer> FUEL_MAP = new Object2ObjectArrayMap<>();

	public static void addFuel(Item item, int fuelValue){
		FUEL_MAP.put(item, fuelValue);
	}

	public static void addFuel(Block block, int fuelValue){
		FUEL_MAP.put(block.asItem(), fuelValue);
	}
}
