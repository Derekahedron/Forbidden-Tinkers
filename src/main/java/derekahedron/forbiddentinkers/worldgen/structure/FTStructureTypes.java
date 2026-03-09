package derekahedron.forbiddentinkers.worldgen.structure;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FTStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES =
            DeferredRegister.create(Registries.STRUCTURE_TYPE, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<StructureType<ChampiumForgeStructure>> CHAMPIUM_FORGE =
            STRUCTURE_TYPES.register("champium_forge", () ->
                    () -> ChampiumForgeStructure.CODEC);
}
