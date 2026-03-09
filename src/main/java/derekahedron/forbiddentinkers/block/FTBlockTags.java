package derekahedron.forbiddentinkers.block;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class FTBlockTags {

    public static final TagKey<Block> NEEDS_CHAMPIUM_TOOL =
            BlockTags.create(FTUtil.location("needs_champium_tool"));

    public static final TagKey<Block> BEDROCK_BRICKS =
            BlockTags.create(FTUtil.location("bedrock_bricks"));
}
