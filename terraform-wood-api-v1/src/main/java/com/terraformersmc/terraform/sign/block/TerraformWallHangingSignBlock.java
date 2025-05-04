package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;

import com.terraformersmc.terraform.sign.TerraformSign;
import net.minecraft.block.WallHangingSignBlock;
import net.minecraft.block.WoodType;

public class TerraformWallHangingSignBlock extends WallHangingSignBlock implements TerraformSign {

	public TerraformWallHangingSignBlock(Settings settings, WoodType woodType) {
		super(BlockSettingsLock.lock(settings), woodType);
	}
}
