package derekahedron.forbiddentinkers.datagen.tags;

import com.github.alexmodguy.alexscaves.server.block.ACBlockRegistry;
import com.github.alexmodguy.alexscaves.server.item.ACItemRegistry;
import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTItemTags;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.util.ForgeTags;
import derekahedron.invexp.item.InvExpItemTags;
import derekahedron.mythictinkers.item.MTItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.tools.TinkerToolParts;

import java.util.concurrent.CompletableFuture;

public class FTItemTagsProvider extends ItemTagsProvider {

    public FTItemTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            CompletableFuture<TagsProvider.TagLookup<Block>> contentsGetter,
            ExistingFileHelper exFileHelper) {
        super(output, lookupProvider, contentsGetter, ForbiddenTinkers.MOD_ID, exFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s Item Tags", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(FTItems.TINKERS_DIAMOND.get())
                .add(FTItems.CHAMPIUM_INGOT.get());

        tag(ItemTags.TRIM_MATERIALS)
                .add(FTItems.CHAMPIUM_INGOT.get());

        tag(ItemTags.STAIRS)
                .add(FTItems.BEDROCK_BRICK_STAIRS.get());

        tag(ItemTags.SLABS)
                .add(FTItems.BEDROCK_BRICK_SLAB.get());

        tag(ItemTags.WALLS)
                .add(FTItems.BEDROCK_BRICK_WALL.get());

        tag(ItemTags.BOOKSHELF_BOOKS)
                .add(FTItems.FORBIDDEN_TOME.get());

        tag(ForgeTags.Items.TINKERS_DIAMOND_BLOCKS)
                .add(FTItems.TINKERS_DIAMOND_BLOCK.get());
        tag(ForgeTags.Items.TINKERS_DIAMOND_ORES)
                .add(FTItems.TINKERS_DIAMOND_ORE.get())
                .add(FTItems.DEEPSLATE_TINKERS_DIAMOND_ORE.get());
        tag(ForgeTags.Items.TINKERS_DIAMOND)
                .add(FTItems.TINKERS_DIAMOND.get());

        tag(ForgeTags.Items.CHAMPIUM_BLOCKS)
                .add(FTItems.CHAMPIUM_BLOCK.get());
        tag(ForgeTags.Items.CHAMPIUM_ORES)
                .add(FTItems.CHAMPIUM_ORE.get())
                .add(FTItems.DEEPSLATE_CHAMPIUM_ORE.get())
                .add(FTItems.BEDROCK_CHAMPIUM_ORE.get());
        tag(ForgeTags.Items.RAW_CHAMPIUM_BLOCKS)
                .add(FTItems.RAW_CHAMPIUM_BLOCK.get());
        tag(ForgeTags.Items.RAW_CHAMPIUM)
                .add(FTItems.RAW_CHAMPIUM.get());
        tag(ForgeTags.Items.CHAMPIUM_NUGGETS)
                .add(FTItems.CHAMPIUM_NUGGET.get());
        tag(ForgeTags.Items.CHAMPIUM_INGOTS)
                .add(FTItems.CHAMPIUM_INGOT.get());

        tag(Tags.Items.ORES)
                .addTag(ForgeTags.Items.TINKERS_DIAMOND_ORES)
                .addTag(ForgeTags.Items.CHAMPIUM_ORES);

        tag(Tags.Items.STORAGE_BLOCKS)
                .addTag(ForgeTags.Items.CHAMPIUM_BLOCKS)
                .addTag(ForgeTags.Items.RAW_CHAMPIUM_BLOCKS);
        tag(Tags.Items.RAW_MATERIALS)
                .addTag(ForgeTags.Items.RAW_CHAMPIUM);
        tag(Tags.Items.NUGGETS)
                .addTag(ForgeTags.Items.CHAMPIUM_NUGGETS);
        tag(Tags.Items.INGOTS)
                .addTag(ForgeTags.Items.CHAMPIUM_INGOTS);

        tag(TinkerTags.Items.ANVIL_METAL)
                .addTag(ForgeTags.Items.CHAMPIUM_BLOCKS);

        tag(InvExpItemTags.SackType.METAL_BLOCK)
                .addTag(ForgeTags.Items.CHAMPIUM_BLOCKS);

        tag(InvExpItemTags.SackType.CRYSTAL_BLOCK)
                .addTag(ForgeTags.Items.TINKERS_DIAMOND_BLOCKS);

        tag(InvExpItemTags.SackType.ORE)
                .addTag(ForgeTags.Items.TINKERS_DIAMOND)
                .addTag(ForgeTags.Items.RAW_CHAMPIUM_BLOCKS)
                .addTag(ForgeTags.Items.RAW_CHAMPIUM)
                .addTag(ForgeTags.Items.CHAMPIUM_NUGGETS)
                .addTag(ForgeTags.Items.CHAMPIUM_INGOTS);

        tag(InvExpItemTags.SackType.BEDROCK)
                .addTag(FTItemTags.BEDROCK_BRICKS);

        tag(InvExpItemTags.SackType.BOOK)
                .add(FTItems.FORBIDDEN_TOME.get());

        tag(InvExpItemTags.SackWeight.FIFTH)
                .addTag(ForgeTags.Items.CHAMPIUM_NUGGETS);
        tag(InvExpItemTags.SackWeight.HALF)
                .add(FTItems.FORBIDDEN_TOME.get());

        tag(FTItemTags.BEDROCK_BRICKS)
                .add(FTItems.BEDROCK_BRICKS.get())
                .add(FTItems.BEDROCK_BRICK_STAIRS.get())
                .add(FTItems.BEDROCK_BRICK_SLAB.get())
                .add(FTItems.BEDROCK_BRICK_WALL.get());

        tag(FTItemTags.AUGMENTATION_PART_BLACKLIST)
                .add(TinkerToolParts.repairKit.get())
                .add(TinkerToolParts.fakeIngot.get())
                .add(TinkerToolParts.fakeStorageBlock.asItem())
                .addOptional(new ResourceLocation("tinkers_things:large_repair_kit"));

        tag(FTItemTags.DISPOSABLE)
                .add(Items.STONE)
                .add(Items.COBBLESTONE)
                .add(Items.MOSSY_COBBLESTONE)
                .add(Items.DEEPSLATE)
                .add(Items.COBBLED_DEEPSLATE)
                .add(Items.TUFF)
                .add(Items.GRANITE)
                .add(Items.DIORITE)
                .add(Items.ANDESITE)
                .add(Items.CALCITE)
                .add(Items.SMOOTH_BASALT)
                .add(Items.DRIPSTONE_BLOCK)
                .add(Items.POINTED_DRIPSTONE)
                .add(Items.AMETHYST_BLOCK)
                .add(Items.DIRT)
                .add(Items.GRAVEL)
                .add(Items.SAND)
                .add(Items.SANDSTONE)
                .add(Items.RED_SAND)
                .add(Items.RED_SANDSTONE)
                .add(Items.MUD)
                .add(Items.MOSS_BLOCK)
                .add(Items.MOSS_CARPET)
                .add(Items.NETHERRACK)
                .add(Items.NETHER_BRICK)
                .add(Items.BLACKSTONE)
                .add(Items.BASALT)
                .add(Items.MAGMA_BLOCK)
                .add(Items.SOUL_SAND)
                .add(Items.SOUL_SOIL)
                .add(Items.END_STONE)
                .add(Items.ROTTEN_FLESH)
                .add(Items.BONE)
                .add(Items.STRING)
                .add(Items.WOODEN_SWORD)
                .add(Items.WOODEN_SHOVEL)
                .add(Items.WOODEN_PICKAXE)
                .add(Items.WOODEN_AXE)
                .add(Items.WOODEN_HOE)
                .add(Items.LEATHER_HELMET)
                .add(Items.LEATHER_CHESTPLATE)
                .add(Items.LEATHER_LEGGINGS)
                .add(Items.LEATHER_BOOTS)
                .add(Items.STONE_SWORD)
                .add(Items.STONE_SHOVEL)
                .add(Items.STONE_PICKAXE)
                .add(Items.STONE_AXE)
                .add(Items.STONE_HOE)
                .add(Items.CHAINMAIL_HELMET)
                .add(Items.CHAINMAIL_CHESTPLATE)
                .add(Items.CHAINMAIL_LEGGINGS)
                .add(Items.CHAINMAIL_BOOTS)
                .add(Items.IRON_SWORD)
                .add(Items.IRON_SHOVEL)
                .add(Items.IRON_PICKAXE)
                .add(Items.IRON_AXE)
                .add(Items.IRON_HOE)
                .add(Items.IRON_HELMET)
                .add(Items.IRON_CHESTPLATE)
                .add(Items.IRON_LEGGINGS)
                .add(Items.IRON_BOOTS)
                .add(Items.GOLDEN_SWORD)
                .add(Items.GOLDEN_SHOVEL)
                .add(Items.GOLDEN_PICKAXE)
                .add(Items.GOLDEN_AXE)
                .add(Items.GOLDEN_HOE)
                .add(Items.GOLDEN_HELMET)
                .add(Items.GOLDEN_CHESTPLATE)
                .add(Items.GOLDEN_LEGGINGS)
                .add(Items.GOLDEN_BOOTS)
                .add(Items.DIAMOND_SWORD)
                .add(Items.DIAMOND_SHOVEL)
                .add(Items.DIAMOND_PICKAXE)
                .add(Items.DIAMOND_AXE)
                .add(Items.DIAMOND_HOE)
                .add(Items.DIAMOND_HELMET)
                .add(Items.DIAMOND_CHESTPLATE)
                .add(Items.DIAMOND_LEGGINGS)
                .add(Items.DIAMOND_BOOTS)
                .add(Items.BOW)
                .add(Items.CROSSBOW)
                .add(Items.SHIELD)
                .add(ACBlockRegistry.GALENA.get().asItem())
                .add(ACBlockRegistry.ENERGIZED_GALENA_AZURE.get().asItem())
                .add(ACBlockRegistry.ENERGIZED_GALENA_SCARLET.get().asItem())
                .add(ACBlockRegistry.ENERGIZED_GALENA_NEUTRAL.get().asItem())
                .add(MTItems.BYZANTIUM_ENERGIZED_GALENA.get())
                .add(ACBlockRegistry.GALENA_SPIRE.get().asItem())
                .add(ACBlockRegistry.METAL_SWARF.get().asItem())
                .add(ACBlockRegistry.LIMESTONE.get().asItem())
                .add(ACBlockRegistry.RADROCK.get().asItem())
                .add(ACBlockRegistry.ACIDIC_RADROCK.get().asItem())
                .add(ACBlockRegistry.ABYSSMARINE.get().asItem())
                .add(ACBlockRegistry.MUCK.get().asItem())
                .add(ACItemRegistry.GUANO.get().asItem())
                .add(ACBlockRegistry.GUANO_BLOCK.get().asItem())
                .add(ACItemRegistry.GUMBALL_PILE.get().asItem())
                .add(ACBlockRegistry.GUANOSTONE.get().asItem())
                .add(ACBlockRegistry.COPROLITH.get().asItem())
                .add(ACBlockRegistry.POROUS_COPROLITH.get().asItem())
                .add(ACBlockRegistry.PEERING_COPROLITH.get().asItem())
                .add(ACBlockRegistry.CAKE_LAYER.get().asItem())
                .add(ACBlockRegistry.BLOCK_OF_CHOCOLATE.get().asItem())
                .add(ACBlockRegistry.BLOCK_OF_FROSTED_CHOCOLATE.get().asItem())
                .add(ACBlockRegistry.WHITE_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.ORANGE_RADON_LAMP.get().asItem())
                .add(ACBlockRegistry.MAGENTA_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.LIGHT_BLUE_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.YELLOW_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.LIME_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.PINK_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.GRAY_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.LIGHT_BLUE_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.CYAN_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.PURPLE_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.BLUE_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.BROWN_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.GREEN_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.RED_ROCK_CANDY.get().asItem())
                .add(ACBlockRegistry.BLACK_ROCK_CANDY.get().asItem())
                .addOptional(new ResourceLocation("betterend:sulphuric_rock"))
                .addOptional(new ResourceLocation("betterend:brimstone"))
                .addOptional(new ResourceLocation("betterend:azure_jadestone"))
                .addOptional(new ResourceLocation("betterend:virid_jadestone"))
                .addOptional(new ResourceLocation("betterend:sandy_jadestone"))
                .addOptional(new ResourceLocation("betterend:violecite"))
                .addOptional(new ResourceLocation("betterend:flavolite"))
                .addOptional(new ResourceLocation("betterend:umbralith"))
                .addOptional(new ResourceLocation("betterend:endstone_dust"))
                .addOptional(new ResourceLocation("betternether:soul_sandstone"))
                .addOptional(new ResourceLocation("betternether:basalt_stalactite"))
                .addOptional(new ResourceLocation("betternether:netherrack_stalactite"))
                .addOptional(new ResourceLocation("betternether:blackstone_stalactite"))
                .addOptional(new ResourceLocation("betternether:bone_stalactite"))
                .addOptional(new ResourceLocation("betternether:glowstone_stalactite"))
                .addOptional(new ResourceLocation("biomesoplenty:glowing_moss_block"))
                .addOptional(new ResourceLocation("biomesoplenty:glowing_moss_carpet"))
                .addOptional(new ResourceLocation("biomesoplenty:flesh"))
                .addOptional(new ResourceLocation("biomesoplenty:porous_flesh"))
                .addOptional(new ResourceLocation("biomesoplenty:white_sand"))
                .addOptional(new ResourceLocation("biomesoplenty:orange_sand"))
                .addOptional(new ResourceLocation("biomesoplenty:black_sand"))
                .addOptional(new ResourceLocation("biomesoplenty:white_sandstone"))
                .addOptional(new ResourceLocation("biomesoplenty:orange_sandstone"))
                .addOptional(new ResourceLocation("biomesoplenty:black_sandstone"))
                .addOptional(new ResourceLocation("biomesoplenty:thermal_calcite"))
                .addOptional(new ResourceLocation("biomesoplenty:brimstone"))
                .addOptional(new ResourceLocation("biomesoplenty:rose_quartz_block"))
                .addOptional(new ResourceLocation("create:asurine"))
                .addOptional(new ResourceLocation("create:crimsite"))
                .addOptional(new ResourceLocation("create:limestone"))
                .addOptional(new ResourceLocation("create:ochrum"))
                .addOptional(new ResourceLocation("create:scoria"))
                .addOptional(new ResourceLocation("create:scorchia"))
                .addOptional(new ResourceLocation("create:veridium"))
                .addOptional(new ResourceLocation("enderio:broken_spawner"))
                .addOptional(new ResourceLocation("galosphere:allurite_block"))
                .addOptional(new ResourceLocation("galosphere:lumiere_block"))
                .addOptional(new ResourceLocation("galosphere:pastel_pink_salt"))
                .addOptional(new ResourceLocation("galosphere:rose_pink_salt"))
                .addOptional(new ResourceLocation("galosphere:pink_salt"))
                .addOptional(new ResourceLocation("galosphere:pink_salt_straw"))
                .addOptional(new ResourceLocation("scguns:phosphorite"))
                .addOptional(new ResourceLocation("yungscavebiomes:ancient_sand"))
                .addOptional(new ResourceLocation("yungscavebiomes:ancient_sandstone"))
                .addOptional(new ResourceLocation("yungscavebiomes:layered_ancient_sandstone"));

        tag(FTItemTags.MEALS)
                // Delightful
                .addOptional(new ResourceLocation("delightful", "matcha_ice_cream"))
                .addOptional(new ResourceLocation("delightful", "salmonberry_ice_cream"))
                .addOptional(new ResourceLocation("delightful", "source_berry_ice_cream"))
                .addOptional(new ResourceLocation("delightful", "salmonberry_pie_slice"))
                .addOptional(new ResourceLocation("delightful", "pumpkin_pie_slice"))
                .addOptional(new ResourceLocation("delightful", "source_berry_pie_slice"))
                .addOptional(new ResourceLocation("delightful", "source_berry_cookie"))
                .addOptional(new ResourceLocation("delightful", "glow_jam_cookie"))
                .addOptional(new ResourceLocation("delightful", "baklava_slice"))
                .addOptional(new ResourceLocation("delightful", "cactus_chili"))
                .addOptional(new ResourceLocation("delightful", "cactus_soup"))
                .addOptional(new ResourceLocation("delightful", "venison_stew"))
                .addOptional(new ResourceLocation("delightful", "sinigang"))
                .addOptional(new ResourceLocation("delightful", "field_salad"))
                .addOptional(new ResourceLocation("delightful", "nut_butter_and_jam_sandwich"))
                .addOptional(new ResourceLocation("delightful", "cheeseburger"))
                .addOptional(new ResourceLocation("delightful", "deluxe_cheeseburger"))
                .addOptional(new ResourceLocation("delightful", "smore"))
                .addOptional(new ResourceLocation("delightful", "cantaloupe_bread"))
                .addOptional(new ResourceLocation("delightful", "cantaloupe_popsicle"))
                .addOptional(new ResourceLocation("delightful", "stuffed_cantaloupe"))
                // Ender's Delight
                .addOptional(new ResourceLocation("endersdelight", "uncanny_cookies"))
                .addOptional(new ResourceLocation("endersdelight", "strange_eclair"))
                .addOptional(new ResourceLocation("endersdelight", "chorus_pie_slice"))
                .addOptional(new ResourceLocation("endersdelight", "crawling_sandwich"))
                .addOptional(new ResourceLocation("endersdelight", "crispy_skewer"))
                .addOptional(new ResourceLocation("endersdelight", "twisted_cereal"))
                .addOptional(new ResourceLocation("endersdelight", "endermite_stew"))
                .addOptional(new ResourceLocation("endersdelight", "pearl_pasta"))
                .addOptional(new ResourceLocation("endersdelight", "ender_paella"))
                .addOptional(new ResourceLocation("endersdelight", "chorus_stew"))
                .addOptional(new ResourceLocation("endersdelight", "amberveil_stew"))
                .addOptional(new ResourceLocation("endersdelight", "amberveiled_curry"))
                .addOptional(new ResourceLocation("endersdelight", "steak_fries"))
                .addOptional(new ResourceLocation("endersdelight", "veil_of_flames_risotto"))
                .addOptional(new ResourceLocation("endersdelight", "chicken_curry"))
                .addOptional(new ResourceLocation("endersdelight", "stuffed_shulker_bowl"))
                // Farmer's Delight
                .addOptional(new ResourceLocation("farmersdelight", "fried_egg"))
                .addOptional(new ResourceLocation("farmersdelight", "apple_pie"))
                .addOptional(new ResourceLocation("farmersdelight", "sweet_berry_cheesecake"))
                .addOptional(new ResourceLocation("farmersdelight", "chocolate_pie"))
                .addOptional(new ResourceLocation("farmersdelight", "cake_slice"))
                .addOptional(new ResourceLocation("farmersdelight", "apple_pie_slice"))
                .addOptional(new ResourceLocation("farmersdelight", "sweet_berry_cheesecake_slice"))
                .addOptional(new ResourceLocation("farmersdelight", "chocolate_pie_slice"))
                .addOptional(new ResourceLocation("farmersdelight", "sweet_berry_cookie"))
                .addOptional(new ResourceLocation("farmersdelight", "honey_cookie"))
                .addOptional(new ResourceLocation("farmersdelight", "melon_popsicle"))
                .addOptional(new ResourceLocation("farmersdelight", "glow_berry_custard"))
                .addOptional(new ResourceLocation("farmersdelight", "fruit_salad"))
                .addOptional(new ResourceLocation("farmersdelight", "mixed_salad"))
                .addOptional(new ResourceLocation("farmersdelight", "nether_salad"))
                .addOptional(new ResourceLocation("farmersdelight", "barbecue_stick"))
                .addOptional(new ResourceLocation("farmersdelight", "egg_sandwich"))
                .addOptional(new ResourceLocation("farmersdelight", "chicken_sandwich"))
                .addOptional(new ResourceLocation("farmersdelight", "hamburger"))
                .addOptional(new ResourceLocation("farmersdelight", "bacon_sandwich"))
                .addOptional(new ResourceLocation("farmersdelight", "mutton_wrap"))
                .addOptional(new ResourceLocation("farmersdelight", "dumplings"))
                .addOptional(new ResourceLocation("farmersdelight", "stuffed_potato"))
                .addOptional(new ResourceLocation("farmersdelight", "cabbage_rolls"))
                .addOptional(new ResourceLocation("farmersdelight", "salmon_roll"))
                .addOptional(new ResourceLocation("farmersdelight", "cod_roll"))
                .addOptional(new ResourceLocation("farmersdelight", "kelp_roll"))
                .addOptional(new ResourceLocation("farmersdelight", "kelp_roll_slice"))
                .addOptional(new ResourceLocation("farmersdelight", "cooked_rice"))
                .addOptional(new ResourceLocation("farmersdelight", "bone_broth"))
                .addOptional(new ResourceLocation("farmersdelight", "beef_stew"))
                .addOptional(new ResourceLocation("farmersdelight", "chicken_soup"))
                .addOptional(new ResourceLocation("farmersdelight", "vegetable_soup"))
                .addOptional(new ResourceLocation("farmersdelight", "fish_stew"))
                .addOptional(new ResourceLocation("farmersdelight", "fried_rice"))
                .addOptional(new ResourceLocation("farmersdelight", "pumpkin_soup"))
                .addOptional(new ResourceLocation("farmersdelight", "baked_cod_stew"))
                .addOptional(new ResourceLocation("farmersdelight", "noodle_soup"))
                .addOptional(new ResourceLocation("farmersdelight", "bacon_and_eggs"))
                .addOptional(new ResourceLocation("farmersdelight", "pasta_with_meatballs"))
                .addOptional(new ResourceLocation("farmersdelight", "pasta_with_mutton_chop"))
                .addOptional(new ResourceLocation("farmersdelight", "mushroom_rice"))
                .addOptional(new ResourceLocation("farmersdelight", "roasted_mutton_chops"))
                .addOptional(new ResourceLocation("farmersdelight", "vegetable_noodles"))
                .addOptional(new ResourceLocation("farmersdelight", "steak_and_potatoes"))
                .addOptional(new ResourceLocation("farmersdelight", "ratatouille"))
                .addOptional(new ResourceLocation("farmersdelight", "squid_ink_pasta"))
                .addOptional(new ResourceLocation("farmersdelight", "grilled_salmon"))
                .addOptional(new ResourceLocation("farmersdelight", "roast_chicken"))
                .addOptional(new ResourceLocation("farmersdelight", "stuffed_pumpkin"))
                .addOptional(new ResourceLocation("farmersdelight", "honey_glazed_ham"))
                .addOptional(new ResourceLocation("farmersdelight", "shepherds_pie"))
                // Nether's Delight
                .addOptional(new ResourceLocation("nethersdelight", "strider_moss_stew"))
                .addOptional(new ResourceLocation("nethersdelight", "plate_of_stuffed_hoglin_snout"))
                .addOptional(new ResourceLocation("nethersdelight", "plate_of_stuffed_hoglin_ham"))
                .addOptional(new ResourceLocation("nethersdelight", "plate_of_stuffed_hoglin_roast"))
                .addOptional(new ResourceLocation("nethersdelight", "nether_skewer"))
                // Ocean's Delight
                .addOptional(new ResourceLocation("oceansdelight", "baked_tentacle_on_a_stick"))
                .addOptional(new ResourceLocation("oceansdelight", "bowl_of_guardian_soup"))
                .addOptional(new ResourceLocation("oceansdelight", "braised_sea_pickle"))
                .addOptional(new ResourceLocation("oceansdelight", "cabbage_wrapped_elder_guardian"))
                .addOptional(new ResourceLocation("oceansdelight", "cooked_stuffed_cod"))
                .addOptional(new ResourceLocation("oceansdelight", "elder_guardian_roll"))
                .addOptional(new ResourceLocation("oceansdelight", "fugu_roll"))
                .addOptional(new ResourceLocation("oceansdelight", "guardian_soup"))
                .addOptional(new ResourceLocation("oceansdelight", "honey_fried_kelp"))
                .addOptional(new ResourceLocation("oceansdelight", "seagrass_salad"))
                .addOptional(new ResourceLocation("oceansdelight", "squid_rings"));
    }
}
