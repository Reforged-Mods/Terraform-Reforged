package com.terraformersmc.terraform.utils;

import com.terraformersmc.terraform.utils.mixin.AbstractBlockAccessor;
import com.terraformersmc.terraform.utils.mixin.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;

public class TerraformUtils {

	public static AbstractBlock.Settings copy(Block block, MapColor color){
		AbstractBlock.Settings setting = AbstractBlock.Settings.of(block.getDefaultState().getMaterial(), color);
		AbstractBlockSettingsAccessor thisAccessor = (AbstractBlockSettingsAccessor) setting;
		AbstractBlockSettingsAccessor otherAccessor = (AbstractBlockSettingsAccessor) ((AbstractBlockAccessor)block).getSettings();

		thisAccessor.setMaterial(otherAccessor.getMaterial());
		((AbstractBlockSettingsAccessor) setting).setHardness(otherAccessor.getHardness());
		((AbstractBlockSettingsAccessor) setting).setResistance(otherAccessor.getResistance());
		((AbstractBlockSettingsAccessor) setting).setCollidable(otherAccessor.getCollidable());
		thisAccessor.setRandomTicks(otherAccessor.getRandomTicks());
		setting.luminance(otherAccessor.getLuminance());
		setting.sounds(otherAccessor.getSoundGroup());
		setting.slipperiness(otherAccessor.getSlipperiness());
		setting.velocityMultiplier(otherAccessor.getVelocityMultiplier());
		thisAccessor.setDynamicBounds(otherAccessor.getDynamicBounds());
		thisAccessor.setOpaque(otherAccessor.getOpaque());
		thisAccessor.setIsAir(otherAccessor.getIsAir());
		thisAccessor.setToolRequired(otherAccessor.isToolRequired());
		return setting;
	}
}
