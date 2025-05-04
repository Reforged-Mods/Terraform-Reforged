package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	public TerraformSignBlock(Settings settings, WoodType type) {
		super(BlockSettingsLock.lock(settings), type);
	}
}
