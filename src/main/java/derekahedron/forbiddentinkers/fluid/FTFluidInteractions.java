package derekahedron.forbiddentinkers.fluid;

import derekahedron.mythictinkers.fluid.MTFluidTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class FTFluidInteractions {

    public static void initialize(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            FluidInteractionRegistry.addInteraction(FTFluidTypes.MOLTEN_CHAMPIUM.get(),
                    new FluidInteractionRegistry.InteractionInformation(
                            (level, currentPos, relativePos, currentState) ->
                                    level.getFluidState(relativePos).is(MTFluidTags.COOLS_MOLTEN_ORE),
                            Blocks.COBBLESTONE.defaultBlockState()));
        });
    }
}
