package derekahedron.forbiddentinkers.worldgen.structure;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FTStructurePieceTypes {
    public static final DeferredRegister<StructurePieceType> STRUCTURE_PIECE_TYPES
            = DeferredRegister.create(Registries.STRUCTURE_PIECE, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<StructurePieceType> CHAMPIUM_FORGE =
            STRUCTURE_PIECE_TYPES.register("champium_forge", () -> ChampiumForgeStructurePiece::create);
}
