package derekahedron.forbiddentinkers.block;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.block.entity.FTBlockEntityTypes;
import derekahedron.forbiddentinkers.particle.FTParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ChampiumForgeBlock extends BaseEntityBlock implements EntityBlock, BreakableUnbreakable {
    public static final StateProperty STATE = StateProperty.create("state");
    public static final IntProvider XP_PROVIDER = UniformInt.of(45, 135);
    public static final float SMOKE_CHANCE = 0.1F;
    public static final double SMOKE_SPEED = 0.04D;
    public static final double SMOKE_SPEED_VARIATION = 0.025D;
    public static final float BREAK_SPEED = 100.0F;

    public ChampiumForgeBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState()
                .setValue(STATE, State.INACTIVE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(STATE);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ChampiumForgeBlockEntity(pos, state);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide()) {
            if (level.getBlockEntity(pos) instanceof ChampiumForgeBlockEntity blockEntity) {
                blockEntity.refreshRecipe();
                NetworkHooks.openScreen((ServerPlayer) player, blockEntity, buf -> {
                    buf.writeBlockPos(pos);
                    buf.writeOptional(
                            Optional.ofNullable(blockEntity.recipeId),
                            FriendlyByteBuf::writeResourceLocation);
                    buf.writeOptional(
                            Optional.ofNullable(blockEntity.maxUses),
                            FriendlyByteBuf::writeInt);
                });
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (stack.hasCustomHoverName()) {
            if (level.getBlockEntity(pos) instanceof ChampiumForgeBlockEntity blockEntity) {
                blockEntity.setCustomName(stack.getHoverName());
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        if (level.getBlockEntity(pos) instanceof ChampiumForgeBlockEntity blockEntity) {
            return blockEntity.getRedstoneSignal();
        } else {
            return 0;
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> entityType) {
        if (!level.isClientSide()) {
            return createTickerHelper(entityType, FTBlockEntityTypes.CHAMPIUM_FORGE.get(), ChampiumForgeBlockEntity::tick);
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState oldState, Level level, BlockPos pos, BlockState newState, boolean moved) {
        if (!oldState.is(newState.getBlock())) {
            if (level.getBlockEntity(pos) instanceof ChampiumForgeBlockEntity blockEntity) {
                if (level instanceof ServerLevel) {
                    Containers.dropContents(level, pos, blockEntity);
                }

                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(oldState, level, pos, newState, moved);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(STATE) == State.BROKEN) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                if (random.nextFloat() < SMOKE_CHANCE) {
                    addSmokeParticle(level, pos, FTParticleTypes.BROKEN_CHAMPIUM_FORGE_SMOKE.get(), direction);
                }
            }
        }
    }

    public static void addSmokeParticle(Level level, BlockPos pos, ParticleOptions particle, Direction direction) {
        double x = pos.getX() + 0.5D;
        double y = pos.getY() + 0.3D;
        double z = pos.getZ() + 0.5D;
        Vec3i normal = direction.getNormal();
        x += normal.getX() * 0.51D;
        z += normal.getZ() * 0.51D;
        level.addParticle(
                particle,
                x + normal.getZ() * Mth.lerp(level.random.nextDouble(), -0.2D, 0.2D),
                y + Mth.lerp(level.random.nextDouble(), -0.1D, 0.1D),
                z + normal.getX() * Mth.lerp(level.random.nextDouble(), -0.2D, 0.2D),
                normal.getX() * SMOKE_SPEED + Mth.lerp(level.random.nextDouble(), -SMOKE_SPEED_VARIATION, SMOKE_SPEED_VARIATION),
                Mth.lerp(level.random.nextDouble(), -SMOKE_SPEED_VARIATION, SMOKE_SPEED_VARIATION),
                normal.getZ() * SMOKE_SPEED + Mth.lerp(level.random.nextDouble(), -SMOKE_SPEED_VARIATION, SMOKE_SPEED_VARIATION));
    }

    public int getExpDrop(BlockState state, LevelReader level, RandomSource random, BlockPos pos, int fortuneLevel, int silkTouchLevel) {
        return XP_PROVIDER.sample(random);
    }

    @Override
    public float getDestroySpeed(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (state.getValue(STATE) == State.BROKEN
                && ForgeHooks.isCorrectToolForDrops(state, player)) {
            return BREAK_SPEED;
        } else {
            return state.getDestroySpeed(level, pos);
        }
    }

    public enum State implements StringRepresentable {
        INACTIVE("inactive"),
        ACTIVE("active"),
        BROKEN("broken");

        private final String name;

        State(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static class StateProperty extends EnumProperty<State> {

        protected StateProperty(String name, Collection<State> values) {
            super(name, State.class, values);
        }

        public static StateProperty create(String name) {
            return new StateProperty(name, List.of(State.values()));
        }
    }
}
