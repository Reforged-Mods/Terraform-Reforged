package com.terraformersmc.terraform.sign.block;

import com.terraformersmc.terraform.sign.BlockSettingsLock;
import com.terraformersmc.terraform.sign.TerraformSign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.util.Identifier;

public class TerraformWallSignBlock extends WallSignBlock implements TerraformSign {
	SignType signType;
	public TerraformWallSignBlock(Settings settings, SignType signType) {
		super(settings, signType); //TODO: take a look at this again
		this.signType = signType;
	}

	@Override
	public SignType getSignType() {
		return signType;
	}
}
