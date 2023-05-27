package com.terraformersmc.terraform.utils;

import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.minecraft.data.DataCache;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;

import java.nio.file.Path;
import java.util.Set;

public class TeraformRecipeProvider extends RecipeProvider {
	public TeraformRecipeProvider(DataGenerator root) {
		super(root);
	}

	@Override
	public void run(DataCache cache) {
		Path path = this.root.getOutput();
		Set<Identifier> generatedRecipes = Sets.newHashSet();
		generate(provider -> {
			Identifier identifier = getRecipeIdentifier(provider.getRecipeId());

			if (!generatedRecipes.add(identifier)) {
				throw new IllegalStateException("Duplicate recipe " + identifier);
			}

			JsonObject recipeJson = provider.toJson();

			saveRecipe(cache, recipeJson, path.resolve("data/" + identifier.getNamespace() + "/recipes/" + identifier.getPath() + ".json"));
			JsonObject advancementJson = provider.toAdvancementJson();

			if (advancementJson != null) {
				saveRecipeAdvancement(cache, advancementJson, path.resolve("data/" + identifier.getNamespace() + "/advancements/" + provider.getAdvancementId().getPath() + ".json"));
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
