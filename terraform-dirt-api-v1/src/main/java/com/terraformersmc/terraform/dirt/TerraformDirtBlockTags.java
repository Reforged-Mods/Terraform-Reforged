package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final Tag.Identified<Block> SOIL = register("soil");
	public static final Tag.Identified<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final Tag.Identified<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
	}

	private static Tag.Identified<Block> register(String id) {
		return BlockTags.register(new Identifier("terraform", id).toString());
	}
}
