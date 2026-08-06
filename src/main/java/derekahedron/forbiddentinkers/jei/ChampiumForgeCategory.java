package derekahedron.forbiddentinkers.jei;

import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.client.inventory.ChampiumForgeScreen;
import derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeIngredient;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.recipe.FTRecipeTypes;
import derekahedron.forbiddentinkers.util.FTUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.IRecipeSlotBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.inputs.IJeiInputHandler;
import mezz.jei.api.gui.inputs.IJeiUserInput;
import mezz.jei.api.gui.inputs.RecipeSlotUnderMouse;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.gui.widgets.ISlottedRecipeWidget;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.lwjgl.glfw.GLFW;
import slimeknights.tconstruct.library.client.GuiUtil;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu.*;

public class ChampiumForgeCategory implements IRecipeCategory<ChampiumForgeRecipe> {

    public static final ResourceLocation UID = FTUtil.location("champium_forge");
    public static final RecipeType<ChampiumForgeRecipe> CHAMPIUM_FORGE_TYPE =
            new RecipeType<>(UID, ChampiumForgeRecipe.class);
    public static final String COUNT_TOOLTIP = Util.makeDescriptionId("jei", UID) + ".count";
    public static final String CHANCE_TOOLTIP = Util.makeDescriptionId("jei", UID) + ".chance";
    public static final String CYCLE_TOOLTIP = Util.makeDescriptionId("jei", UID) + ".chance.tooltip";
    public static final String RECIPE_CHANCE_LABEL = Util.makeDescriptionId("jei", UID) + ".recipe_chance";
    public static final String RECIPE_CHANCE_TOOLTIP = RECIPE_CHANCE_LABEL + ".tooltip";
    public static final String USES_LABEL = Util.makeDescriptionId("jei", UID) + ".uses";
    public static final String USES_RANGE_LABEL = USES_LABEL + ".range";
    public static final String USES_TOOLTIP = USES_LABEL + ".tooltip";
    public static final String USES_SINGLE_TOOLTIP = USES_TOOLTIP + ".single";
    public static final String USES_RANGE_TOOLTIP = USES_TOOLTIP + ".range";
    public static final DecimalFormat CHANCE_FORMAT = new DecimalFormat("0.##");
    public static final int TEXT_COLOR = 0xFF808080;
    public static final int WIDTH_OFFSET = BORDER + PADDING;
    public static final int HEIGHT_OFFSET = BORDER + FONT_MARGIN + FONT_HEIGHT + ITEM_SIZE + INPUTS_GAP;
    public static final int WIDTH = ChampiumForgeMenu.WIDTH - 2 * WIDTH_OFFSET;
    public static final int HEIGHT = SLOT_SIZE + PADDING + FILL_BAR_HEIGHT + FILL_BAR_BORDER * 2 + PADDING + RESULT_SLOT_SIZE;

    private final IDrawable icon;
    private final IDrawable background;

    public ChampiumForgeCategory(IGuiHelper guiHelper) {
        icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(FTBlocks.CHAMPIUM_FORGE.get()));
        background = guiHelper.createDrawable(ChampiumForgeScreen.BACKGROUND_TEXTURE,
                WIDTH_OFFSET, HEIGHT_OFFSET,
                WIDTH, HEIGHT);
    }

    @Override
    public RecipeType<ChampiumForgeRecipe> getRecipeType() {
        return CHAMPIUM_FORGE_TYPE;
    }

    @Override
    public Component getTitle() {
        return ChampiumForgeBlockEntity.TITLE;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ChampiumForgeRecipe recipe, IFocusGroup focusGroup) {
        int x;
        int y;

        x = (WIDTH - SLOT_SIZE * recipe.ingredients.size() - PADDING * Math.max(recipe.ingredients.size() - 1, 0)) / 2;
        y = SLOT_BORDER;
        for (int i = 0; i < recipe.ingredients.size(); i++) {
            x += SLOT_BORDER;
            IRecipeSlotBuilder slotBuilder = builder.addInputSlot(x, y);
            List<Ingredient> validIngredients =
                    recipe.ingredients.get(i).getValidIngredients();

            for (Ingredient ingredient : validIngredients) {
                slotBuilder = slotBuilder.addIngredients(ingredient);
            }
            x += ITEM_SIZE + SLOT_BORDER + PADDING;
        }

        x = (WIDTH - ITEM_SIZE) / 2;
        y += ITEM_SIZE + SLOT_BORDER + PADDING + FILL_BAR_HEIGHT + 2 * FILL_BAR_BORDER + PADDING + RESULT_SLOT_BORDER;

        builder.addOutputSlot(x, y)
                .addIngredients(Ingredient.of(recipe.result));
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, ChampiumForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        int x = (WIDTH - FILL_BAR_WIDTH) / 2;
        int y = SLOT_SIZE + PADDING + FILL_BAR_BORDER;

        if (GuiUtil.isHovered(Mth.floor(mouseX), Mth.floor(mouseY), x, y, FILL_BAR_WIDTH, FILL_BAR_HEIGHT)) {
            tooltip.add(Component.translatable(COUNT_TOOLTIP, recipe.count));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, ChampiumForgeRecipe recipe, IFocusGroup focusGroup) {
        List<IRecipeSlotDrawable> inputSlots = builder.getRecipeSlots().getSlots(RecipeIngredientRole.INPUT);

        addChanceWidget(builder, recipe);
        addMaxUsesWidget(builder, recipe);

        for (int i = 0; i < recipe.ingredients.size() && i < inputSlots.size(); i++) {
            ChampiumForgeIngredient ingredient = recipe.ingredients.get(i);
            if (ingredient.getValidIngredients().size() <= 1) continue;

            IRecipeSlotDrawable slot = inputSlots.get(i);
            RandomizedSlotWidget widget = new RandomizedSlotWidget(slot, ingredient);
            builder.addSlottedWidget(widget, List.of(slot));
            builder.addInputHandler(widget);
        }
    }

    public void addChanceWidget(IRecipeExtrasBuilder builder, ChampiumForgeRecipe recipe) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;

        List<ChampiumForgeRecipe> recipes = level.getRecipeManager()
                .getAllRecipesFor(FTRecipeTypes.CHAMPIUM_FORGE.get());
        int totalWeight = recipes.stream()
                .filter(ChampiumForgeRecipe::isValid)
                .mapToInt(r -> r.weight)
                .sum();

        String chanceString;
        if (totalWeight > 0) {
            chanceString = CHANCE_FORMAT.format(100.0D * recipe.weight / totalWeight);
        } else if (!recipes.isEmpty()) {
            chanceString = CHANCE_FORMAT.format(100.0D / recipes.size());
        } else {
            chanceString = CHANCE_FORMAT.format(0.0D);
        }

        Component component = Component.translatable(RECIPE_CHANCE_LABEL, chanceString);
        Component tooltipComponent = Component.translatable(RECIPE_CHANCE_TOOLTIP, chanceString);
        builder.addWidget(new TooltipTextWidget(component, tooltipComponent)
                .setPosition(WIDTH, HEIGHT)
                .setColor(TEXT_COLOR)
                .setTextAlignment(VerticalAlignment.BOTTOM)
                .setTextAlignment(HorizontalAlignment.RIGHT));
    }

    public void addMaxUsesWidget(IRecipeExtrasBuilder builder, ChampiumForgeRecipe recipe) {
        if (recipe.maxUses == null) return;

        final Component component;
        final Component tooltipComponent;

        int min = recipe.maxUses.getMinValue();
        int max = recipe.maxUses.getMaxValue();

        if (min == max) {
            component = Component.translatable(USES_LABEL, min);
            tooltipComponent = min == 1
                    ? Component.translatable(USES_SINGLE_TOOLTIP)
                    : Component.translatable(USES_TOOLTIP, min);
        } else {
            component = Component.translatable(USES_RANGE_LABEL, min, max);
            tooltipComponent = Component.translatable(USES_RANGE_TOOLTIP, min, max);
        }

        builder.addWidget(new TooltipTextWidget(component, tooltipComponent)
                .setPosition((WIDTH + RESULT_SLOT_SIZE) / 2 + PADDING, HEIGHT - (RESULT_SLOT_SIZE) / 2)
                .setColor(TEXT_COLOR)
                .setTextAlignment(VerticalAlignment.CENTER));
    }

    private static class RandomizedSlotWidget implements ISlottedRecipeWidget, IJeiInputHandler {

        public static final Component LABEL = Component.literal("?");
        public static final int COLOR = 0xFFFFFFFF;
        public static final int OVERHAND = 2;

        private final IRecipeSlotDrawable slot;
        private final List<IngredientChoice> choices;
        private final Rect2i area;
        private int index = 0;

        /**
         * Log of an ingredient option, and the chance it has to generate.
         *
         * @param ingredient the ingredient
         * @param chance the chance it has to generate
         */
        public record IngredientChoice(Ingredient ingredient, double chance) {}

        RandomizedSlotWidget(IRecipeSlotDrawable slot, ChampiumForgeIngredient ingredient) {
            this.slot = slot;
            List<ChampiumForgeIngredient.IngredientOption> validOptions = ingredient.getIngredientOptions().stream()
                    .filter(ChampiumForgeIngredient.IngredientOption::isValid)
                    .toList();

            int totalWeight = validOptions.stream()
                    .mapToInt(ChampiumForgeIngredient.IngredientOption::weight)
                    .sum();

            this.choices = validOptions.stream()
                    .flatMap(option -> {
                        double chance = totalWeight > 0
                                ? 100.0D * option.weight() / totalWeight
                                : 100.0D / validOptions.size();

                        if (option.expandItems()) {
                            ItemStack[] stacks = option.ingredient().getItems();
                            if (stacks.length == 0) return Stream.empty();
                            double subChance = chance / stacks.length;

                            return Arrays.stream(stacks)
                                    .map(stack -> new IngredientChoice(Ingredient.of(stack), subChance));
                        } else {
                            return Stream.of(new IngredientChoice(option.ingredient(), chance));
                        }
                    })
                    .toList();

            Font font = Minecraft.getInstance().font;
            int width = font.width(LABEL);
            int height = font.lineHeight;

            Rect2i slotArea = slot.getAreaIncludingBackground();
            area = new Rect2i(
                    slotArea.getX() + slotArea.getWidth() - width + OVERHAND,
                    slotArea.getY() + slotArea.getHeight() - height + OVERHAND,
                    width,
                    height);
            slot.clearDisplayOverrides();
            slot.createDisplayOverrides().addIngredients(choices.get(index).ingredient());
        }

        private boolean isMouseOver(double mouseX, double mouseY) {
            return GuiUtil.isHovered(Mth.floor(mouseX), Mth.floor(mouseY),
                    area.getX(), area.getY(), area.getWidth(), area.getHeight());
        }

        @Override
        public ScreenPosition getPosition() {
            return new ScreenPosition(0, 0);
        }

        @Override
        public ScreenRectangle getArea() {
            return new ScreenRectangle(getPosition(), WIDTH, HEIGHT);
        }

        @Override
        public void drawWidget(GuiGraphics guiGraphics, double mouseX, double mouseY) {
            slot.draw(guiGraphics);

            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(0.0F, 0.0F, 200.0F);
            guiGraphics.drawString(
                    Minecraft.getInstance().font,
                    LABEL,
                    area.getX(), area.getY(),
                    COLOR);
            guiGraphics.pose().popPose();
        }

        @Override
        public Optional<RecipeSlotUnderMouse> getSlotUnderMouse(double mouseX, double mouseY) {
            if (isMouseOver(mouseX, mouseY)) return Optional.empty();
            if (slot.isMouseOver(mouseX, mouseY)) {
                return Optional.of(new RecipeSlotUnderMouse(slot, 0, 0));
            }
            return Optional.empty();
        }

        @Override
        public void getTooltip(ITooltipBuilder tooltip, double mouseX, double mouseY) {
            if (!isMouseOver(mouseX, mouseY)) return;

            double chance = choices.get(index).chance;
            tooltip.add(Component.translatable(CHANCE_TOOLTIP, CHANCE_FORMAT.format(chance)));
            tooltip.add(Component.translatable(CYCLE_TOOLTIP).withStyle(ChatFormatting.GRAY));
        }

        @Override
        public boolean handleInput(double mouseX, double mouseY, IJeiUserInput input) {
            if (!isMouseOver(mouseX, mouseY)
                    || input.getKey().getValue() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return false;
            if (!input.isSimulate()) {
                index = (index + 1) % choices.size();
                slot.clearDisplayOverrides();
                slot.createDisplayOverrides().addIngredients(choices.get(index).ingredient());
            }
            return true;
        }
    }

    @Override
    public void draw(ChampiumForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int x;
        int y;

        background.draw(guiGraphics);

        int numSlots = recipe.ingredients.size();
        x = (WIDTH - SLOT_SIZE * numSlots - PADDING * Math.max(numSlots - 1, 0)) / 2;
        y = 0;

        for (int i = 0; i < numSlots; i++) {
            guiGraphics.blit(
                    ChampiumForgeScreen.SLOT_TEXTURE,
                    x, y,
                    0, 0,
                    SLOT_SIZE, SLOT_SIZE,
                    SLOT_SIZE, SLOT_SIZE);
            x += SLOT_SIZE + PADDING;
        }

        x = (WIDTH - FILL_BAR_WIDTH) / 2;
        y += SLOT_SIZE + PADDING + FILL_BAR_BORDER;
        guiGraphics.blit(
                ChampiumForgeScreen.FILL_BAR_TEXTURE,
                x, y,
                0, 0,
                FILL_BAR_WIDTH, FILL_BAR_HEIGHT,
                FILL_BAR_WIDTH, FILL_BAR_HEIGHT);
    }

    public static void registerGuiHandlers(IGuiHandlerRegistration registration) {
        // Add arrow click handler
        registration.addRecipeClickArea(
                ChampiumForgeScreen.class,
                (ChampiumForgeMenu.WIDTH - RESULT_SLOT_SIZE) / 2 - PADDING - 14,
                BORDER + FONT_MARGIN + FONT_HEIGHT + ITEM_SIZE + INPUTS_GAP + SLOT_SIZE + PADDING + FILL_BAR_HEIGHT + 2 * FILL_BAR_BORDER + PADDING,
                14, 18,
                ChampiumForgeCategory.CHAMPIUM_FORGE_TYPE);
    }
}
