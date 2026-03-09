package derekahedron.forbiddentinkers.recipe;

import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import slimeknights.mantle.data.loadable.field.ContextKey;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.recipe.ingredient.SizedIngredient;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.json.field.MergingField;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IDisplayModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.tinkerstation.ITinkerStationContainer;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.material.ToolPartsHook;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.MaterialItem;
import slimeknights.tconstruct.library.tools.part.ToolPartItem;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class AugmentationModifierRecipe extends SwappableModifierRecipe {
    public static final RecordLoadable<AugmentationModifierRecipe> LOADER =
            RecordLoadable.create(
                    ContextKey.ID.requiredField(),
                    INPUTS_FIELD,
                    TOOLS_FIELD,
                    MAX_TOOL_SIZE_FIELD,
                    new MergingField<>(ModifierId.PARSER.requiredField(
                            "name",
                            (r) -> r.result.getId()),
                            "result", MergingField.MissingMode.DISALLOWED),
                    new MergingField<>(MaterialVariantId.LOADABLE.requiredField(
                            "value", (r) -> r.materialId),
                            "result",
                            MergingField.MissingMode.DISALLOWED),
                    SwappableModifierRecipe.VariantFormatter.LOADER.defaultField(
                            "variant_formatter",
                            SwappableModifierRecipe.VariantFormatter.DEFAULT,
                            (r) -> r.variantFormatter),
                    SLOTS_FIELD,
                    ALLOW_CRYSTAL_FIELD,
                    AugmentationModifierRecipe::new);
    private @Nullable List<ItemStack> toolWithModifier;

    public final MaterialVariantId materialId;
    public final VariantFormatter variantFormatter;

    public AugmentationModifierRecipe(
            ResourceLocation id, List<SizedIngredient> inputs, Ingredient toolRequirement,
            int maxToolSize, ModifierId result, MaterialVariantId materialId,
            VariantFormatter variantFormatter, @Nullable SlotType.SlotCount slots, boolean allowCrystal) {
        super(id, inputs, toolRequirement, maxToolSize, result, materialId.toString(), variantFormatter, slots, allowCrystal);
        this.materialId = materialId;
        this.variantFormatter = variantFormatter;
    }

    @Override
    public boolean matches(ITinkerStationContainer inv, Level level) {
        if (!super.matches(inv, level) || !canBeAppliedToTool(inv.getTinkerable())) return false;
        if (matchesCrystal(inv)) return true;

        for (int i = 0; i < inv.getInputCount(); i++) {
            ItemStack stack = inv.getInput(i);
            if (stack.getItem() instanceof MaterialItem materialItem) {
                if (!materialId.matchesVariant(materialItem.getMaterial(stack))) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public List<ItemStack> getToolWithModifier() {
        if (toolWithModifier == null) {
            // Add the modifier traits from all materials to each tool.
            toolWithModifier = super.getToolWithModifier().stream()
                    .map((stack) -> {
                        if (stack.is(TinkerTags.Items.MODIFIABLE)) {
                            ToolStack tool = ToolStack.from(stack);
                            ToolDefinition toolDefinition = tool.getDefinition();
                            ModifierEntry result = getDisplayResult();
                            List<ModifierEntry> modifiers = new ArrayList<>(
                                    IDisplayModifierRecipe.modifiersForResult(result, result));
                            modifiers.addAll(MTUtil.getMaterialModifiers(
                                    materialId.getId(),
                                    toolDefinition));

                            if (!modifiers.isEmpty()) {
                                return IDisplayModifierRecipe.withModifiers(stack, maxToolSize, modifiers,
                                        (data) -> data.putString(result.getId(), materialId.toString()));
                            }
                        }
                        return stack;
                    })
                    .toList();
        }

        return toolWithModifier;
    }

    @Override
    protected List<ItemStack> getToolInputs() {
        return super.getToolInputs().stream()
                .filter(stack -> canBeAppliedToTool(ToolStack.from(stack)))
                .toList();
    }

    @Override
    public List<ItemStack> getDisplayItems(int slot) {
        List<ItemStack> stacks = super.getDisplayItems(slot);

        stacks.removeIf(stack ->
                stack.getItem() instanceof ToolPartItem toolPartItem
                        && !toolPartItem.canUseMaterial(materialId.getId()));

        stacks.forEach(stack -> {
            if (stack.getItem() instanceof MaterialItem) {
                stack.getOrCreateTag().putString("Material", materialId.toString());
            }
        });

        return stacks;
    }

    public boolean canBeAppliedToTool(ToolStack tool) {
        return ToolPartsHook.parts(tool.getDefinition())
                .stream()
                .anyMatch(toolPart -> toolPart.canUseMaterial(materialId.getId()));
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return FTRecipeSerializers.AUGMENTATION_MODIFIER.get();
    }

    public static class Builder extends SwappableModifierRecipeBuilder {
        public final MaterialVariantId materialId;
        public VariantFormatter variantFormatter;

        protected Builder(ModifierId modifier, MaterialVariantId material) {
            super(modifier, material.toString());
            this.materialId = material;
            this.variantFormatter = VariantFormatter.DEFAULT;
        }

        public static Builder modifier(ModifierId modifier, MaterialVariantId material) {
            return new Builder(modifier, material);
        }

        public static Builder modifier(LazyModifier modifier, MaterialVariantId material) {
            return modifier(modifier.getId(), material);
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
                consumer.accept(new LoadableFinishedRecipe<>(new AugmentationModifierRecipe(
                        id,
                        inputs,
                        tools,
                        maxToolSize,
                        result,
                        materialId,
                        variantFormatter,
                        slots,
                        allowCrystal
                ), AugmentationModifierRecipe.LOADER, advancementId));
            }
        }
    }
}
