package com.terraformersmc.terraform.boat.impl.client;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.boat.impl.TerraformBoatInitializer;

import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Map;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
public final class TerraformBoatClientInitializer {

	public static final Map<Identifier, Map<Boolean, Supplier<TexturedModelData>>> SUPPLIER_MAP = new Object2ObjectLinkedOpenHashMap<>();

	@SubscribeEvent
	public void onEvent(EntityRenderersEvent.RegisterRenderers event){
		event.registerEntityRenderer(TerraformBoatInitializer.BOAT, context -> new TerraformBoatEntityRenderer(context, false));
		event.registerEntityRenderer(TerraformBoatInitializer.CHEST_BOAT, context -> new TerraformBoatEntityRenderer(context, true));
	}

	@SubscribeEvent
	public void onRegisterModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
		SUPPLIER_MAP.forEach((i, s) -> {
			event.registerLayerDefinition(TerraformBoatClientHelper.getLayer(i, true), s.get(true));
			event.registerLayerDefinition(TerraformBoatClientHelper.getLayer(i, false), s.get(false));
		});
	}

	@SubscribeEvent
	public void onRegisterSpriteEvents(TextureStitchEvent.Pre event){
		SpriteIdentifierRegistry.INSTANCE.getIdentifiers().forEach(spriteIdentifier -> {
			if (event.getAtlas().getId().equals(spriteIdentifier.getAtlasId())){
				event.addSprite(spriteIdentifier.getTextureId());
			}
		});
	}
}
