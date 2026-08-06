package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class FTItemTags {

    public static final TagKey<Item> HAS_ACTION =
            ItemTags.create(FTUtil.location("has_action"));

    public static final TagKey<Item> BEDROCK_BRICKS =
            ItemTags.create(FTUtil.location("bedrock_bricks"));

    public static final TagKey<Item> AUGMENTATION_PART_BLACKLIST =
            ItemTags.create(FTUtil.location("augmentation_part_blacklist"));

    public static final TagKey<Item> DISPOSABLE =
            ItemTags.create(FTUtil.location("disposable"));

    public static final TagKey<Item> MEALS =
            ItemTags.create(FTUtil.location("meals"));
}
