package com.terraformersmc.terraform.leaves.block;

import com.terraformersmc.terraform.wood.block.SmallLogBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.OptionalInt;

/**
 * <p>
 * A leaves block with extended range, permitting leaves to be as far as 13 blocks away from the tree rather than the
 * limit of 6 blocks imposed by vanilla leaves.  Enabling the opti-leaves feature can reduce geometry and give better
 * rendering performance in dense forests of large trees.  By default, extended leaves are transparent (do not block
 * light at all).  If the optional leaf_particle field is present, the custom particle is used; otherwise a tinted
 * falling leaf particle is used.
 * </p><p>
 * Be aware when using the extended leaf range, there are now two distance properties which combine to provide the
 * extended distance range.  Mod authors should be careful not to directly rely on or set the value of the
 * {@link LeavesBlock#DISTANCE} property when using this class.  Instead, use the provided
 * {@link ExtendedLeavesBlock#getExtendedDistance(BlockState)} and
 * {@link ExtendedLeavesBlock#setExtendedDistance(BlockState, int)} methods, which will sum up and properly allocate
 * the total distance to the two properties.  For the total available extended distance constant (14), use
 * {@link ExtendedLeavesBlock#MAX_TOTAL_DISTANCE} instead of either MAX_DISTANCE or MAX_EXTENDED_DISTANCE.
 * </p>
 */
/* This class must override every LeavesBlock function that references (compiler inlined) MAX_DISTANCE.
 * The DISTANCE_1_7 property used by LeavesBlock is complemented by our EXTENDED_DISTANCE property.
 */
public class ExtendedLeavesBlock extends TransparentLeavesBlock {
	public static final int MAX_EXTENDED_DISTANCE = 7;
	public static final int MAX_TOTAL_DISTANCE = MAX_DISTANCE + MAX_EXTENDED_DISTANCE;
	public static final IntProperty EXTENDED_DISTANCE = IntProperty.of("extended_distance", 0, MAX_EXTENDED_DISTANCE);

	public ExtendedLeavesBlock(AbstractBlock.Settings settings) {
		super(settings);

		this.setDefaultState(this.stateManager.getDefaultState()
				.with(DISTANCE, MAX_DISTANCE)
				.with(EXTENDED_DISTANCE, MAX_EXTENDED_DISTANCE)
				.with(PERSISTENT, false)
				.with(WATERLOGGED, false));
	}



	@Override
	public boolean hasRandomTicks(BlockState state) {
		return getExtendedDistance(state) == MAX_TOTAL_DISTANCE && !state.get(PERSISTENT);
	}

	@Override
	public boolean shouldDecay(BlockState state) {
		return !state.get(PERSISTENT) && getExtendedDistance(state) == MAX_TOTAL_DISTANCE;
	}

	@Override
	public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		world.setBlockState(pos, ExtendedLeavesBlock.updateDistanceFromLogs(state, world, pos), 3);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (state.get(WATERLOGGED)) {
			world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
		}

		int distance = ExtendedLeavesBlock.getDistanceFromLog(neighborState) + 1;
		if (distance != 1 || getExtendedDistance(state) != distance) {
			world.scheduleBlockTick(pos, this, 1);
		}

		return state;
	}

	private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
		int distance = MAX_TOTAL_DISTANCE;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (Direction direction : Direction.values()) {
			mutable.set(pos, direction);
			distance = Math.min(distance, ExtendedLeavesBlock.getDistanceFromLog(world.getBlockState(mutable)) + 1);
			if (distance == 1) {
				break;
			}
		}

		return setExtendedDistance(state, distance);
	}

	private static int getDistanceFromLog(BlockState state) {
		return ExtendedLeavesBlock.getOptionalDistanceFromLog(state).orElse(MAX_TOTAL_DISTANCE);
	}

	/**
	 * <p>
	 * Get an {@link OptionalInt} containing the previously calculated distance from the provided leaves block state
	 * to the nearest log.  The value will be between 0 and 14 inclusive.
	 * </p><p>
	 * A value of 0 indicates the block is a log (including a {@link SmallLogBlock} which may also have leaves).
	 * A value of 14 indicates the leaves should decay and will schedule random ticks.
	 * No value indicates the block state does not contain the {@link LeavesBlock#DISTANCE} property.
	 * </p>
	 *
	 * @param state Target block state for which to fetch extended optional distance
	 * @return OptionalInt of the previously calculated distance, if present
	 */
	public static OptionalInt getOptionalDistanceFromLog(BlockState state) {
		if (state.isIn(BlockTags.LOGS)) {
			return OptionalInt.of(0);
		}

		Block block = state.getBlock();
		if (block instanceof ExtendedLeavesBlock) {
			return OptionalInt.of(getExtendedDistance(state));
		} else if (state.contains(DISTANCE)) {
			int distance = state.get(DISTANCE);
			return OptionalInt.of(distance < LeavesBlock.MAX_DISTANCE ? distance : MAX_TOTAL_DISTANCE);
		}

		return OptionalInt.empty();
	}

	/**
	 * <p>
	 * Get the extended distance value for the targeted leaves block state.  It is the caller's responsibility to
	 * determine whether the block state is extended or not.
	 * </p><p>
	 * If the target block state is of {@link ExtendedLeavesBlock} or a descendant, the value will be between 1 and 14
	 * inclusive, and a value of 14 indicates the leaves should decay and will schedule random ticks.
	 * </p><p>
	 * Otherwise, if the target block state contains the {@link LeavesBlock#DISTANCE} property, the value will be
	 * between 1 and 7, and a value of 7 indicates the leaves should decay and will schedule random ticks.
	 * </p>
	 *
	 * @param state Target block state for which to fetch extended distance
	 * @return Extended distance value of the target block state
	 */
	public static int getExtendedDistance(BlockState state) {
		return state.get(DISTANCE) + state.getOrEmpty(EXTENDED_DISTANCE).orElse(0);
	}

	/**
	 * <p>
	 * Set the extended distance value for the targeted leaves block state.  It is the caller's responsibility to
	 * determine whether the block state is extended or not.  Passing an out-of-bounds value will throw an exception.
	 * </p><p>
	 * If the target block state is of {@link ExtendedLeavesBlock} or a descendant, the value must be between 1 and 14
	 * inclusive, and a value of 14 indicates the leaves should decay and will schedule random ticks.
	 * </p><p>
	 * Otherwise, if the target block state contains the {@link LeavesBlock#DISTANCE} property, the value must be
	 * between 1 and 7, and a value of 7 indicates the leaves should decay and will schedule random ticks.
	 * </p>
	 *
	 * @param state The target block state for which to update extended distance
	 * @param distance The extended distance to set for the target block state
	 * @return The modified block state
	 */
	public static BlockState setExtendedDistance(BlockState state, int distance) {
		if (state.contains(EXTENDED_DISTANCE)) {
			if (distance > MAX_DISTANCE) {
				return state.with(DISTANCE, MAX_DISTANCE).with(EXTENDED_DISTANCE, distance - MAX_DISTANCE);
			} else {
				return state.with(DISTANCE, distance).with(EXTENDED_DISTANCE, 0);
			}
		} else {
			return state.with(DISTANCE, distance);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(EXTENDED_DISTANCE);
	}

	@Override
	public BlockState getPlacementState(ItemPlacementContext context) {
		FluidState fluidState = context.getWorld().getFluidState(context.getBlockPos());
		BlockState blockState = this.getDefaultState().with(PERSISTENT, true).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);

		return ExtendedLeavesBlock.updateDistanceFromLogs(blockState, context.getWorld(), context.getBlockPos());
	}

	@Override
	public boolean isSideInvisible(BlockState state, BlockState neighborState, Direction offset) {
		// OptiLeaves optimization: Cull faces with leaf block neighbors to reduce geometry in redwood forests
		return neighborState.getBlock() instanceof ExtendedLeavesBlock;
	}
}
