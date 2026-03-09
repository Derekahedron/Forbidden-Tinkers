package derekahedron.forbiddentinkers.datagen.tags;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTItemTags;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.util.CompatUtil;
import derekahedron.forbiddentinkers.util.ForgeTags;
import derekahedron.invexp.item.InvExpItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
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
                .addOptional(CompatUtil.tinkersThings("large_repair_kit"));

        tag(FTItemTags.DISPOSABLE)
                .add(Items.STONE)
                .add(Items.COBBLESTONE)
                .add(Items.DEEPSLATE)
                .add(Items.COBBLED_DEEPSLATE)
                .add(Items.TUFF)
                .add(Items.GRANITE)
                .add(Items.DIORITE)
                .add(Items.ANDESITE)
                .add(Items.DIRT)
                .add(Items.GRAVEL)
                .add(Items.SAND)
                .add(Items.RED_SAND)
                .add(Items.NETHERRACK)
                .add(Items.NETHER_BRICK)
                .add(Items.BLACKSTONE)
                .add(Items.BASALT)
                .add(Items.MAGMA_BLOCK)
                .add(Items.SOUL_SAND)
                .add(Items.SOUL_SOIL)
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
                .addOptional(CompatUtil.create("asurine"))
                .addOptional(CompatUtil.create("crimsite"))
                .addOptional(CompatUtil.create("limestone"))
                .addOptional(CompatUtil.create("ochrum"))
                .addOptional(CompatUtil.create("scoria"))
                .addOptional(CompatUtil.create("scorchia"))
                .addOptional(CompatUtil.create("veridium"))
                .addOptional(CompatUtil.enderIO("broken_spawner"));
    }
}
