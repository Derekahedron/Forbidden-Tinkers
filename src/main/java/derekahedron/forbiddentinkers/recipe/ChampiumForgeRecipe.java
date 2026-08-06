package derekahedron.forbiddentinkers.recipe;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.JsonOps;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class ChampiumForgeRecipe implements Recipe<ChampiumForgeBlockEntity> {

    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String COUNT_KEY = "count";
    public static final String MAX_USES_KEY = "max_uses";
    public static final String EXPERIENCE_KEY = "experience";
    public static final String RESULT_KEY = "result";
    public static final String WEIGHT_KEY = "weight";
    public static final String RELOAD_KEY = "reload";
    public final ResourceLocation id;
    public final int weight;
    public final NonNullList<ChampiumForgeIngredient> ingredients;
    public final int count;
    @Nullable
    public final IntProvider maxUses;
    public final float experience;
    public final ItemStack result;
    public final boolean reloadAfterCraft;

    public ChampiumForgeRecipe(
            ResourceLocation id,
            NonNullList<ChampiumForgeIngredient> ingredients,
            int count,
            @Nullable IntProvider maxUses,
            float experience,
            ItemStack result,
            int weight,
            boolean reloadAfterCraft) {
        this.id = id;
        this.ingredients = ingredients;
        this.count = Math.max(count, 0);
        this.maxUses = maxUses;
        this.experience = experience;
        this.result = result;
        this.weight = Math.max(weight, 1);
        this.reloadAfterCraft = reloadAfterCraft;
    }

    public boolean isValid() {
        return !ingredients.isEmpty() && ingredients.stream()
                .allMatch(ChampiumForgeIngredient::isValid);
    }

    @Override
    public boolean matches(ChampiumForgeBlockEntity champiumForge, Level level) {
        if (champiumForge.ingredients == null) return false;
        for (int i = 0; i < champiumForge.ingredients.size(); i++) {
            if (!champiumForge.ingredients.get(i).test(champiumForge.inputs.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack assemble(ChampiumForgeBlockEntity champiumForge, RegistryAccess registryAccess) {
        return result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int x, int y) {
        return true;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, ingredients.stream()
                .map(ChampiumForgeIngredient::getValidIngredients)
                .flatMap(List::stream)
                .toList().toArray(new Ingredient[0]));
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return result;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<ChampiumForgeRecipe> getSerializer() {
        return FTRecipeSerializers.CHAMPIUM_FORGE.get();
    }

    @Override
    public RecipeType<ChampiumForgeRecipe> getType() {
        return FTRecipeTypes.CHAMPIUM_FORGE.get();
    }

    @Override
    public boolean isSpecial() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ChampiumForgeRecipe> {

        @Override
        public ChampiumForgeRecipe fromJson(ResourceLocation id, JsonObject json) {

            JsonArray ingredientsJson = GsonHelper.getAsJsonArray(json, INGREDIENTS_KEY);
            NonNullList<ChampiumForgeIngredient> ingredients = NonNullList.withSize(ingredientsJson.size(), ChampiumForgeIngredient.EMPTY);
            for (int i = 0; i < ingredientsJson.size(); i++) {
                ChampiumForgeIngredient ingredient = ChampiumForgeIngredient.fromJson(ingredientsJson.get(i));
                ingredients.set(i, ingredient);
            }

            int count = 1;
            if (json.has(COUNT_KEY)) {
                count = GsonHelper.getAsInt(json, COUNT_KEY);
            }

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for champium forge recipe");
            } else if (ingredients.size() > 9) {
                throw new JsonParseException("Too many ingredients for champium forge recipe. The maximum is " + 9);
            }

            IntProvider maxUses = null;
            if (json.has(MAX_USES_KEY)) {
                maxUses = IntProvider.NON_NEGATIVE_CODEC.decode(JsonOps.INSTANCE, json.get(MAX_USES_KEY)).result()
                        .map(Pair::getFirst).orElse(null);
            }

            float experience = GsonHelper.getAsFloat(json, EXPERIENCE_KEY, 0.0F);

            if (!json.has(RESULT_KEY)) {
                throw new JsonSyntaxException("Missing result, expected to find a string or object");
            }

            ItemStack result;
            if (json.get(RESULT_KEY).isJsonObject()) {
                result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, RESULT_KEY));
            } else {
                ResourceLocation itemId = ResourceLocation.tryParse(GsonHelper.getAsString(json, RESULT_KEY));
                Item resultItem = ForgeRegistries.ITEMS.getValue(itemId);
                if (resultItem == null) {
                    throw new IllegalStateException("Item: " + itemId + " does not exist");
                }
                result = new ItemStack(resultItem);
            }

            int weight = GsonHelper.getAsInt(json, WEIGHT_KEY, 1);

            boolean reloadAfterCraft = GsonHelper.getAsBoolean(json, RELOAD_KEY, false);

            return new ChampiumForgeRecipe(id, ingredients, count, maxUses, experience, result, weight, reloadAfterCraft);
        }

        @Override
        @Nullable
        public ChampiumForgeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            NonNullList<ChampiumForgeIngredient> ingredients = buffer.readCollection(
                    NonNullList::createWithCapacity,
                    ChampiumForgeIngredient::fromNetwork);

            int count = buffer.readInt();

            IntProvider maxUses = buffer.readOptional((buf) -> buf.readJsonWithCodec(IntProvider.POSITIVE_CODEC))
                    .orElse(null);

            float experience = buffer.readFloat();

            ItemStack result = buffer.readItem();

            int weight = buffer.readInt();

            boolean reloadAfterCraft = buffer.readBoolean();

            return new ChampiumForgeRecipe(id, ingredients, count, maxUses, experience, result, weight, reloadAfterCraft);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChampiumForgeRecipe recipe) {
            buffer.writeCollection(recipe.ingredients, ChampiumForgeIngredient::toNetwork);

            buffer.writeInt(recipe.count);

            buffer.writeOptional(Optional.ofNullable(recipe.maxUses),
                    (buf, val) -> buf.writeJsonWithCodec(IntProvider.CODEC, val));

            buffer.writeFloat(recipe.experience);

            buffer.writeItem(recipe.result);

            buffer.writeInt(recipe.weight);

            buffer.writeBoolean(recipe.reloadAfterCraft);
        }
    }

    @SuppressWarnings("unused")
    public static class Builder implements RecipeBuilder {
        public final Item result;
        public final List<ChampiumForgeIngredient> ingredients;
        @Nullable
        public Integer count;
        @Nullable
        public IntProvider maxUses = null;
        @Nullable
        public Float experience;
        @Nullable
        public Integer weight;
        public boolean reloadAfterCraft = false;

        public Builder(ItemLike result) {
            this.result = result.asItem();
            this.ingredients = new ArrayList<>();
        }

        public Builder addIngredient(ChampiumForgeIngredient ingredient) {
            ingredients.add(ingredient);
            return this;
        }

        public Builder addIngredient(Ingredient ingredient) {
            return addIngredient(new ChampiumForgeIngredient.Builder().addIngredient(ingredient).build());
        }

        public Builder addIngredient(ItemLike... ingredients) {
            return addIngredient(Ingredient.of(ingredients));
        }

        public Builder addIngredient(TagKey<Item> tag) {
            return addIngredient(Ingredient.of(tag));
        }

        public Builder count(int count) {
            this.count = count;
            return this;
        }

        public Builder maxUses(int maxUses) {
            this.maxUses = ConstantInt.of(maxUses);
            return this;
        }

        public Builder maxUses(@Nullable IntProvider maxUses) {
            this.maxUses = maxUses;
            return this;
        }

        public Builder experience(float experience) {
            this.experience = experience;
            return this;
        }

        public Builder weight(@Nullable Integer weight) {
            this.weight = weight;
            return this;
        }

        public Builder reloadAfterCraft() {
            return reloadAfterCraft(true);
        }

        public Builder reloadAfterCraft(boolean reloadAfterCraft) {
            this.reloadAfterCraft = reloadAfterCraft;
            return this;
        }

        public Builder unlockedBy(String p_126133_, CriterionTriggerInstance p_126134_) {
            return this;
        }

        public Builder group(@Nullable String group) {
            return this;
        }

        public Item getResult() {
            return result;
        }

        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            consumer.accept(new Builder.Result(
                    id,
                    result,
                    ingredients,
                    count,
                    maxUses,
                    experience,
                    weight,
                    reloadAfterCraft));
        }

        public static class Result implements FinishedRecipe {
            private final ResourceLocation id;
            private final Item result;
            public final List<ChampiumForgeIngredient> ingredients;
            @Nullable
            public final Integer count;
            @Nullable
            public final IntProvider maxUses;
            @Nullable
            public final Float experience;
            @Nullable
            public final Integer weight;
            public final boolean reloadAfterCraft;

            public Result(
                    ResourceLocation id,
                    Item result,
                    List<ChampiumForgeIngredient> ingredients,
                    @Nullable Integer count,
                    @Nullable IntProvider maxUses,
                    @Nullable Float experience,
                    @Nullable Integer weight,
                    boolean reloadAfterCraft) {
                this.id = id;
                this.result = result;
                this.ingredients = ingredients;
                this.count = count;
                this.maxUses = maxUses;
                this.experience = experience;
                this.weight = weight;
                this.reloadAfterCraft = reloadAfterCraft;
            }

            public void serializeRecipeData(JsonObject json) {
                JsonArray ingredientsJson = new JsonArray();
                for (ChampiumForgeIngredient ingredient : ingredients) {
                    ingredientsJson.add(ingredient.toJson());
                }
                json.add(INGREDIENTS_KEY, ingredientsJson);

                if (count != null) {
                    json.addProperty(COUNT_KEY, count);
                }

                if (maxUses != null) {
                    JsonElement maxUsesJson = IntProvider.NON_NEGATIVE_CODEC.encodeStart(JsonOps.INSTANCE, maxUses)
                            .result().orElse(null);
                    json.add(MAX_USES_KEY, maxUsesJson);
                }

                if (experience != null) {
                    json.addProperty(EXPERIENCE_KEY, experience);
                }

                JsonObject resultJson = new JsonObject();
                resultJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
                json.add(RESULT_KEY, resultJson);

                if (weight != null) {
                    json.addProperty(WEIGHT_KEY, weight);
                }

                if (reloadAfterCraft) {
                    json.addProperty(RELOAD_KEY, true);
                }
            }

            public RecipeSerializer<?> getType() {
                return FTRecipeSerializers.CHAMPIUM_FORGE.get();
            }

            public ResourceLocation getId() {
                return this.id;
            }

            @Nullable
            public JsonObject serializeAdvancement() {
                return null;
            }

            @Nullable
            public ResourceLocation getAdvancementId() {
                return null;
            }
        }
    }
}
