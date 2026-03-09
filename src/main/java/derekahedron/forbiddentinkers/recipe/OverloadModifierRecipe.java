package derekahedron.forbiddentinkers.recipe;

import derekahedron.forbiddentinkers.item.ForbiddenTomeItem;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.ingredient.SizedIngredient;
import slimeknights.tconstruct.common.recipe.RecipeCacheInvalidator;
import slimeknights.tconstruct.library.json.field.MergingField;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class OverloadModifierRecipe extends SwappableModifierRecipe {
    public static final List<OverloadModifierRecipe> RECIPES = new ArrayList<>();
    private static final RecipeCacheInvalidator.DuelSidedListener LISTENER =
            RecipeCacheInvalidator.addDuelSidedListener(RECIPES::clear);

    public static final RecordLoadable<OverloadModifierRecipe> LOADER =
            RecordLoadable.create(
                    ContextKey.ID.requiredField(),
                    INPUTS_FIELD,
                    TOOLS_FIELD,
                    MAX_TOOL_SIZE_FIELD,
                    new MergingField<>(ModifierId.PARSER.requiredField(
                            "name",
                            (r) -> r.result.getId()),
                            "result", MergingField.MissingMode.DISALLOWED),
                    new MergingField<>(ModifierId.PARSER.requiredField(
                            "value", (r) -> r.modifierId),
                            "result",
                            MergingField.MissingMode.DISALLOWED),
                    SwappableModifierRecipe.VariantFormatter.LOADER.defaultField(
                            "variant_formatter",
                            SwappableModifierRecipe.VariantFormatter.DEFAULT,
                            (r) -> r.variantFormatter),
                    SLOTS_FIELD,
                    ALLOW_CRYSTAL_FIELD,
                    OverloadModifierRecipe::new);
    public static final VariantFormatter MODIFIER_VARIANT_FORMATER =
            VariantFormatter.LOADER.register(FTUtil.location("modifier"), (modifier, variant) ->
                    Component.translatable(Util.makeTranslationKey("modifier", Objects.requireNonNullElse(ModifierId.tryParse(variant), ModifierManager.EMPTY))));

    public final ModifierId modifierId;
    public final VariantFormatter variantFormatter;

    public OverloadModifierRecipe(
            ResourceLocation id, List<SizedIngredient> inputs, Ingredient toolRequirement,
            int maxToolSize, ModifierId result, ModifierId modifierId,
            VariantFormatter variantFormatter, @Nullable SlotType.SlotCount slots, boolean allowCrystal) {
        super(id, inputs, toolRequirement, maxToolSize, result, modifierId.toString(), variantFormatter, slots, allowCrystal);
        this.modifierId = modifierId;
        this.variantFormatter = variantFormatter;
        LISTENER.checkClear();
        RECIPES.add(this);
    }

    @Override
    public boolean matches(ITinkerStationContainer inv, Level level) {
        if (!super.matches(inv, level)) return false;
        if (matchesCrystal(inv)) return true;

        for (int i = 0; i < inv.getInputCount(); i++) {
            ItemStack stack = inv.getInput(i);
            if (stack.getItem() instanceof ForbiddenTomeItem) {
                if (!modifierId.equals(ForbiddenTomeItem.getModifier(stack))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<ItemStack> getDisplayItems(int slot) {
        List<ItemStack> stacks = super.getDisplayItems(slot);
        stacks.forEach(stack -> {
            if (stack.getItem() instanceof ForbiddenTomeItem item) {
                item.addModifier(stack, modifierId);
            }
        });
        return stacks;
    }

    @Override
    public RecipeSerializer<OverloadModifierRecipe> getSerializer() {
        return FTRecipeSerializers.OVERLOADED_MODIFIER.get();
    }

    public static class Builder extends SwappableModifierRecipeBuilder {
        public final ModifierId modifierId;
        public VariantFormatter variantFormatter;

        protected Builder(ModifierId modifier, ModifierId modifierId) {
            super(modifier, modifierId.toString());
            this.modifierId = modifierId;
            this.variantFormatter = VariantFormatter.DEFAULT;
        }

        public static Builder modifier(ModifierId modifier, ModifierId modifierId) {
            return new Builder(modifier, modifierId);
        }

        public static Builder modifier(LazyModifier modifier, ModifierId modifierId) {
            return modifier(modifier.getId(), modifierId);
        }

        public Builder variantFormatter(VariantFormatter variantFormatter) {
            super.variantFormatter(variantFormatter);
            this.variantFormatter = variantFormatter;
            return this;
        }

        public void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
            if (inputs.isEmpty()) {
                throw new IllegalStateException("Must have at least 1 input");
            } else {
                ResourceLocation advancementId = this.buildOptionalAdvancement(id, "modifiers");
                consumer.accept(new LoadableFinishedRecipe<>(new OverloadModifierRecipe(
                        id,
                        inputs,
                        tools,
                        maxToolSize,
                        result,
                        modifierId,
                        variantFormatter,
                        slots,
                        allowCrystal
                ), LOADER, advancementId));
            }
        }
    }
}
