package derekahedron.forbiddentinkers.datagen.tags;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.fluid.FTFluidTags;
import derekahedron.forbiddentinkers.fluid.FTFluids;
import derekahedron.forbiddentinkers.util.ForgeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class FTFluidTagsProvider extends FluidTagsProvider {

    public FTFluidTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s Fluid Tags", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {

        tag(FTFluidTags.MOLTEN_CHAMPIUM)
                .add(FTFluids.MOLTEN_CHAMPIUM.get())
                .add(FTFluids.MOLTEN_CHAMPIUM_FLOWING.get());
        tag(ForgeTags.Fluids.MOLTEN_CHAMPIUM)
                .addTag(FTFluidTags.MOLTEN_CHAMPIUM);

        tag(TinkerTags.Fluids.METAL_TOOLTIPS)
                .addTag(ForgeTags.Fluids.MOLTEN_CHAMPIUM);
    }
}
