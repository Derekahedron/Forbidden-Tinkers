package derekahedron.forbiddentinkers.worldgen.structure.processor;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FTStructureProcessorTypes {
    public static final DeferredRegister<StructureProcessorType<?>> STRUCTURE_PROCESSOR_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_PROCESSOR, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<StructureProcessorType<ChampiumForgeProcessor>> CHAMPIUM_FORGE =
            STRUCTURE_PROCESSOR_TYPES.register("champium_forge", () ->
                    () -> ChampiumForgeProcessor.CODEC);
}
