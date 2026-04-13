package derekahedron.forbiddentinkers.worldgen;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class FTPlacedFeatures {
    public static final ResourceKey<PlacedFeature> TINKERS_DIAMOND_ORE_SMALL =
            of("tinkers_diamond_ore_small");
    public static final ResourceKey<PlacedFeature> TINKERS_DIAMOND_ORE =
            of("tinkers_diamond_ore");
    public static final ResourceKey<PlacedFeature> CHAMPIUM_ORE =
            of("champium_ore");
    public static final ResourceKey<PlacedFeature> BEDROCK_CHAMPIUM_ORE =
            of("bedrock_champium_ore");

    public static ResourceKey<PlacedFeature> of(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, FTUtil.location(id));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        context.register(TINKERS_DIAMOND_ORE_SMALL, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(FTConfiguredFeatures.TINKERS_DIAMOND_ORE),
                List.of(
                        CountPlacement.of(1),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.bottom(),
                                VerticalAnchor.absolute(64)),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())));
        context.register(TINKERS_DIAMOND_ORE, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(FTConfiguredFeatures.TINKERS_DIAMOND_ORE),
                List.of(
                        CountPlacement.of(1),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-48),
                                VerticalAnchor.aboveBottom(48)),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())));

        context.register(CHAMPIUM_ORE, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(FTConfiguredFeatures.CHAMPIUM_ORE),
                List.of(
                        RarityFilter.onAverageOnceEvery(16),
                        HeightRangePlacement.triangle(
                                VerticalAnchor.aboveBottom(-64),
                                VerticalAnchor.aboveBottom(64)),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())));
        context.register(BEDROCK_CHAMPIUM_ORE, new PlacedFeature(
                context.lookup(Registries.CONFIGURED_FEATURE)
                        .getOrThrow(FTConfiguredFeatures.BEDROCK_CHAMPIUM_ORE),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.aboveBottom(1),
                                VerticalAnchor.aboveBottom(4)),
                        InSquarePlacement.spread(),
                        BiomeFilter.biome())));
    }
}
