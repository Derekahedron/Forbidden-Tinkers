package derekahedron.forbiddentinkers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;

public class BreakableBedrockWallBlock extends WallBlock implements BreakableUnbreakable {
    public static final BooleanProperty UNBREAKABLE = BreakableBedrockBlock.UNBREAKABLE;

    public final float destroySpeed;

    public BreakableBedrockWallBlock(float destroySpeed, Properties properties) {
        super(properties);
        this.destroySpeed = destroySpeed;

        registerDefaultState(defaultBlockState()
                .setValue(UNBREAKABLE, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(UNBREAKABLE);
    }

    @Override
    public float getDestroySpeed(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (state.getValue(UNBREAKABLE) && !ForgeHooks.isCorrectToolForDrops(state, player)) {
            return state.getDestroySpeed(level, pos);
        } else {
            return destroySpeed;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        state = state.setValue(BreakableBedrockBlock.UNBREAKABLE, stateDefinition.any().getValue(BreakableBedrockBlock.UNBREAKABLE));
        return super.getShape(state, level, pos, context);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        state = state.setValue(BreakableBedrockBlock.UNBREAKABLE, stateDefinition.any().getValue(BreakableBedrockBlock.UNBREAKABLE));
        return super.getCollisionShape(state, level, pos, context);
    }
}
