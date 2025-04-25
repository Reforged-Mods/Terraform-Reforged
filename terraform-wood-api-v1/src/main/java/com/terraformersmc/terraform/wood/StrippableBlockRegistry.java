package com.terraformersmc.terraform.wood;

import com.terraformersmc.terraform.wood.mixin.AxeItemAccessor;
import net.minecraft.block.Block;
import net.minecraft.state.property.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

public class StrippableBlockRegistry {
	private static final Logger LOGGER = LoggerFactory.getLogger(StrippableBlockRegistry.class);
	/**
	 * Registers a stripping interaction.
	 *
	 * <p>Both blocks must have the {@link Properties#AXIS axis} property.
	 *
	 * @param input    the input block that can be stripped
	 * @param stripped the stripped result block
	 * @throws IllegalArgumentException if the input or the output doesn't have the {@link Properties#AXIS axis} property
	 */
	public static void register(Block input, Block stripped) {
		requireNonNullAndAxisProperty(input, "input block");
		requireNonNullAndAxisProperty(stripped, "stripped block");

		Block old = getRegistry().put(input, stripped);

		if (old != null) {
			LOGGER.debug("Replaced old stripping mapping from {} to {} with {}", input, old, stripped);
		}
	}

	private static void requireNonNullAndAxisProperty(Block block, String name) {
		Objects.requireNonNull(block, name + " cannot be null");

		if (!block.getStateManager().getProperties().contains(Properties.AXIS)) {
			throw new IllegalArgumentException(name + " must have the 'axis' property");
		}
	}

	private static Map<Block, Block> getRegistry() {
		return AxeItemAccessor.getSTRIPPED_BLOCKS();
	}
}
