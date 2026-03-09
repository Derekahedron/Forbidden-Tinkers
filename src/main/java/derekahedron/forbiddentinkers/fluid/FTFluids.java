package derekahedron.forbiddentinkers.fluid;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.particle.FTParticleTypes;
import derekahedron.forbiddentinkers.sound.FTSoundEvents;
import derekahedron.mythictinkers.fluid.MoltenOreFluid;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTFluids {
    public static final DeferredRegister<Fluid> FLUIDS =
            DeferredRegister.create(ForgeRegistries.FLUIDS, ForbiddenTinkers.MOD_ID);

    // Molten Champium
    public static final RegistryObject<FlowingFluid> MOLTEN_CHAMPIUM =
            FLUIDS.register("molten_champium", () ->
                    new MoltenOreFluid.Source(
                            FTFluidHolders.MOLTEN_CHAMPIUM.properties()
                                    .bucket(FTItems.MOLTEN_CHAMPIUM_BUCKET)
                                    .block(FTBlocks.MOLTEN_CHAMPIUM)
                                    .slopeFindDistance(2)
                                    .levelDecreasePerBlock(2)
                                    .tickRate(20),
                            FTParticleTypes.MOLTEN_CHAMPIUM::get,
                            FTParticleTypes.DRIPPING_MOLTEN_CHAMPIUM::get,
                            FTSoundEvents.MOLTEN_CHAMPIUM_AMBIENT,
                            FTSoundEvents.MOLTEN_CHAMPIUM_POP));
    public static final RegistryObject<FlowingFluid> MOLTEN_CHAMPIUM_FLOWING =
            FLUIDS.register("molten_champium_flowing", () ->
                    new MoltenOreFluid.Flowing(
                            FTFluidHolders.MOLTEN_CHAMPIUM.properties()
                                    .bucket(FTItems.MOLTEN_CHAMPIUM_BUCKET)
                                    .block(FTBlocks.MOLTEN_CHAMPIUM)
                                    .slopeFindDistance(2)
                                    .levelDecreasePerBlock(2)
                                    .tickRate(20),
                            FTParticleTypes.MOLTEN_CHAMPIUM::get,
                            FTParticleTypes.DRIPPING_MOLTEN_CHAMPIUM::get,
                            FTSoundEvents.MOLTEN_CHAMPIUM_AMBIENT,
                            FTSoundEvents.MOLTEN_CHAMPIUM_POP));
}
