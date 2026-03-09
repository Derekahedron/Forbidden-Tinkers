package derekahedron.forbiddentinkers.datagen.tags;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlockTags;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.util.ForgeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class FTBlockTagsProvider extends BlockTagsProvider {

    public FTBlockTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s Block Tags", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(BlockTags.BEACON_BASE_BLOCKS)
                .add(FTBlocks.TINKERS_DIAMOND_BLOCK.get())
                .add(FTBlocks.CHAMPIUM_BLOCK.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(FTBlocks.TINKERS_DIAMOND_BLOCK.get())
                .add(FTBlocks.TINKERS_DIAMOND_ORE.get())
                .add(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get())
                .addTag(FTBlockTags.BEDROCK_BRICKS)
                .add(FTBlocks.CHAMPIUM_FORGE.get())
                .add(FTBlocks.CHAMPIUM_BLOCK.get())
                .add(FTBlocks.CHAMPIUM_ORE.get())
                .add(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get())
                .add(FTBlocks.BEDROCK_CHAMPIUM_ORE.get())
                .add(FTBlocks.RAW_CHAMPIUM_BLOCK.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(FTBlocks.TINKERS_DIAMOND_BLOCK.get())
                .add(FTBlocks.TINKERS_DIAMOND_ORE.get())
                .add(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get());

        tag(Tags.Blocks.NEEDS_NETHERITE_TOOL)
                .add(FTBlocks.CHAMPIUM_BLOCK.get())
                .add(FTBlocks.RAW_CHAMPIUM_BLOCK.get());

        tag(BlockTags.STAIRS)
                .add(FTBlocks.BEDROCK_BRICK_STAIRS.get());

        tag(BlockTags.SLABS)
                .add(FTBlocks.BEDROCK_BRICK_SLAB.get());

        tag(BlockTags.WALLS)
                .add(FTBlocks.BEDROCK_BRICK_WALL.get());

        tag(ForgeTags.Blocks.TINKERS_DIAMOND_BLOCKS)
                .add(FTBlocks.TINKERS_DIAMOND_BLOCK.get());
        tag(ForgeTags.Blocks.TINKERS_DIAMOND_ORES)
                .add(FTBlocks.TINKERS_DIAMOND_ORE.get())
                .add(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get());

        tag(ForgeTags.Blocks.CHAMPIUM_BLOCKS)
                .add(FTBlocks.CHAMPIUM_BLOCK.get());
        tag(ForgeTags.Blocks.CHAMPIUM_ORES)
                .add(FTBlocks.CHAMPIUM_ORE.get())
                .add(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get())
                .add(FTBlocks.BEDROCK_CHAMPIUM_ORE.get());
        tag(ForgeTags.Blocks.RAW_CHAMPIUM_BLOCKS)
                .add(FTBlocks.RAW_CHAMPIUM_BLOCK.get());

        tag(Tags.Blocks.ORES)
                .addTag(ForgeTags.Blocks.TINKERS_DIAMOND_ORES)
                .addTag(ForgeTags.Blocks.CHAMPIUM_ORES);

        tag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(ForgeTags.Blocks.CHAMPIUM_BLOCKS)
                .addTag(ForgeTags.Blocks.RAW_CHAMPIUM_BLOCKS);

        tag(TinkerTags.Blocks.ANVIL_METAL)
                .addTag(ForgeTags.Blocks.CHAMPIUM_BLOCKS);

        tag(FTBlockTags.NEEDS_CHAMPIUM_TOOL)
                .addTag(FTBlockTags.BEDROCK_BRICKS)
                .add(FTBlocks.CHAMPIUM_FORGE.get())
                .add(FTBlocks.CHAMPIUM_ORE.get())
                .add(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get())
                .add(FTBlocks.BEDROCK_CHAMPIUM_ORE.get());

        tag(FTBlockTags.BEDROCK_BRICKS)
                .add(FTBlocks.BEDROCK_BRICKS.get())
                .add(FTBlocks.BEDROCK_BRICK_STAIRS.get())
                .add(FTBlocks.BEDROCK_BRICK_SLAB.get())
                .add(FTBlocks.BEDROCK_BRICK_WALL.get());
    }
}
