package com.terraformersmc.terraform.dirt.block;

import net.minecraft.block.Block;
import net.minecraft.block.FarmlandBlock;

/**
 * A custom farmland block for new farmland. Mixins are required to make hoes create these blocks and to allow seeds to be planted.
 * @see com.terraformersmc.terraform.dirt.mixin.MixinFarmlandBlock
 */
public class TerraformFarmlandBlock extends FarmlandBlock {
	/**
	 * @deprecated the "trampled" block is no longer controlled by TerraformFarmlandBlock, use the other constructor.
	 */
	@Deprecated
	public TerraformFarmlandBlock(Settings settings, Block trampled) {
		super(settings);
	}

	public TerraformFarmlandBlock(Settings settings) {
		super(settings);
	}
}
