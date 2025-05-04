package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	public TerraformWallSignBlock(Settings settings, WoodType signType) {
		super(BlockSettingsLock.lock(settings), signType);
	}
}
