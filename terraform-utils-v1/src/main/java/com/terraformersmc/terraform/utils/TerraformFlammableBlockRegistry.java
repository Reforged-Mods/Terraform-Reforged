package com.terraformersmc.terraform.utils;

import com.terraformersmc.terraform.utils.mixin.FireBlockAccessor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import oshi.util.tuples.Pair;

import java.util.Map;

public class TerraformFlammableBlockRegistry {

	public static void addFlammableBlock(Block block, int burn, int spread){
		((FireBlockAccessor) Blocks.FIRE).invokeRegisterFlammableBlock(block, burn, spread);
	}
}
