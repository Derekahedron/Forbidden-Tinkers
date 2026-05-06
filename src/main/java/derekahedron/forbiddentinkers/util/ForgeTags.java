package derekahedron.forbiddentinkers.util;

import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;

public class ForgeTags {
    public static class Items {
        public static final TagKey<Item> TINKERS_DIAMOND_BLOCKS =
                MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "tinkers_diamond");
        public static final TagKey<Item> TINKERS_DIAMOND_ORES =
                MTUtil.childTag(Tags.Items.ORES, "tinkers_diamond");
        public static final TagKey<Item> TINKERS_DIAMOND =
                MTUtil.childTag(Tags.Items.GEMS, "tinkers_diamond");
        public static final TagKey<Item> CHAMPIUM_BLOCKS =
                MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "champium");
        public static final TagKey<Item> CHAMPIUM_ORES =
                MTUtil.childTag(Tags.Items.ORES, "champium");
        public static final TagKey<Item> RAW_CHAMPIUM_BLOCKS =
                MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "raw_champium");
        public static final TagKey<Item> RAW_CHAMPIUM =
                MTUtil.childTag(Tags.Items.RAW_MATERIALS, "champium");
        public static final TagKey<Item> CHAMPIUM_NUGGETS =
                MTUtil.childTag(Tags.Items.NUGGETS, "champium");
        public static final TagKey<Item> CHAMPIUM_INGOTS =
                MTUtil.childTag(Tags.Items.INGOTS, "champium");
    }

    public static class Blocks {
        public static final TagKey<Block> TINKERS_DIAMOND_BLOCKS =
                MTUtil.childTag(Tags.Blocks.STORAGE_BLOCKS, "tinkers_diamond");
        public static final TagKey<Block> TINKERS_DIAMOND_ORES =
                MTUtil.childTag(Tags.Blocks.ORES, "tinkers_diamond");
        public static final TagKey<Block> CHAMPIUM_BLOCKS =
                MTUtil.childTag(Tags.Blocks.STORAGE_BLOCKS, "champium");
        public static final TagKey<Block> CHAMPIUM_ORES =
                MTUtil.childTag(Tags.Blocks.ORES, "champium");
        public static final TagKey<Block> RAW_CHAMPIUM_BLOCKS =
                MTUtil.childTag(Tags.Blocks.STORAGE_BLOCKS, "raw_champium");
    }

    public static class Fluids {
        public static final TagKey<Fluid> MOLTEN_CHAMPIUM =
                FluidTags.create(new ResourceLocation("forge:molten_champium"));
    }
}
