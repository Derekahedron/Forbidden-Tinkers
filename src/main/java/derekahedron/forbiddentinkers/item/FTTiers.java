package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.block.FTBlockTags;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.List;

public enum FTTiers implements Tier {

    CHAMPIUM(
            4,
            1500,
            8.0F,
            3.5F,
            100,
            FTItems.CHAMPIUM_INGOT.get(),
            FTBlockTags.NEEDS_CHAMPIUM_TOOL);

    private final int level;
    private final int durability;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final Ingredient repairIngredient;
    private final TagKey<Block> tag;

    FTTiers(int level, int durability, float speed, float damage, int enchantmentValue, ItemLike repairIngredient, TagKey<Block> tag) {
        this.level = level;
        this.durability = durability;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = Ingredient.of(repairIngredient);
        this.tag = tag;
    }

    @Override
    public int getUses() {
        return this.durability;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }

    @Override
    public TagKey<Block> getTag() {
        return tag;
    }

    public static void registerTiers() {
        TierSortingRegistry.registerTier(
                CHAMPIUM,
                FTUtil.location("champium"),
                List.of(Tiers.NETHERITE),
                List.of());
    }

    public static void initialize(FMLCommonSetupEvent event) {
        event.enqueueWork(FTTiers::registerTiers);
    }
}
