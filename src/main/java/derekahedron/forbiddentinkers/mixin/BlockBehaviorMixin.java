package derekahedron.forbiddentinkers.mixin;

import derekahedron.forbiddentinkers.block.BreakableUnbreakable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {

    @ModifyVariable(
            method = "getDestroyProgress",
            at = @At("STORE"))
    private float getNewDestroySpeed(float destroySpeed, BlockState state, Player player, BlockGetter level, BlockPos pos) {
        if (state.getBlock() instanceof BreakableUnbreakable breakableUnbreakable) {
            return breakableUnbreakable.getDestroySpeed(state, player, level, pos);
        }
        return destroySpeed;
    }
}
