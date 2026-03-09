package derekahedron.forbiddentinkers.block;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.fluid.FTFluidHolders;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ForbiddenTinkers.MOD_ID);

    // Tinkers Diamond
    public static final RegistryObject<Block> TINKERS_DIAMOND_BLOCK =
            BLOCKS.register("tinkers_diamond_block", () ->
                    new Block(
                            BlockBehaviour.Properties.of()
                                    .mapColor(DyeColor.WHITE)
                                    .requiresCorrectToolForDrops()
                                    .strength(15.0F, 20.0F)
                                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> TINKERS_DIAMOND_ORE =
            BLOCKS.register("tinkers_diamond_ore", () ->
                    new DropExperienceBlock(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .instrument(NoteBlockInstrument.BASEDRUM)
                                    .requiresCorrectToolForDrops()
                                    .strength(3.0F, 3.0F),
                            UniformInt.of(4, 8)));
    public static final RegistryObject<Block> DEEPSLATE_TINKERS_DIAMOND_ORE =
            BLOCKS.register("deepslate_tinkers_diamond_ore", () ->
                    new DropExperienceBlock(
                            BlockBehaviour.Properties.copy(TINKERS_DIAMOND_ORE.get())
                                    .mapColor(MapColor.DEEPSLATE)
                                    .strength(4.5F, 3.0F)
                                    .sound(SoundType.DEEPSLATE),
                            UniformInt.of(4, 8)));

    // Bedrock Bricks
    public static final RegistryObject<BreakableBedrockBlock> BEDROCK_BRICKS =
            BLOCKS.register("bedrock_bricks", () ->
                    new BreakableBedrockBlock(
                            40.0F,
                            BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BreakableBedrockStairBlock> BEDROCK_BRICK_STAIRS =
            BLOCKS.register("bedrock_brick_stairs", () ->
                    new BreakableBedrockStairBlock(
                            () -> BEDROCK_BRICKS.get().defaultBlockState(),
                            40.0F,
                            BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BreakableBedrockSlabBlock> BEDROCK_BRICK_SLAB =
            BLOCKS.register("bedrock_brick_slab", () ->
                    new BreakableBedrockSlabBlock(
                            40.0F,
                            BlockBehaviour.Properties.copy(Blocks.BEDROCK)));
    public static final RegistryObject<BreakableBedrockWallBlock> BEDROCK_BRICK_WALL =
            BLOCKS.register("bedrock_brick_wall", () ->
                    new BreakableBedrockWallBlock(
                            40.0F,
                            BlockBehaviour.Properties.copy(Blocks.BEDROCK)));

    // Champium
    public static final RegistryObject<ChampiumForgeBlock> CHAMPIUM_FORGE =
            BLOCKS.register("champium_forge", () ->
                    new ChampiumForgeBlock(
                            BlockBehaviour.Properties.copy(Blocks.BEDROCK)
                                    .noLootTable()
                                    .lightLevel(state ->
                                            state.getValue(ChampiumForgeBlock.STATE) == ChampiumForgeBlock.State.ACTIVE
                                                    ? 15 : 0)));
    public static final RegistryObject<Block> CHAMPIUM_BLOCK =
            BLOCKS.register("champium_block", () ->
                    new Block(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.GOLD)
                                    .requiresCorrectToolForDrops()
                                    .strength(15.0F, 20.0F)
                                    .sound(SoundType.METAL)));
    public static final RegistryObject<Block> CHAMPIUM_ORE =
            BLOCKS.register("champium_ore", () ->
                    new Block(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.STONE)
                                    .instrument(NoteBlockInstrument.BASEDRUM)
                                    .requiresCorrectToolForDrops()
                                    .strength(12.0F, 3.0F)));
    public static final RegistryObject<Block> DEEPSLATE_CHAMPIUM_ORE =
            BLOCKS.register("deepslate_champium_ore", () ->
                    new Block(
                            BlockBehaviour.Properties.copy(CHAMPIUM_ORE.get())
                                    .mapColor(MapColor.DEEPSLATE)
                                    .strength(18.0F, 3.0F)
                                    .sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<BreakableBedrockBlock> BEDROCK_CHAMPIUM_ORE =
            BLOCKS.register("bedrock_champium_ore", () ->
                    new BreakableBedrockBlock(
                            100.0F,
                            BlockBehaviour.Properties.copy(CHAMPIUM_ORE.get())
                                    .strength(-1.0F, 3600000.0F)
                                    .isValidSpawn((state, level, pos, entityType) -> false)));
    public static final RegistryObject<Block> RAW_CHAMPIUM_BLOCK =
            BLOCKS.register("raw_champium_block", () ->
                    new Block(
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.GOLD)
                                    .requiresCorrectToolForDrops()
                                    .strength(15.0F, 20.0F)
                                    .sound(SoundType.METAL)));
    public static final RegistryObject<LiquidBlock> MOLTEN_CHAMPIUM =
            BLOCKS.register("molten_champium", () ->
                    new LiquidBlock(
                            FTFluidHolders.MOLTEN_CHAMPIUM.stillFluid,
                            BlockBehaviour.Properties.of()
                                    .mapColor(MapColor.GOLD)
                                    .noCollission()
                                    .strength(100.0F)
                                    .noLootTable()
                                    .replaceable()
                                    .liquid()
                                    .lightLevel((state) -> 15)
                                    .pushReaction(PushReaction.DESTROY)
                                    .sound(SoundType.EMPTY)));
}
