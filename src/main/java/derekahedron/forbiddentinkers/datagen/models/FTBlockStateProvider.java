package derekahedron.forbiddentinkers.datagen.models;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.ChampiumForgeBlock;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.mythictinkers.block.MTBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FTBlockStateProvider extends BlockStateProvider {

    public FTBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    @Override
    @SuppressWarnings("DataFlowIssue")
    protected void registerStatesAndModels() {
        // Augmentation Gem
        simpleBlockWithItem(FTBlocks.TINKERS_DIAMOND_BLOCK.get(),
                cubeAll(FTBlocks.TINKERS_DIAMOND_BLOCK.getId()));
        simpleBlockWithItem(FTBlocks.TINKERS_DIAMOND_ORE.get(),
                cubeAll(FTBlocks.TINKERS_DIAMOND_ORE.getId()));
        simpleBlockWithItem(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get(),
                cubeColumn(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.getId()));

        // Bedrock Bricks
        simpleBlockWithItem(FTBlocks.BEDROCK_BRICKS.get(),
                cubeAll(FTBlocks.BEDROCK_BRICKS.getId()));
        stairsBlock(FTBlocks.BEDROCK_BRICK_STAIRS.get(),
                FTBlocks.BEDROCK_BRICKS.getId().withPrefix("block/"));
        simpleBlockItem(
                FTBlocks.BEDROCK_BRICK_STAIRS.get(),
                models().getExistingFile(FTItems.BEDROCK_BRICK_STAIRS.getId()));
        slabBlock(FTBlocks.BEDROCK_BRICK_SLAB.get(),
                FTBlocks.BEDROCK_BRICKS.getId().withPrefix("block/"),
                FTBlocks.BEDROCK_BRICKS.getId().withPrefix("block/"));
        simpleBlockItem(
                FTBlocks.BEDROCK_BRICK_SLAB.get(),
                models().getExistingFile(FTItems.BEDROCK_BRICK_SLAB.getId()));
        wallBlock(FTBlocks.BEDROCK_BRICK_WALL.get(),
                FTBlocks.BEDROCK_BRICKS.getId().withPrefix("block/"));
        simpleBlockItem(FTBlocks.BEDROCK_BRICK_WALL.get(),
                models().wallInventory(
                        FTBlocks.BEDROCK_BRICK_WALL.getId().getPath(),
                        FTBlocks.BEDROCK_BRICKS.getId().withPrefix("block/")));

        // Champium
        getVariantBuilder(FTBlocks.CHAMPIUM_FORGE.get())
                .partialState().with(ChampiumForgeBlock.STATE, ChampiumForgeBlock.State.INACTIVE).addModels(
                        new ConfiguredModel(cubeColumn(FTBlocks.CHAMPIUM_FORGE.getId())))
                .partialState().with(ChampiumForgeBlock.STATE, ChampiumForgeBlock.State.ACTIVE).addModels(
                        new ConfiguredModel(cubeColumn(
                                FTBlocks.CHAMPIUM_FORGE.getId().withSuffix("_on"),
                                FTBlocks.CHAMPIUM_FORGE.getId().withSuffix("_top"))))
                .partialState().with(ChampiumForgeBlock.STATE, ChampiumForgeBlock.State.BROKEN).addModels(
                        new ConfiguredModel(cubeColumn(
                                FTBlocks.CHAMPIUM_FORGE.getId().withSuffix("_broken"),
                                FTBlocks.CHAMPIUM_FORGE.getId().withSuffix("_top"))));
        simpleBlockItem(FTBlocks.CHAMPIUM_FORGE.get(),
                models().getExistingFile(FTBlocks.CHAMPIUM_FORGE.getId()));

        // Champium
        simpleBlockWithItem(FTBlocks.CHAMPIUM_BLOCK.get(),
                cubeAll(FTBlocks.CHAMPIUM_BLOCK.getId()));
        simpleBlockWithItem(FTBlocks.CHAMPIUM_ORE.get(),
                cubeAll(FTBlocks.CHAMPIUM_ORE.getId()));
        simpleBlockWithItem(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get(),
                cubeColumn(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.getId()));
        simpleBlockWithItem(FTBlocks.BEDROCK_CHAMPIUM_ORE.get(),
                cubeAll(FTBlocks.BEDROCK_CHAMPIUM_ORE.getId()));
        simpleBlockWithItem(FTBlocks.RAW_CHAMPIUM_BLOCK.get(),
                cubeAll(FTBlocks.RAW_CHAMPIUM_BLOCK.getId()));
        simpleBlock(FTBlocks.MOLTEN_CHAMPIUM.get(),
                fluid(FTBlocks.MOLTEN_CHAMPIUM.getId()));
    }

    public BlockModelBuilder cubeAll(ResourceLocation id) {
        return models().cubeAll(
                id.getPath(),
                id.withPrefix("block/"));
    }

    public BlockModelBuilder cubeColumn(ResourceLocation id) {
        return cubeColumn(id, id.withSuffix("_top"));
    }

    public BlockModelBuilder cubeColumn(ResourceLocation id, ResourceLocation topId) {
        return models().cubeColumn(
                id.getPath(),
                id.withPrefix("block/"),
                topId.withPrefix("block/"));
    }

    public BlockModelBuilder fluid(ResourceLocation id) {
        return models().getBuilder(id.getPath())
                .texture("particle", id.withPrefix("block/").withSuffix("_still"));
    }
}
