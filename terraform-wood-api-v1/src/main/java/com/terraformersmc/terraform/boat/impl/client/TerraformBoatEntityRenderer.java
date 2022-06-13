package com.terraformersmc.terraform.boat.impl.client;

import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatEntity;
import net.minecraft.client.render.entity.BoatEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.BoatEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TerraformBoatEntityRenderer extends BoatEntityRenderer {
	private final Map<TerraformBoatType, Pair<Identifier, BoatEntityModel>> texturesAndModels;

	public TerraformBoatEntityRenderer(EntityRendererFactory.Context context) {
		super(context);

		this.texturesAndModels = TerraformBoatTypeRegistry.INSTANCE.getEntrySet().stream().collect(ImmutableMap.toImmutableMap(entry -> {
			return entry.getValue();
		}, entry -> {
			Identifier id = entry.getKey().getValue();
			Identifier textureId = new Identifier(id.getNamespace(), "textures/entity/boat/" + id.getPath() + ".png");

			EntityModelLayer layer = TerraformBoatClientHelper.getLayer(id);
			BoatEntityModel model = new BoatEntityModel(context.getPart(layer));

			return new Pair<>(textureId, model);
		}));
	}

	@Override
	public Identifier getTexture(BoatEntity entity) {
		if (entity instanceof TerraformBoatEntity) {
			TerraformBoatType boat = ((TerraformBoatEntity) entity).getTerraformBoat();
			return this.texturesAndModels.get(boat).getFirst();
		}
		return super.getTexture(entity);
	}

	@Override
	public Pair<Identifier, BoatEntityModel> getModelWithLocation(BoatEntity boat) {
		if (boat instanceof TerraformBoatEntity boatEntity){
			return getTextureAndModel(boatEntity);
		}
		return super.getModelWithLocation(boat);
	}

	public Pair<Identifier, BoatEntityModel> getTextureAndModel(TerraformBoatEntity entity) {
		return this.texturesAndModels.get(entity.getTerraformBoat());
	}
}
