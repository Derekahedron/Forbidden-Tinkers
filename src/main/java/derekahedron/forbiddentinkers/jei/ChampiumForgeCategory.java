package derekahedron.forbiddentinkers.jei;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.client.inventory.ChampiumForgeScreen;
import derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.util.FTUtil;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.tconstruct.library.client.GuiUtil;

import static derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu.*;

public class ChampiumForgeCategory implements IRecipeCategory<ChampiumForgeRecipe> {
    public static final ResourceLocation UID = FTUtil.location("champium_forge");
    public static final RecipeType<ChampiumForgeRecipe> CHAMPIUM_FORGE_TYPE =
            new RecipeType<>(UID, ChampiumForgeRecipe.class);
    public static final String COUNT_TOOLTIP = "jei." + ForbiddenTinkers.MOD_ID + ".champium_forge.count";
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

        x = (WIDTH - SLOT_SIZE * recipe.displayIngredients.size() - PADDING * Math.max(recipe.displayIngredients.size() - 1, 0)) / 2;
        y = SLOT_BORDER;
        for (int i = 0; i < recipe.displayIngredients.size(); i++) {
            x += SLOT_BORDER;
            builder.addInputSlot(x, y)
                    .addIngredients(recipe.displayIngredients.get(i));
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
    public void draw(ChampiumForgeRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        int x;
        int y;

        background.draw(guiGraphics);

        x = (WIDTH - SLOT_SIZE * recipe.displayIngredients.size() - PADDING * Math.max(recipe.displayIngredients.size() - 1, 0)) / 2;
        y = 0;

        for (int i = 0; i < recipe.displayIngredients.size(); i++) {
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
