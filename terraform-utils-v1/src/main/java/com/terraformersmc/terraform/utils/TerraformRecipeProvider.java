package com.terraformersmc.terraform.utils;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.data.DataOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.ModLoadingContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public abstract class TerraformRecipeProvider extends RecipeProvider {
	public TerraformRecipeProvider(DataOutput root) {
		super(root);
	}

	@Override
	public CompletableFuture<?> run(DataWriter writer) {
		Set<Identifier> generatedRecipes = Sets.newHashSet();
		List<CompletableFuture<?>> list = new ArrayList();
		this.generate((provider) -> {
			Identifier identifier = getRecipeIdentifier(provider.getRecipeId());
			if (!generatedRecipes.add(identifier)) {
				throw new IllegalStateException("Duplicate recipe " + identifier);
			}
			list.add(DataProvider.writeToPath(writer, provider.toJson(), this.recipesPathResolver.resolveJson(identifier)));
			JsonObject jsonobject = provider.toAdvancementJson();
			if (jsonobject != null) {
				CompletableFuture<?> saveAdvancementFuture = this.saveAdvancement(writer, provider, jsonobject);
				if (saveAdvancementFuture != null) {
					list.add(saveAdvancementFuture);
				}
			}

		});
		return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
	}

	/**
	 * Override this method to change the recipe identifier. The default implementation normalizes the namespace to the mod ID.
	 */
	protected Identifier getRecipeIdentifier(Identifier identifier) {
		return new Identifier(ModLoadingContext.get().getActiveNamespace(), identifier.getPath());
	}
}
