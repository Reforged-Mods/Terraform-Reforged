package com.terraformersmc.terraform.utils.mixin;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.function.Function;
import java.util.function.ToIntFunction;

@Mixin(AbstractBlock.Settings.class)
public interface AbstractBlockSettingsAccessor {
    /* GETTERS */
	@Accessor
	Material getMaterial();

	@Accessor
	float getHardness();

	@Accessor
	float getResistance();

	@Accessor
	boolean getCollidable();

	@Accessor
	boolean getRandomTicks();

	@Accessor
	ToIntFunction<BlockState> getLuminance();

	@Accessor
	Function<BlockState, MapColor> getMapColorProvider();

	@Accessor
	BlockSoundGroup getSoundGroup();

	@Accessor
	float getSlipperiness();

	@Accessor
	float getVelocityMultiplier();

	@Accessor
	float getJumpVelocityMultiplier();

	@Accessor
	boolean getDynamicBounds();

	@Accessor
	boolean getOpaque();

	@Accessor
	boolean getIsAir();

	@Accessor
	boolean isToolRequired();

	@Accessor
	AbstractBlock.TypedContextPredicate<EntityType<?>> getAllowsSpawningPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getSolidBlockPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getSuffocationPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getBlockVisionPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getPostProcessPredicate();

	@Accessor
	AbstractBlock.ContextPredicate getEmissiveLightingPredicate();

	/* SETTERS */
	@Accessor
	void setMaterial(Material material);

	@Accessor
	void setHardness(float hardness);

	@Accessor
	void setResistance(float resistance);

	@Accessor
	void setCollidable(boolean collidable);

	@Accessor
	void setRandomTicks(boolean ticksRandomly);

	@Accessor
	void setMapColorProvider(Function<BlockState, MapColor> mapColorProvider);

	@Accessor
	void setDynamicBounds(boolean dynamicBounds);

	@Accessor
	void setOpaque(boolean opaque);

	@Accessor
	void setIsAir(boolean isAir);

	@Accessor
	void setLootTableId(Identifier lootTableId);

	@Accessor
	void setToolRequired(boolean toolRequired);

	/* INVOKERS */
	@Invoker
	Block.Settings invokeSounds(BlockSoundGroup group);

	@Invoker
	Block.Settings invokeBreakInstantly();

	@Invoker
	Block.Settings invokeStrength(float strength);

	@Invoker
	Block.Settings invokeTicksRandomly();

	@Invoker
	Block.Settings invokeDropsNothing();

}
