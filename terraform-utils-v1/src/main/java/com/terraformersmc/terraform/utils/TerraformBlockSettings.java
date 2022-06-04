package com.terraformersmc.terraform.utils;

import com.terraformersmc.terraform.utils.mixin.AbstractBlockAccessor;
import com.terraformersmc.terraform.utils.mixin.AbstractBlockSettingsAccessor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;

import java.util.function.ToIntFunction;

public class TerraformBlockSettings extends AbstractBlock.Settings {
	protected TerraformBlockSettings(Material material, MapColor color) {
		super(material, color);
	}

	protected TerraformBlockSettings(AbstractBlock.Settings settings) {
		super(((AbstractBlockSettingsAccessor) settings).getMaterial(), ((AbstractBlockSettingsAccessor) settings).getMapColorProvider());
		// Mostly Copied from vanilla's copy method
		// Note: If new methods are added to Block settings, an accessor must be added here
		AbstractBlockSettingsAccessor thisAccessor = (AbstractBlockSettingsAccessor) this;
		AbstractBlockSettingsAccessor otherAccessor = (AbstractBlockSettingsAccessor) settings;

		thisAccessor.setMaterial(otherAccessor.getMaterial());
		this.hardness(otherAccessor.getHardness());
		this.resistance(otherAccessor.getResistance());
		this.collidable(otherAccessor.getCollidable());
		thisAccessor.setRandomTicks(otherAccessor.getRandomTicks());
		this.luminance(otherAccessor.getLuminance());
		thisAccessor.setMapColorProvider(otherAccessor.getMapColorProvider());
		this.sounds(otherAccessor.getSoundGroup());
		this.slipperiness(otherAccessor.getSlipperiness());
		this.velocityMultiplier(otherAccessor.getVelocityMultiplier());
		this.jumpVelocityMultiplier(otherAccessor.getJumpVelocityMultiplier());
		thisAccessor.setDynamicBounds(otherAccessor.getDynamicBounds());
		thisAccessor.setOpaque(otherAccessor.getOpaque());
		thisAccessor.setIsAir(otherAccessor.getIsAir());
		thisAccessor.setToolRequired(otherAccessor.isToolRequired());
		this.allowsSpawning(otherAccessor.getAllowsSpawningPredicate());
		this.solidBlock(otherAccessor.getSolidBlockPredicate());
		this.suffocates(otherAccessor.getSuffocationPredicate());
		this.blockVision(otherAccessor.getBlockVisionPredicate());
		this.postProcess(otherAccessor.getPostProcessPredicate());
		this.emissiveLighting(otherAccessor.getEmissiveLightingPredicate());
	}

	public static TerraformBlockSettings of(Material material) {
		return of(material, material.getColor());
	}

	public static TerraformBlockSettings of(Material material, MapColor color) {
		return new TerraformBlockSettings(material, color);
	}

	public static TerraformBlockSettings of(Material material, DyeColor color) {
		return new TerraformBlockSettings(material, color.getMapColor());
	}

	public static TerraformBlockSettings copyOf(AbstractBlock block) {
		return new TerraformBlockSettings(((AbstractBlockAccessor) block).getSettings());
	}

	public static TerraformBlockSettings copyOf(AbstractBlock.Settings settings) {
		return new TerraformBlockSettings(settings);
	}

	@Override
	public TerraformBlockSettings noCollision() {
		super.noCollision();
		return this;
	}

	@Override
	public TerraformBlockSettings nonOpaque() {
		super.nonOpaque();
		return this;
	}

	@Override
	public TerraformBlockSettings slipperiness(float value) {
		super.slipperiness(value);
		return this;
	}

	@Override
	public TerraformBlockSettings velocityMultiplier(float velocityMultiplier) {
		super.velocityMultiplier(velocityMultiplier);
		return this;
	}

	@Override
	public TerraformBlockSettings jumpVelocityMultiplier(float jumpVelocityMultiplier) {
		super.jumpVelocityMultiplier(jumpVelocityMultiplier);
		return this;
	}

	@Override
	public TerraformBlockSettings sounds(BlockSoundGroup group) {
		super.sounds(group);
		return this;
	}

	/**
	 * @deprecated Please use {@link TerraformBlockSettings#luminance(ToIntFunction)}.
	 */
	public TerraformBlockSettings lightLevel(ToIntFunction<BlockState> levelFunction) {
		return this.luminance(levelFunction);
	}

	@Override
	public TerraformBlockSettings luminance(ToIntFunction<BlockState> luminanceFunction) {
		super.luminance(luminanceFunction);
		return this;
	}

	@Override
	public TerraformBlockSettings strength(float hardness, float resistance) {
		super.strength(hardness, resistance);
		return this;
	}

	@Override
	public TerraformBlockSettings breakInstantly() {
		super.breakInstantly();
		return this;
	}

	public TerraformBlockSettings strength(float strength) {
		super.strength(strength);
		return this;
	}

	@Override
	public TerraformBlockSettings ticksRandomly() {
		super.ticksRandomly();
		return this;
	}

	@Override
	public TerraformBlockSettings dynamicBounds() {
		super.dynamicBounds();
		return this;
	}

	@Override
	public TerraformBlockSettings dropsNothing() {
		super.dropsNothing();
		return this;
	}

	@Override
	public TerraformBlockSettings dropsLike(Block block) {
		super.dropsLike(block);
		return this;
	}

	@Override
	public TerraformBlockSettings air() {
		super.air();
		return this;
	}

	@Override
	public TerraformBlockSettings allowsSpawning(AbstractBlock.TypedContextPredicate<EntityType<?>> predicate) {
		super.allowsSpawning(predicate);
		return this;
	}

	@Override
	public TerraformBlockSettings solidBlock(AbstractBlock.ContextPredicate predicate) {
		super.solidBlock(predicate);
		return this;
	}

	@Override
	public TerraformBlockSettings suffocates(AbstractBlock.ContextPredicate predicate) {
		super.suffocates(predicate);
		return this;
	}

	@Override
	public TerraformBlockSettings blockVision(AbstractBlock.ContextPredicate predicate) {
		super.blockVision(predicate);
		return this;
	}

	@Override
	public TerraformBlockSettings postProcess(AbstractBlock.ContextPredicate predicate) {
		super.postProcess(predicate);
		return this;
	}

	@Override
	public TerraformBlockSettings emissiveLighting(AbstractBlock.ContextPredicate predicate) {
		super.emissiveLighting(predicate);
		return this;
	}

	/* Terraform ADDITIONS*/

	/**
	 * @deprecated Please use {@link TerraformBlockSettings#luminance(int)}.
	 */
	@Deprecated
	public TerraformBlockSettings lightLevel(int lightLevel) {
		this.luminance(lightLevel);
		return this;
	}

	public TerraformBlockSettings luminance(int luminance) {
		this.luminance(ignored -> luminance);
		return this;
	}

	public TerraformBlockSettings hardness(float hardness) {
		((AbstractBlockSettingsAccessor) this).setHardness(hardness);
		return this;
	}

	public TerraformBlockSettings resistance(float resistance) {
		((AbstractBlockSettingsAccessor) this).setResistance(Math.max(0.0F, resistance));
		return this;
	}

	public TerraformBlockSettings drops(Identifier dropTableId) {
		((AbstractBlockSettingsAccessor) this).setLootTableId(dropTableId);
		return this;
	}

	/**
	 * Make the block require tool to drop and slows down mining speed if the incorrect tool is used.
	 */
	@Override
	public TerraformBlockSettings requiresTool() {
		super.requiresTool();
		return this;
	}

	/* Terraform DELEGATE WRAPPERS */

	/**
	 * @deprecated Please migrate to {@link TerraformBlockSettings#mapColor(MapColor)}
	 */
	@Deprecated
	public TerraformBlockSettings materialColor(MapColor color) {
		return this.mapColor(color);
	}

	/**
	 * @deprecated Please migrate to {@link TerraformBlockSettings#mapColor(DyeColor)}
	 */
	@Deprecated
	public TerraformBlockSettings materialColor(DyeColor color) {
		return this.mapColor(color);
	}

	public TerraformBlockSettings mapColor(MapColor color) {
		((AbstractBlockSettingsAccessor) this).setMapColorProvider(ignored -> color);
		return this;
	}

	public TerraformBlockSettings mapColor(DyeColor color) {
		return this.mapColor(color.getMapColor());
	}

	public TerraformBlockSettings collidable(boolean collidable) {
		((AbstractBlockSettingsAccessor) this).setCollidable(collidable);
		return this;
	}
}
