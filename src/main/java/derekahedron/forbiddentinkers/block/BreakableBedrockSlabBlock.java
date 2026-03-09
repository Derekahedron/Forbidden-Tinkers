package derekahedron.forbiddentinkers.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.common.ForgeHooks;

public class BreakableBedrockSlabBlock extends SlabBlock implements BreakableUnbreakable {
    public static final BooleanProperty UNBREAKABLE = BreakableBedrockBlock.UNBREAKABLE;

    public final float destroySpeed;

    public BreakableBedrockSlabBlock(float destroySpeed, Properties properties) {
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
}
