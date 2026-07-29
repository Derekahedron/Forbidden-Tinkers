package derekahedron.forbiddentinkers.recipe;

import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record ChampiumForgeIngredient(NonNullList<IngredientOption> ingredientOptions) {

    public static final ChampiumForgeIngredient EMPTY = new ChampiumForgeIngredient(NonNullList.create());

    public JsonElement toJson() {
        if (ingredientOptions.size() == 1) return ingredientOptions.get(0).toJson();
        JsonArray ingredientsJson = new JsonArray();
        for (IngredientOption ingredientOption : ingredientOptions) {
            ingredientsJson.add(ingredientOption.toJson());
        }
        return ingredientsJson;
    }

    public static ChampiumForgeIngredient fromJson(JsonElement json) {
        try {
            IngredientOption ingredientOption = IngredientOption.fromJson(json);
            return new ChampiumForgeIngredient(NonNullList.of(IngredientOption.EMPTY, ingredientOption));
        } catch (JsonParseException e) {
            // Do Nothing
        }

        JsonArray optionsJson = json.getAsJsonArray();
        NonNullList<IngredientOption> ingredientOptions = NonNullList.createWithCapacity(optionsJson.size());
        for (int i = 0; i < optionsJson.size(); i++) {
            ingredientOptions.add(IngredientOption.fromJson(optionsJson.get(i)));
        }

        return new ChampiumForgeIngredient(ingredientOptions);
    }

    public static void toNetwork(FriendlyByteBuf buffer, ChampiumForgeIngredient ingredient) {
        buffer.writeCollection(ingredient.ingredientOptions, IngredientOption::toNetwork);
    }

    public static ChampiumForgeIngredient fromNetwork(FriendlyByteBuf buffer) {
        return new ChampiumForgeIngredient(
                buffer.readCollection(
                        NonNullList::createWithCapacity,
                        IngredientOption::fromNetwork));
    }

    public List<IngredientOption> getIngredientOptions() {
        return ingredientOptions;
    }

    public List<IngredientOption> getValidIngredientOptions() {
        return ingredientOptions.stream()
                .filter(IngredientOption::isValid)
                .flatMap(option -> !option.expandItems ? Stream.of(option)
                        : Arrays.stream(option.ingredient.getItems()).map(item ->
                                new IngredientOption(Ingredient.of(item), option.weight, false)))
                .toList();
    }

    public IngredientOption getIngredient(RandomSource random) {
        List<IngredientOption> ingredientOptions = getValidIngredientOptions();
        if (ingredientOptions.isEmpty()) return IngredientOption.EMPTY;
        int maxWeight = ingredientOptions.stream().mapToInt(IngredientOption::weight).sum();
        int desiredWeight = random.nextInt(maxWeight);

        for (IngredientOption ingredientOption : ingredientOptions) {
            if (desiredWeight < ingredientOption.weight()) {
                return ingredientOption;
            } else {
                desiredWeight -= ingredientOption.weight();
            }
        }

        return IngredientOption.EMPTY;
    }

    public boolean isValid() {
        return getIngredientOptions().stream().anyMatch(IngredientOption::isValid);
    }

    public record IngredientOption(Ingredient ingredient, int weight, boolean expandItems) {

        public static final IngredientOption EMPTY = new IngredientOption(Ingredient.EMPTY, 0, false);

        public static final String INGREDIENT_KEY = "ingredient";
        public static final String WEIGHT_KEY = "weight";
        public static final String EXPAND_ITEMS_KEY = "expand_items";

        public boolean isValid() {
            ItemStack[] items = ingredient.getItems();
            if (items.length == 0) return false;
            else if (items.length > 1) return true;
            return items[0].getItem() != Items.BARRIER;
        }

        public boolean test(@Nullable ItemStack stack) {
            return ingredient.test(stack);
        }

        public JsonElement toJson() {
            if (weight == 1 && !expandItems) return ingredient.toJson();

            JsonObject json = new JsonObject();
            json.add(INGREDIENT_KEY, ingredient.toJson());
            if (weight != 1) {
                json.addProperty(WEIGHT_KEY, weight);
            }
            if (expandItems) {
                json.addProperty(EXPAND_ITEMS_KEY, true);
            }
            return json;
        }

        public static IngredientOption fromJson(JsonElement json) {
            try {
                Ingredient ingredient = Ingredient.fromJson(json);
                return new IngredientOption(ingredient, 1, false);
            } catch (JsonParseException e) {
                // Do Nothing
            }

            JsonObject ingredientObject = json.getAsJsonObject();

            if (!ingredientObject.has(INGREDIENT_KEY)) throw new JsonSyntaxException("Invalid json: " + ingredientObject);
            Ingredient ingredient = Ingredient.fromJson(json.getAsJsonObject().get(INGREDIENT_KEY));

            int weight = 1;
            if (ingredientObject.has(WEIGHT_KEY)) {
                weight = ingredientObject.get(WEIGHT_KEY).getAsInt();
            }

            boolean expandItems = false;
            if (ingredientObject.has(EXPAND_ITEMS_KEY)) {
                expandItems = ingredientObject.get(EXPAND_ITEMS_KEY).getAsBoolean();
            }

            return new IngredientOption(ingredient, weight, expandItems);
        }

        public static void toNetwork(FriendlyByteBuf buffer, IngredientOption option) {
            buffer.writeUtf(option.ingredient.toJson().toString());
            buffer.writeInt(option.weight);
            buffer.writeBoolean(option.expandItems);
        }

        public static IngredientOption fromNetwork(FriendlyByteBuf buffer) {
            return new IngredientOption(
                    Ingredient.fromJson(GsonHelper.parse(buffer.readUtf())),
                    buffer.readInt(),
                    buffer.readBoolean());
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof IngredientOption option)) return false;
            if (option.weight != weight) return false;
            if (option.expandItems != expandItems) return false;
            if (!option.ingredient.toJson().equals(ingredient.toJson())) return false;
            return true;
        }
    }

    public static class Builder {
        final NonNullList<IngredientOption> ingredientOptions = NonNullList.create();

        public Builder addIngredient(Ingredient ingredient) {
            return addIngredient(ingredient, 1, false);
        }

        @SuppressWarnings("unused")
        public Builder addIngredient(Ingredient ingredient, int weight) {
            return addIngredient(ingredient, weight, false);
        }

        public Builder addIngredient(Ingredient ingredient, boolean expandItems) {
            return addIngredient(ingredient, 1, expandItems);
        }

        public Builder addIngredient(Ingredient ingredient, int weight, boolean expandItems) {
            ingredientOptions.add(new IngredientOption(ingredient, weight, expandItems));
            return this;
        }

        public ChampiumForgeIngredient build() {
            return new ChampiumForgeIngredient(ingredientOptions);
        }
    }
}
