package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;

import com.terraformersmc.terraform.sign.TerraformSign;
import net.minecraft.block.HangingSignBlock;
import net.minecraft.block.WoodType;

public class TerraformHangingSignBlock extends HangingSignBlock implements TerraformSign {
	public TerraformHangingSignBlock(Settings settings, WoodType woodType) {
		super(BlockSettingsLock.lock(settings), woodType);
	}
}
