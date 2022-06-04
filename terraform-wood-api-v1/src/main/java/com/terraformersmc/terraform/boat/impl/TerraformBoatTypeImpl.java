package com.terraformersmc.terraform.boat.impl;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;

import net.minecraft.item.Item;

/**
 * A simple implementation of {@link TerraformBoatType}.
 */
public record TerraformBoatTypeImpl(Item item) implements TerraformBoatType {
}
