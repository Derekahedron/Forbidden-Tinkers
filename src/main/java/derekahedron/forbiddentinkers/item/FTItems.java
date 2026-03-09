package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.fluid.FTFluidHolders;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<Item> CLOWN_NOSE =
            ITEMS.register("clown_nose", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(Rarity.UNCOMMON)));

    public static final RegistryObject<ForbiddenTomeItem> FORBIDDEN_TOME =
            ITEMS.register("forbidden_tome", () ->
                    new ForbiddenTomeItem(
                            new Item.Properties()
                                    .rarity(Rarity.UNCOMMON)
                                    .stacksTo(1)));

    public static final RegistryObject<BlockItem> TINKERS_DIAMOND_BLOCK =
            ITEMS.register("tinkers_diamond_block", () ->
                    new BlockItem(
                            FTBlocks.TINKERS_DIAMOND_BLOCK.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.TINKERS_DIAMOND)));
    public static final RegistryObject<BlockItem> TINKERS_DIAMOND_ORE =
            ITEMS.register("tinkers_diamond_ore", () ->
                    new BlockItem(
                            FTBlocks.TINKERS_DIAMOND_ORE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.TINKERS_DIAMOND)));
    public static final RegistryObject<BlockItem> DEEPSLATE_TINKERS_DIAMOND_ORE =
            ITEMS.register("deepslate_tinkers_diamond_ore", () ->
                    new BlockItem(
                            FTBlocks.DEEPSLATE_TINKERS_DIAMOND_ORE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.TINKERS_DIAMOND)));
    public static final RegistryObject<Item> TINKERS_DIAMOND =
            ITEMS.register("tinkers_diamond", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(FTRarity.TINKERS_DIAMOND)));

    public static final RegistryObject<HeartItem> HEART =
            ITEMS.register("heart", () ->
                    new HeartItem(
                            new Item.Properties()));

    // Bedrock Bricks
    public static final RegistryObject<BlockItem> BEDROCK_BRICKS =
            ITEMS.register("bedrock_bricks", () ->
                    new BlockItem(
                            FTBlocks.BEDROCK_BRICKS.get(),
                            new Item.Properties()));
    public static final RegistryObject<BlockItem> BEDROCK_BRICK_STAIRS =
            ITEMS.register("bedrock_brick_stairs", () ->
                    new BlockItem(
                            FTBlocks.BEDROCK_BRICK_STAIRS.get(),
                            new Item.Properties()));
    public static final RegistryObject<BlockItem> BEDROCK_BRICK_SLAB =
            ITEMS.register("bedrock_brick_slab", () ->
                    new BlockItem(
                            FTBlocks.BEDROCK_BRICK_SLAB.get(),
                            new Item.Properties()));
    public static final RegistryObject<BlockItem> BEDROCK_BRICK_WALL =
            ITEMS.register("bedrock_brick_wall", () ->
                    new BlockItem(
                            FTBlocks.BEDROCK_BRICK_WALL.get(),
                            new Item.Properties()));

    // Champium
    public static final RegistryObject<BlockItem> CHAMPIUM_FORGE =
            ITEMS.register("champium_forge", () ->
                    new BlockItem(
                            FTBlocks.CHAMPIUM_FORGE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BlockItem> CHAMPIUM_BLOCK =
            ITEMS.register("champium_block", () ->
                    new BlockItem(
                            FTBlocks.CHAMPIUM_BLOCK.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BlockItem> CHAMPIUM_ORE =
            ITEMS.register("champium_ore", () ->
                    new BlockItem(
                            FTBlocks.CHAMPIUM_ORE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BlockItem> DEEPSLATE_CHAMPIUM_ORE =
            ITEMS.register("deepslate_champium_ore", () ->
                    new BlockItem(
                            FTBlocks.DEEPSLATE_CHAMPIUM_ORE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BlockItem> BEDROCK_CHAMPIUM_ORE =
            ITEMS.register("bedrock_champium_ore", () ->
                    new BlockItem(
                            FTBlocks.BEDROCK_CHAMPIUM_ORE.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BlockItem> RAW_CHAMPIUM_BLOCK =
            ITEMS.register("raw_champium_block", () ->
                    new BlockItem(
                            FTBlocks.RAW_CHAMPIUM_BLOCK.get(),
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<Item> RAW_CHAMPIUM =
            ITEMS.register("raw_champium", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<Item> CHAMPIUM_NUGGET =
            ITEMS.register("champium_nugget", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<Item> CHAMPIUM_INGOT =
            ITEMS.register("champium_ingot", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<BucketItem> MOLTEN_CHAMPIUM_BUCKET =
            ITEMS.register("molten_champium_bucket", () ->
                    new BucketItem(
                            FTFluidHolders.MOLTEN_CHAMPIUM.stillFluid,
                            new Item.Properties()
                                    .stacksTo(1)
                                    .rarity(FTRarity.CHAMPIUM)));
    public static final RegistryObject<Item> CHAMPIONS_MEDAL =
            ITEMS.register("champions_medal", () ->
                    new Item(
                            new Item.Properties()
                                    .rarity(FTRarity.CHAMPIUM)));
}
