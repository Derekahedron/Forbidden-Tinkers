package derekahedron.forbiddentinkers.fluid;

import derekahedron.mythictinkers.fluid.FluidHolder;
import net.minecraft.world.level.material.FlowingFluid;

public class FTFluidHolders {

    public static final FluidHolder<FlowingFluid> MOLTEN_CHAMPIUM =
            new FluidHolder<>(
                    FTFluidTypes.MOLTEN_CHAMPIUM,
                    FTFluids.MOLTEN_CHAMPIUM,
                    FTFluids.MOLTEN_CHAMPIUM_FLOWING);
}
