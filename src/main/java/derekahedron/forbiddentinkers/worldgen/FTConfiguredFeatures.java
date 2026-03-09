package derekahedron.forbiddentinkers.worldgen;

import derekahedron.forbiddentinkers.block.BreakableBedrockBlock;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.util.FTUtil;
import derekahedron.mythictinkers.worldgen.feature.MTFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class FTConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> TINKERS_DIAMOND_ORE =
            of("tinkers_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHAMPIUM_ORE =
            of("champium_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEDROCK_CHAMPIUM_ORE =
            of("bedrock_champium_ore");

    public static ResourceKey<ConfiguredFeature<?, ?>> of(String id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FTUtil.location(id));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        context.register(TINKERS_DIAMOND_ORE, new ConfiguredFeature<>(
                Feature.REPLACE_SINGLE_BLOCK,
                new ReplaceBlockConfiguration(List.of(
                        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), FTBlocks.TINKERS_DIAMOND_ORE.get().defaultBlockState()),
                        OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get().defaultBlockState())))));

        context.register(CHAMPIUM_ORE, new ConfiguredFeature<>(
                MTFeatures.REPLACE_UNEXPOSED_BLOCK.get(),
                new ReplaceBlockConfiguration(List.of(
                        OreConfiguration.target(new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES), FTBlocks.CHAMPIUM_ORE.get().defaultBlockState()),
                        OreConfiguration.target(new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES), FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get().defaultBlockState())))));
        context.register(BEDROCK_CHAMPIUM_ORE, new ConfiguredFeature<>(
                Feature.REPLACE_SINGLE_BLOCK,
                new ReplaceBlockConfiguration(List.of(
                        OreConfiguration.target(new BlockMatchTest(Blocks.BEDROCK), FTBlocks.BEDROCK_CHAMPIUM_ORE.get().defaultBlockState()
                                .setValue(BreakableBedrockBlock.UNBREAKABLE, true))))));
    }
}
