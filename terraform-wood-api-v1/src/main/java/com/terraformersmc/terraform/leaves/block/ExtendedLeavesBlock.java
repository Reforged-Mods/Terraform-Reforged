package com.terraformersmc.terraform.leaves.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Random;

/**
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves. It also does not block light at all.
 */
public class ExtendedLeavesBlock extends Block implements IForgeShearable {
	public static final int MAX_DISTANCE = 14;
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;
	public static final IntProperty DISTANCE = IntProperty.of("distance", 1, MAX_DISTANCE);

	public ExtendedLeavesBlock(Block.Settings settings) {
		super(settings);
		this.setDefaultState(this.getStateManager().getDefaultState().with(DISTANCE, MAX_DISTANCE).with(PERSISTENT, false));
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return state.get(DISTANCE) == MAX_DISTANCE && !state.get(PERSISTENT);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (!state.get(PERSISTENT) && state.get(DISTANCE) == MAX_DISTANCE) {
			dropStacks(state, world, pos);
			world.removeBlock(pos, false);
		}

	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
	}


	@Override
	public int getOpacity(BlockState state, BlockView view, BlockPos pos) {
		return 0;
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		int distance = getDistanceFromLog(neighborState) + 1;
		if (distance != 1 || state.get(DISTANCE) != distance) {
			world.createAndScheduleBlockTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int distance = MAX_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			distance = Math.min(distance, getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (distance == 1) {
				break;
			}
		}

		return state.with(DISTANCE, distance);
	}

	private static int getDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) {
			return 0;
		} else {
			return state.getBlock() instanceof ExtendedLeavesBlock ? state.get(DISTANCE) : MAX_DISTANCE;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		Blocks.OAK_LEAVES.randomDisplayTick(state, world, pos, random);
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		// OptiLeaves optimization: Cull faces with leaf block neighbors to reduce geometry in redwood forests
		return neighborState.getBlock() instanceof ExtendedLeavesBlock;
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(DISTANCE, PERSISTENT);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, true), context.getWorld(), context.getBlockPos());
	}

	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return VoxelShapes.empty();
	}


	//AntimatterApi branch cutter support
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
		List<ItemStack> list = super.getDroppedStacks(state, builder);
		if (ModList.get().isLoaded("antimatter")){
			ItemStack stack = builder.get(LootContextParameters.TOOL);
			if (stack != null && !stack.isEmpty() && stack.getItem().getRegistryName().toString().equals("antimatter:branch_cutter")){
				ItemStack sapling = ItemStack.EMPTY;
				if (ForgeRegistries.BLOCKS.containsKey(new Identifier(this.getRegistryName().toString().replace("leaves", "sapling")))){
					sapling = new ItemStack(ForgeRegistries.BLOCKS.getValue(new Identifier(this.getRegistryName().toString().replace("leaves", "sapling"))));
				}
				if (!sapling.isEmpty()){
					list.clear();
					list.add(sapling);
				}
			}
		}
		return list;
	}
}
