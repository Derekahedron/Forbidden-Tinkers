package derekahedron.forbiddentinkers.fluid;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class FTFluidTags {

    public static final TagKey<Fluid> MOLTEN_CHAMPIUM =
            FluidTags.create(FTUtil.location("molten_champium"));
}
