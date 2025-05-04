package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.SignType;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	public TerraformWallSignBlock(Settings settings, SignType signType) {
		super(settings, signType);
	}
}
