package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.SignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformSignBlock extends SignBlock implements TerraformSign {
	SignType signType;
	public TerraformSignBlock(Settings settings, SignType type) {
		super(settings, type);
		this.signType = type;
	}

	@Override
	public SignType getSignType() {
		return signType;
	}
}
