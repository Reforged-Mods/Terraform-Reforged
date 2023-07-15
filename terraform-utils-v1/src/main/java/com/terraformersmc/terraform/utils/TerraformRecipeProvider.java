package com.terraformersmc.terraform.utils;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataWriter;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.ModLoadingContext;

import java.nio.file.Path;
import java.util.Set;

public class TerraformRecipeProvider extends RecipeProvider {
	public TerraformRecipeProvider(DataGenerator root) {
		super(root);
	}

	@Override
	public void run(DataWriter cache) {
		Set<Identifier> generatedRecipes = Sets.newHashSet();
		generate(provider -> {
			Identifier identifier = getRecipeIdentifier(provider.getRecipeId());

			if (!generatedRecipes.add(identifier)) {
				throw new IllegalStateException("Duplicate recipe " + identifier);
			}

			JsonObject recipeJson = provider.toJson();

			saveRecipe(cache, recipeJson, this.recipesPathResolver.resolveJson(identifier));
			JsonObject advancementJson = provider.toAdvancementJson();

			if (advancementJson != null) {
				saveRecipeAdvancement(cache, advancementJson, this.advancementsPathResolver.resolveJson(getRecipeIdentifier(provider.getAdvancementId())));
			}
		});
	}

	/**
	 * Override this method to change the recipe identifier. The default implementation normalizes the namespace to the mod ID.
	 */
	protected Identifier getRecipeIdentifier(Identifier identifier) {
		return new Identifier(ModLoadingContext.get().getActiveNamespace(), identifier.getPath());
	}
}
