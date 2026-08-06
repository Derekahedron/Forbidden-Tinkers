package derekahedron.forbiddentinkers.recipe;

import com.google.gson.*;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public record ChampiumForgeIngredient(NonNullList<IngredientOption> ingredientOptions) {

    public static final ChampiumForgeIngredient EMPTY = new ChampiumForgeIngredient(NonNullList.createWithCapacity(0));

    public JsonElement toJson() {
        JsonArray ingredientsJson = new JsonArray();
        for (IngredientOption ingredientOption : ingredientOptions) {
            ingredientsJson.add(ingredientOption.toJson());
        }
        return ingredientsJson;
    }

    public static ChampiumForgeIngredient fromJson(JsonElement json) {
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

    public List<Ingredient> getValidIngredients() {
        return ingredientOptions.stream()
                .filter(IngredientOption::isValid)
                .flatMap(option -> !option.expandItems
                        ? Stream.of(option.ingredient)
                        : Arrays.stream(option.ingredient.getItems()).map(Ingredient::of))
                .toList();
    }

    public Ingredient getIngredient(RandomSource random) {
        List<IngredientOption> validOptions = ingredientOptions.stream()
                .filter(IngredientOption::isValid)
                .toList();
        if (validOptions.isEmpty()) return Ingredient.EMPTY;
        int maxWeight = validOptions.stream()
                .mapToInt(IngredientOption::weight)
                .sum();
        int desiredWeight = random.nextInt(maxWeight);

        for (IngredientOption ingredientOption : ingredientOptions) {
            if (desiredWeight < ingredientOption.weight()) {
                if (ingredientOption.expandItems) {
                    ItemStack[] stacks = ingredientOption.ingredient.getItems();
                    if (stacks.length == 0) return Ingredient.EMPTY;

                    return Ingredient.of(stacks[random.nextInt(stacks.length)]);
                } else {
                    return ingredientOption.ingredient;
                }
            } else {
                desiredWeight -= ingredientOption.weight();
            }
        }

        return Ingredient.EMPTY;
    }

    public boolean isValid() {
        return getIngredientOptions().stream().anyMatch(IngredientOption::isValid);
    }

    public record IngredientOption(Ingredient ingredient, int weight, boolean expandItems) {

        public static final String INGREDIENT_KEY = "ingredient";
        public static final String WEIGHT_KEY = "weight";
        public static final String EXPAND_ITEMS_KEY = "expand_items";

        public boolean isValid() {
            ItemStack[] items = ingredient.getItems();
            if (items.length == 0) return false;
            else if (items.length > 1) return true;
            return items[0].getItem() != Items.BARRIER;
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
