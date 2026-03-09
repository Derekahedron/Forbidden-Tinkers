package derekahedron.forbiddentinkers.worldgen;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class FTBiomeModifiers {
    public static final ResourceKey<BiomeModifier> TINKERS_DIAMOND_ORE_SMALL =
            of("tinkers_diamond_ore_small");
    public static final ResourceKey<BiomeModifier> TINKERS_DIAMOND_ORE =
            of("tinkers_diamond_ore");
    public static final ResourceKey<BiomeModifier> CHAMPIUM_ORE =
            of("champium_ore");
    public static final ResourceKey<BiomeModifier> BEDROCK_CHAMPIUM_ORE =
            of("bedrock_champium_ore");

    public static ResourceKey<BiomeModifier> of(String id) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, FTUtil.location(id));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        context.register(TINKERS_DIAMOND_ORE_SMALL, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                context.lookup(Registries.BIOME)
                        .getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(FTPlacedFeatures.TINKERS_DIAMOND_ORE_SMALL)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(TINKERS_DIAMOND_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                context.lookup(Registries.BIOME)
                        .getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(FTPlacedFeatures.TINKERS_DIAMOND_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(CHAMPIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                context.lookup(Registries.BIOME)
                        .getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(FTPlacedFeatures.CHAMPIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
        context.register(BEDROCK_CHAMPIUM_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                context.lookup(Registries.BIOME)
                        .getOrThrow(BiomeTags.IS_OVERWORLD),
                HolderSet.direct(context.lookup(Registries.PLACED_FEATURE)
                        .getOrThrow(FTPlacedFeatures.BEDROCK_CHAMPIUM_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));
    }
}
