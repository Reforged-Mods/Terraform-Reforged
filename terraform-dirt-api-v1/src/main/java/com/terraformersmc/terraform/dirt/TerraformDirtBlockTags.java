package com.terraformersmc.terraform.dirt;

import net.minecraft.block.Block;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraftforge.common.Tags;

public class TerraformDirtBlockTags {
	/**
	 * Dirts, grass blocks, and podzol.
	 */
	public static final Tags.IOptionalNamedTag<Block> SOIL = register("soil");
	public static final Tags.IOptionalNamedTag<Block> GRASS_BLOCKS = register("grass_blocks");
	public static final Tags.IOptionalNamedTag<Block> FARMLAND = register("farmland");

	private TerraformDirtBlockTags() {
	}

	public static void init(){
	}

	private static Tags.IOptionalNamedTag<Block> register(String id) {
		return BlockTags.createOptional(new Identifier("terraform", id));
	}
}
