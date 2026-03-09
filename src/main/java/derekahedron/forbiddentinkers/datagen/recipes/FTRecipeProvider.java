package derekahedron.forbiddentinkers.datagen.recipes;

import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.util.FTUtil;
import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class FTRecipeProvider extends RecipeProvider {

    public FTRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Augmentation Gem
        nineBlockStorageRecipes(consumer,
                RecipeCategory.MISC,
                FTItems.TINKERS_DIAMOND.get(),
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.TINKERS_DIAMOND_BLOCK.get());
        oreSmelting(consumer,
                List.of(FTItems.TINKERS_DIAMOND_ORE.get()),
                RecipeCategory.MISC,
                FTItems.TINKERS_DIAMOND.get(),
                4.0F, 200,
                "tinkers_diamond");
        oreBlasting(consumer,
                List.of(FTItems.TINKERS_DIAMOND_ORE.get()),
                RecipeCategory.MISC,
                FTItems.TINKERS_DIAMOND.get(),
                4.0F, 100,
                "tinkers_diamond");
        oreSmelting(consumer,
                List.of(FTItems.DEEPSLATE_TINKERS_DIAMOND_ORE.get()),
                RecipeCategory.MISC,
                FTItems.TINKERS_DIAMOND.get(),
                4.0F, 200,
                "tinkers_diamond");
        oreBlasting(consumer,
                List.of(FTItems.DEEPSLATE_TINKERS_DIAMOND_ORE.get()),
                RecipeCategory.MISC,
                FTItems.TINKERS_DIAMOND.get(),
                4.0F, 100,
                "tinkers_diamond");

        // Bedrock Bricks
        polished(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICKS.get(),
                Items.BEDROCK);
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICKS.get(),
                Items.BEDROCK);
        stair(consumer,
                FTItems.BEDROCK_BRICK_STAIRS.get(),
                FTItems.BEDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_STAIRS.get(),
                Items.BEDROCK);
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_STAIRS.get(),
                FTItems.BEDROCK_BRICKS.get());
        slab(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_SLAB.get(),
                FTItems.BEDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_SLAB.get(),
                Items.BEDROCK,
                2);
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_SLAB.get(),
                FTItems.BEDROCK_BRICKS.get(),
                2);
        wall(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_WALL.get(),
                FTItems.BEDROCK_BRICKS.get());
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_WALL.get(),
                Items.BEDROCK);
        stonecutterResultFromBase(consumer,
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.BEDROCK_BRICK_WALL.get(),
                FTItems.BEDROCK_BRICKS.get());

        // Champium Ore
        oreSmelting(consumer,
                List.of(FTItems.CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 200,
                "champium_ingot");
        oreBlasting(consumer,
                List.of(FTItems.CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 100,
                "champium_ingot");
        oreSmelting(consumer,
                List.of(FTItems.DEEPSLATE_CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 200,
                "champium_ingot");
        oreBlasting(consumer,
                List.of(FTItems.DEEPSLATE_CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 100,
                "champium_ingot");
        oreSmelting(consumer,
                List.of(FTItems.BEDROCK_CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 200,
                "champium_ingot");
        oreBlasting(consumer,
                List.of(FTItems.BEDROCK_CHAMPIUM_ORE.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 100,
                "champium_ingot");
        oreSmelting(consumer,
                List.of(FTItems.RAW_CHAMPIUM.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 200,
                "champium_ingot");
        oreBlasting(consumer,
                List.of(FTItems.RAW_CHAMPIUM.get()),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                4.0F, 100,
                "champium_ingot");

        // Champium
        nineBlockStorageRecipes(consumer,
                RecipeCategory.MISC,
                FTItems.RAW_CHAMPIUM.get(),
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.RAW_CHAMPIUM_BLOCK.get());
        nineBlockStorageRecipesRecipesWithCustomUnpacking(consumer,
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                RecipeCategory.BUILDING_BLOCKS,
                FTItems.CHAMPIUM_BLOCK.get(),
                "champium_ingot_from_champium_block",
                "champium_ingot");
        nineBlockStorageRecipesWithCustomPacking(consumer,
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_NUGGET.get(),
                RecipeCategory.MISC,
                FTItems.CHAMPIUM_INGOT.get(),
                "champium_ingot_from_nuggets",
                "champium_ingot");

        // Champium Forge Recipes
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(Items.HAY_BLOCK)
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(consumer, FTUtil.location("champium_forge_wheat_block"));

        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "carrot"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "carrot")), FTUtil.location("champium_forge_carrot_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .addIngredient(Items.CARROT)
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(notHasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "carrot")), FTUtil.location("champium_forge_carrot"));

        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "potato"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "potato")), FTUtil.location("champium_forge_potato_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .addIngredient(Items.POTATO)
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(notHasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "potato")), FTUtil.location("champium_forge_potato"));

        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "beetroot"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "beetroot")), FTUtil.location("champium_forge_beetroot_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .addIngredient(Items.BEETROOT)
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(notHasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "beetroot")), FTUtil.location("champium_forge_beetroot"));

        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "cabbage"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "cabbage")), FTUtil.location("champium_forge_cabbage_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "tomato"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "tomato")), FTUtil.location("champium_forge_tomato_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "onion"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "onion")), FTUtil.location("champium_forge_onion_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .weight(4)
                .addIngredient(MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "rice"))
                .count(8 * 64)
                .maxUses(UniformInt.of(8, 12))
                .save(hasTag(consumer, MTUtil.childTag(Tags.Items.STORAGE_BLOCKS, "rice")), FTUtil.location("champium_forge_rice_block"));

        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.MELON)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_melon"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.PUMPKIN)
                .addIngredient(Items.PUMPKIN)
                .addIngredient(Items.PUMPKIN)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_pumpkin"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.BAMBOO_BLOCK)
                .addIngredient(Items.BAMBOO_BLOCK)
                .addIngredient(Items.BAMBOO_BLOCK)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_bamboo_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.DRIED_KELP_BLOCK)
                .addIngredient(Items.DRIED_KELP_BLOCK)
                .addIngredient(Items.DRIED_KELP_BLOCK)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_dried_kelp_block"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .addIngredient(Items.NETHER_WART)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_nether_wart"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .addIngredient(Items.GLOW_BERRIES)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_glow_berries"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .addIngredient(Items.SWEET_BERRIES)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_sweet_berries"));
        new ChampiumForgeRecipe.Builder(FTItems.CHAMPIUM_NUGGET.get())
                .addIngredient(Items.SUGAR_CANE)
                .addIngredient(Items.SUGAR_CANE)
                .addIngredient(Items.SUGAR_CANE)
                .count(8 * 64)
                .maxUses(UniformInt.of(32, 64))
                .save(consumer, FTUtil.location("champium_forge_sugar_cane"));
    }

    protected static void nineBlockStorageRecipes(
            Consumer<FinishedRecipe> consumer,
            RecipeCategory smallItemCategory,
            ItemLike smallItem,
            RecipeCategory largeItemCategory,
            ItemLike largeItem) {
        nineBlockStorageRecipes(
                consumer,
                smallItemCategory,
                smallItem,
                largeItemCategory,
                largeItem,
                withNamespace(largeItem, getSimpleRecipeName(largeItem)),
                null,
                withNamespace(smallItem, getSimpleRecipeName(smallItem)),
                null);
    }

    protected static void nineBlockStorageRecipesWithCustomPacking(
            Consumer<FinishedRecipe> consumer,
            RecipeCategory smallItemCategory,
            ItemLike smallItem,
            RecipeCategory largeItemCategory,
            ItemLike largeItem,
            String largeItemRecipeName,
            @Nullable String largeItemGroupName) {
        nineBlockStorageRecipes(
                consumer,
                smallItemCategory,
                smallItem,
                largeItemCategory,
                largeItem,
                withNamespace(largeItem, largeItemRecipeName),
                largeItemGroupName,
                withNamespace(smallItem, getSimpleRecipeName(smallItem)),
                null);
    }

    protected static void nineBlockStorageRecipesRecipesWithCustomUnpacking(
            Consumer<FinishedRecipe> consumer,
            RecipeCategory smallItemCategory,
            ItemLike smallItem,
            RecipeCategory largeItemCategory,
            ItemLike largeItem,
            String smallItemRecipeName,
            @Nullable String smallItemGroupName) {
        nineBlockStorageRecipes(
                consumer,
                smallItemCategory,
                smallItem,
                largeItemCategory,
                largeItem,
                withNamespace(largeItem, getSimpleRecipeName(largeItem)),
                null,
                withNamespace(smallItem, smallItemRecipeName),
                smallItemGroupName);
    }

    protected static void stonecutterResultFromBase(
            Consumer<FinishedRecipe> consumer,
            RecipeCategory recipeCategory,
            ItemLike output,
            ItemLike input) {
        stonecutterResultFromBase(consumer,
                recipeCategory,
                output,
                input,
                1);
    }

    protected static void stonecutterResultFromBase(
            Consumer<FinishedRecipe> consumer,
            RecipeCategory recipeCategory,
            ItemLike output,
            ItemLike input,
            int amount) {
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), recipeCategory, output, amount)
                .unlockedBy(getHasName(input), has(input))
                .save(consumer, withNamespace(input, getConversionRecipeName(output, input) + "_stonecutting"));
    }

    protected static void oreSmelting(
            Consumer<FinishedRecipe> consumer,
            List<ItemLike> inputs,
            RecipeCategory recipeCategory,
            ItemLike output,
            float expReward,
            int cookTicks,
            String groupName) {
        oreCooking(
                consumer,
                RecipeSerializer.SMELTING_RECIPE,
                inputs,
                recipeCategory,
                output,
                expReward,
                cookTicks,
                groupName,
                "_from_smelting");
    }

    protected static void oreBlasting(
            Consumer<FinishedRecipe> consumer,
            List<ItemLike> inputs,
            RecipeCategory recipeCategory,
            ItemLike output,
            float expReward,
            int cookTicks,
            String groupName) {
        oreCooking(
                consumer,
                RecipeSerializer.BLASTING_RECIPE,
                inputs,
                recipeCategory,
                output,
                expReward,
                cookTicks,
                groupName,
                "_from_blasting");
    }

    protected static void oreCooking(
            Consumer<FinishedRecipe> consumer,
            RecipeSerializer<? extends AbstractCookingRecipe> recipeSerializer,
            List<ItemLike> inputs,
            RecipeCategory recipeCategory,
            ItemLike output,
            float expReward,
            int cookTicks,
            String groupName,
            String suffix) {
        for (ItemLike input : inputs) {
            SimpleCookingRecipeBuilder.generic(
                    Ingredient.of(input),
                    recipeCategory,
                    output,
                    expReward,
                    cookTicks,
                    recipeSerializer)
                    .group(groupName)
                    .unlockedBy(getHasName(input), has(input))
                    .save(consumer, withNamespace(output, getItemName(output) + suffix + "_" + getItemName(input)));
        }
    }

    protected static void stair(
            Consumer<FinishedRecipe> consumer, ItemLike stairs, ItemLike base) {
        stairBuilder(stairs, Ingredient.of(base)).unlockedBy(getHasName(base), has(base)).save(consumer);
    }

    @SuppressWarnings("deprecation")
    protected static String withNamespace(ItemLike item, String path) {
        String namespace = BuiltInRegistries.ITEM.getKey(item.asItem()).getNamespace();
        return namespace + ":" + path;
    }

    public static Consumer<FinishedRecipe> hasTag(Consumer<FinishedRecipe> consumer, TagKey<?> tag) {
        return ConsumerWrapperBuilder.wrap()
                .addCondition(new NotCondition(new TagEmptyCondition(tag.location())))
                .build(consumer);
    }

    public static Consumer<FinishedRecipe> notHasTag(Consumer<FinishedRecipe> consumer, TagKey<?> tag) {
        return ConsumerWrapperBuilder.wrap()
                .addCondition(new TagEmptyCondition(tag.location()))
                .build(consumer);
    }
}
