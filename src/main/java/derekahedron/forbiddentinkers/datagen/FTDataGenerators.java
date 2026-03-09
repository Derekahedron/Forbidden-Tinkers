package derekahedron.forbiddentinkers.datagen;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.damage.FTDamageTypes;
import derekahedron.forbiddentinkers.datagen.advancements.FTAdvancementProvider;
import derekahedron.forbiddentinkers.datagen.loot.FTLootTableProvider;
import derekahedron.forbiddentinkers.datagen.models.FTBlockStateProvider;
import derekahedron.forbiddentinkers.datagen.models.FTItemModelProvider;
import derekahedron.forbiddentinkers.datagen.recipes.FTRecipeProvider;
import derekahedron.forbiddentinkers.datagen.tags.*;
import derekahedron.forbiddentinkers.datagen.tinkers.*;
import derekahedron.forbiddentinkers.registry.FTRegistryKeys;
import derekahedron.forbiddentinkers.worldgen.FTBiomeModifiers;
import derekahedron.forbiddentinkers.worldgen.FTConfiguredFeatures;
import derekahedron.forbiddentinkers.worldgen.FTPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTDataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        RegistrySetBuilder registrySetBuilder = new RegistrySetBuilder();
        registrySetBuilder.add(Registries.DAMAGE_TYPE, FTDamageTypes::bootstrap);
        registrySetBuilder.add(Registries.CONFIGURED_FEATURE, FTConfiguredFeatures::bootstrap);
        registrySetBuilder.add(Registries.PLACED_FEATURE, FTPlacedFeatures::bootstrap);
        registrySetBuilder.add(ForgeRegistries.Keys.BIOME_MODIFIERS, FTBiomeModifiers::bootstrap);
        registrySetBuilder.add(FTRegistryKeys.DIVINE_SLOTS, FTDivineSlotsProvider::bootstrap);
        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(
                output, lookupProvider, registrySetBuilder,
                Set.of(ForbiddenTinkers.MOD_ID)));

        generator.addProvider(event.includeClient(), new FTBlockStateProvider(
                output, existingFileHelper));

        generator.addProvider(event.includeClient(), new FTItemModelProvider(
                output, existingFileHelper));

        AbstractMaterialSpriteProvider materialSpriteProvider = new FTMaterialSpriteProvider();

        generator.addProvider(event.includeClient(), new FTMaterialRenderInfoProvider(
                output, materialSpriteProvider, existingFileHelper));

        BlockTagsProvider blockTagsProvider =
                generator.addProvider(event.includeServer(), new FTBlockTagsProvider(
                        output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new FTItemTagsProvider(
                output, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new FTFluidTagsProvider(
                output, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new FTLootTableProvider(
                output));

        generator.addProvider(event.includeServer(), new FTRecipeProvider(
                output));

        generator.addProvider(event.includeServer(), new FTModifierRecipeProvider(
                output));

        generator.addProvider(event.includeServer(), new FTSmelteryRecipeProvider(
                output));

        generator.addProvider(event.includeServer(), new FTMaterialRecipeProvider(
                output));

        generator.addProvider(event.includeServer(), new FTModifierProvider(
                output));

        AbstractMaterialDataProvider materialProvider =
                generator.addProvider(event.includeServer(), new FTMaterialDataProvider(
                        output));

        generator.addProvider(event.includeServer(), new FTMaterialStatsDataProvider(
                output,
                materialProvider));

        generator.addProvider(event.includeServer(), new FTMaterialTraitDataProvider(
                output,
                materialProvider));

        generator.addProvider(event.includeServer(), new FTModifierTagsProvider(
                output,
                existingFileHelper));

        generator.addProvider(event.includeServer(), new FTMaterialTagsProvider(
                output,
                existingFileHelper));

        generator.addProvider(event.includeServer(), new FTAdvancementProvider(
                output,
                lookupProvider,
                existingFileHelper));
    }
}
