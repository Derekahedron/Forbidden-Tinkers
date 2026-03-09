package derekahedron.forbiddentinkers.datagen.loot;

import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.item.FTItems;
import com.google.common.collect.ImmutableSet;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class FTBlockLoot extends BlockLootSubProvider {
    public static final Set<Item> EXPLOSION_RESISTANT = ImmutableSet.of();

    public FTBlockLoot() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        // Augmentation Gem
        dropSelf(FTBlocks.TINKERS_DIAMOND_BLOCK.get());
        add(FTBlocks.TINKERS_DIAMOND_ORE.get(),
                createSingleItemTableWithSilkTouch(
                        FTBlocks.TINKERS_DIAMOND_ORE.get(),
                        FTItems.TINKERS_DIAMOND.get()));
        add(FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get(),
                createSingleItemTableWithSilkTouch(
                        FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get(),
                        FTItems.TINKERS_DIAMOND.get()));

        // Bedrock Bricks
        dropSelf(FTBlocks.BEDROCK_BRICKS.get());
        dropSelf(FTBlocks.BEDROCK_BRICK_STAIRS.get());
        add(FTBlocks.BEDROCK_BRICK_SLAB.get(),
                createSlabItemTable(FTBlocks.BEDROCK_BRICK_SLAB.get()));
        dropSelf(FTBlocks.BEDROCK_BRICK_WALL.get());

        // Champium
        dropSelf(FTBlocks.CHAMPIUM_BLOCK.get());
        add(FTBlocks.CHAMPIUM_ORE.get(),
                createSingleItemTableWithSilkTouch(
                        FTBlocks.CHAMPIUM_ORE.get(),
                        FTItems.RAW_CHAMPIUM.get()));
        add(FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get(),
                createSingleItemTableWithSilkTouch(
                        FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get(),
                        FTItems.RAW_CHAMPIUM.get()));
        add(FTBlocks.BEDROCK_CHAMPIUM_ORE.get(),
                createSingleItemTableWithSilkTouch(
                        FTBlocks.BEDROCK_CHAMPIUM_ORE.get(),
                        FTItems.RAW_CHAMPIUM.get()));
        dropSelf(FTBlocks.RAW_CHAMPIUM_BLOCK.get());

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FTBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
