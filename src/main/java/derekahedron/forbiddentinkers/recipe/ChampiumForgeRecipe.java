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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class ChampiumForgeRecipe implements Recipe<ChampiumForgeBlockEntity> {
    public static final String INGREDIENTS_KEY = "ingredients";
    public static final String COUNT_KEY = "count";
    public static final String MAX_USES_KEY = "max_uses";
    public static final String RESULT_KEY = "result";
    public static final String WEIGHT_KEY = "weight";
    public final ResourceLocation id;
    public final int weight;
    public final NonNullList<Ingredient> ingredients;
    public final int count;
    @Nullable
    public final IntProvider maxUses;
    public final ItemStack result;

    public ChampiumForgeRecipe(ResourceLocation id, NonNullList<Ingredient> inputs, int count, @Nullable IntProvider maxUses, ItemStack result, int weight) {
        this.id = id;
        this.ingredients = inputs;
        this.count = Math.max(count, 0);
        this.maxUses = maxUses;
        this.result = result;
        this.weight = Math.max(weight, 1);
    }

    @Override
    public boolean matches(ChampiumForgeBlockEntity champiumForge, Level level) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (!ingredients.get(i).test(champiumForge.inputs.get(i))) {
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
        return ingredients;
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
            NonNullList<Ingredient> ingredients = NonNullList.withSize(ingredientsJson.size(), Ingredient.EMPTY);

            for (int i = 0; i < ingredientsJson.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredientsJson.get(i), false);
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
            int weight = 1;

            if (json.has(WEIGHT_KEY)) {
                weight = GsonHelper.getAsInt(json, WEIGHT_KEY);
            }

            return new ChampiumForgeRecipe(id, ingredients, count, maxUses, result, weight);
        }

        @Override
        @Nullable
        public ChampiumForgeRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            int numIngredients = buffer.readVarInt();
            NonNullList<Ingredient> ingredients = NonNullList.withSize(numIngredients, Ingredient.EMPTY);
            for (int i = 0; i < numIngredients; i++) {
                ingredients.set(i, Ingredient.fromNetwork(buffer));
            }

            int count = buffer.readInt();

            IntProvider maxUses = buffer.readOptional((buf) -> buf.readJsonWithCodec(IntProvider.POSITIVE_CODEC))
                    .orElse(null);

            ItemStack result = buffer.readItem();

            int weight = buffer.readInt();

            return new ChampiumForgeRecipe(id, ingredients, count, maxUses, result, weight);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, ChampiumForgeRecipe recipe) {
            buffer.writeVarInt(recipe.ingredients.size());
            for (Ingredient input : recipe.ingredients) {
                input.toNetwork(buffer);
            }

            buffer.writeInt(recipe.count);

            buffer.writeOptional(Optional.ofNullable(recipe.maxUses),
                    (buf, val) -> buf.writeJsonWithCodec(IntProvider.CODEC, val));

            buffer.writeItem(recipe.result);

            buffer.writeInt(recipe.weight);
        }
    }

    public static class Builder implements RecipeBuilder {
        public final Item result;
        public final List<Ingredient> ingredients;
        @Nullable
        public Integer count;
        @Nullable
        public IntProvider maxUses = null;
        @Nullable
        public Integer weight;

        public Builder(ItemLike result) {
            this.result = result.asItem();
            this.ingredients = NonNullList.create();
        }

        public Builder addIngredient(Ingredient ingredient) {
            ingredients.add(ingredient);
            return this;
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

        public Builder weight(@Nullable Integer weight) {
            this.weight = weight;
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
                    weight));
        }

        public static class Result implements FinishedRecipe {
            private final ResourceLocation id;
            private final Item result;
            public final List<Ingredient> ingredients;
            @Nullable
            public final Integer count;
            @Nullable
            public final IntProvider maxUses;
            @Nullable
            public final Integer weight;

            public Result(ResourceLocation id, Item result, List<Ingredient> ingredients, @Nullable Integer count, @Nullable IntProvider maxUses, @Nullable Integer weight) {
                this.id = id;
                this.result = result;
                this.ingredients = ingredients;
                this.count = count;
                this.maxUses = maxUses;
                this.weight = weight;
            }

            public void serializeRecipeData(JsonObject json) {
                JsonArray ingredientsJson = new JsonArray();
                for (Ingredient ingredient : ingredients) {
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

                JsonObject resultJson = new JsonObject();
                resultJson.addProperty("item", Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(result)).toString());
                json.add(RESULT_KEY, resultJson);

                if (weight != null) {
                    json.addProperty(WEIGHT_KEY, weight);
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
