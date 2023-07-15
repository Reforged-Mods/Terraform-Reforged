package com.terraformersmc.terraform.dirt;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Allows the addition of custom tillable block mappings. You probably don't need to use this directly if you're using
 * {@link TerraformDirtRegistry}.
 */
@Mod.EventBusSubscriber(modid = "terraform_dirt_api_v1")
public abstract class TillableBlockRegistry {
	protected static final Map<Block, BlockState> TILLING_ACTIONS = new HashMap<>();

	/**
	 * Adds a custom tillable block mapping.
	 *
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param pair the interaction between the blocks
	 */
	public static void add(Block block, Pair<Predicate<ItemUsageContext>, Consumer<ItemUsageContext>> pair) {
		//TILLING_ACTIONS.put(block, pair);
	}
	
	/**
	 * Adds a custom tillable block mapping.
	 *
	 * Note that you don't need to call this yourself if you're already using {@link TerraformDirtRegistry}.
	 *
	 * @param block the block being tilled
	 * @param state the block to be replaced with
	 */
	public static void add(Block block, BlockState state) {
		//TILLING_ACTIONS.put(block, Pair.of(HoeItem::canTillFarmland, HoeItem.createTillAction(state)));
		TILLING_ACTIONS.put(block, state);
	}

	@SubscribeEvent
	public static void onBlockToolInteraction(BlockEvent.BlockToolModificationEvent event){
		if (event.getToolAction() == ToolActions.HOE_TILL){
			if (TILLING_ACTIONS.containsKey(event.getFinalState().getBlock())){
				BlockState state = TILLING_ACTIONS.get(event.getFinalState().getBlock());
				event.setFinalState(state);
			}
		}
	}
}
