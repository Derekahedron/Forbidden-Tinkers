package derekahedron.forbiddentinkers.block.entity;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<BlockEntityType<ChampiumForgeBlockEntity>> CHAMPIUM_FORGE =
            BLOCK_ENTITY_TYPES.register("champium_forge", () ->
                    BlockEntityType.Builder.of(
                            ChampiumForgeBlockEntity::new,
                            FTBlocks.CHAMPIUM_FORGE.get())
                            .build(null));
}
