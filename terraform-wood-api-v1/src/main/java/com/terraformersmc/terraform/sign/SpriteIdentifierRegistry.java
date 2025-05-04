package com.terraformersmc.terraform.sign;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;

public class SpriteIdentifierRegistry {
	public static final SpriteIdentifierRegistry INSTANCE = new SpriteIdentifierRegistry();
	private final List<Identifier> identifiers;

	private SpriteIdentifierRegistry() {
		identifiers = new ArrayList<>();
	}

	public void addIdentifier(Identifier sprite) {
		this.identifiers.add(sprite);
	}

	public Collection<Identifier> getIdentifiers() {
		return Collections.unmodifiableList(identifiers);
	}
}
