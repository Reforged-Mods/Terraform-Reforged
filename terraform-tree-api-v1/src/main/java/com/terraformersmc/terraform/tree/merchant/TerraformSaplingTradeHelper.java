package com.terraformersmc.terraform.tree.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.WanderingTraderManager;
import net.minecraftforge.event.village.WandererTradesEvent;
import org.apache.commons.lang3.ArrayUtils;

/**
 * A helper class for merchant trades regarding saplings.
 */
public final class TerraformSaplingTradeHelper {
	private TerraformSaplingTradeHelper() {}

	public static void onWanderingTraderEvent(WandererTradesEvent event){
	}

	/**
	 * Registers a trade for wandering traders that sells saplings for 5 emeralds, similar to vanilla saplings.
	 */
	public static void registerWanderingTraderSaplingTrades(ItemConvertible... saplings) {
		registerWanderingTraderOffers(1, factories -> {
			for (ItemConvertible sapling : saplings) {
				factories.add(new SellSaplingFactory(sapling));
			}
		});
	}

	public static synchronized void registerWanderingTraderOffers(int level, Consumer<List<TradeOffers.Factory>> factory) {
		registerOffers(TradeOffers.WANDERING_TRADER_TRADES, level, factory);
	}

	// Shared code to register offers for both villagers and wandering traders.
	private static void registerOffers(Int2ObjectMap<TradeOffers.Factory[]> leveledTradeMap, int level, Consumer<List<TradeOffers.Factory>> factory) {
		final List<TradeOffers.Factory> list = new ArrayList<>();
		factory.accept(list);

		final TradeOffers.Factory[] originalEntries = leveledTradeMap.computeIfAbsent(level, key -> new TradeOffers.Factory[0]);
		final TradeOffers.Factory[] addedEntries = list.toArray(new TradeOffers.Factory[0]);

		final TradeOffers.Factory[] allEntries = ArrayUtils.addAll(originalEntries, addedEntries);
		leveledTradeMap.put(level, allEntries);
	}

	private static class SellSaplingFactory implements TradeOffers.Factory {
		private final ItemStack sapling;

		public SellSaplingFactory(ItemConvertible sapling) {
			this.sapling = new ItemStack(sapling);
		}

		@Override
		public TradeOffer create(Entity entity, Random random) {
			return new TradeOffer(new ItemStack(Items.EMERALD, 5), this.sapling, 8, 1, 0.05f);
		}
	}
}
