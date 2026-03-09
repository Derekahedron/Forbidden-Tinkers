package derekahedron.forbiddentinkers.client.inventory;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

import static derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu.*;

public class ChampiumForgeScreen extends AbstractContainerScreen<ChampiumForgeMenu> {
    public static final ResourceLocation FOLDER = FTUtil.location("textures/gui/container/champium_forge/");
    public static final ResourceLocation BACKGROUND_TEXTURE = FOLDER.withSuffix("background.png");
    public static final ResourceLocation SLOT_TEXTURE = FOLDER.withSuffix("slot.png");
    public static final ResourceLocation INVALID_INPUT_TEXTURE = FOLDER.withSuffix("invalid_input.png");
    public static final ResourceLocation FILL_BAR_TEXTURE = FOLDER.withSuffix("fill_bar.png");
    public static final int ITEM_CYCLE_TIME_MILLIS = 2000; // Two seconds
    public final long screenOpenedMillis;
    public int baseX;
    public int baseY;

    public ChampiumForgeScreen(ChampiumForgeMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        screenOpenedMillis = System.currentTimeMillis();
        imageWidth = WIDTH;
        imageHeight = HEIGHT;
        inventoryLabelX = (imageWidth - 9 * SLOT_SIZE) / 2;
        inventoryLabelY = imageHeight - BORDER - PADDING - SLOT_SIZE - PADDING - SLOT_SIZE * 3 - FONT_HEIGHT + FONT_PADDING;
    }

    public void init() {
        super.init();
        titleLabelX = (imageWidth - font.width(title)) / 2;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        baseX = (width - imageWidth) / 2;
        baseY = (height - imageHeight) / 2;
        graphics.blit(BACKGROUND_TEXTURE, baseX, baseY, 0, 0, imageWidth, imageHeight);

        if (menu.blockEntity.recipe != null) {
            renderRecipe(graphics, menu.blockEntity.recipe);
        }
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, delta);
        renderTooltip(graphics, mouseX, mouseY);
    }

    public void renderRecipe(GuiGraphics graphics, ChampiumForgeRecipe recipe) {
        int numIngredients = recipe.ingredients.size();
        int x = (imageWidth - SLOT_SIZE * numIngredients - PADDING * Math.max(numIngredients - 1, 0)) / 2;
        int y = BORDER + FONT_MARGIN + FONT_HEIGHT;
        long cycles = (System.currentTimeMillis() - screenOpenedMillis) / ITEM_CYCLE_TIME_MILLIS;

        for (int i = 0; i < numIngredients; i++) {
            int slotX = baseX + x + i * (SLOT_SIZE + PADDING);

            ItemStack[] displayStacks = recipe.ingredients.get(i).getItems();
            if (displayStacks.length > 0) {
                ItemStack displayStack = displayStacks[(int) cycles % displayStacks.length];

                graphics.renderItem(displayStack,
                        slotX + SLOT_BORDER,
                        baseY + y);
            }

            if (!menu.blockEntity.hasUsesRemaining()) {
                graphics.pose().pushPose();
                graphics.pose().translate(0, 0, 200F);
                graphics.blit(INVALID_INPUT_TEXTURE,
                        slotX + SLOT_BORDER,
                        baseY + y,
                        0, 0,
                        16, 16,
                        16, 16);
                graphics.pose().popPose();
            }

            graphics.blit(SLOT_TEXTURE,
                    slotX,
                    baseY + y + ITEM_SIZE + INPUTS_GAP,
                    0, 0,
                    SLOT_SIZE, SLOT_SIZE,
                    SLOT_SIZE, SLOT_SIZE);
        }

        x = (imageWidth - FILL_BAR_WIDTH) / 2;
        y += ITEM_SIZE + INPUTS_GAP + SLOT_SIZE + PADDING + FILL_BAR_BORDER;
//        float fill = recipe.count <= 1 ? 0F
//                : Math.min((float) menu.blockEntity.count / (recipe.count - 1), 1.0F);
        float fill;
        if (menu.blockEntity.cooldown > 0 && menu.blockEntity.count > 0) {
            float cooldownFill = 1 - (float) menu.blockEntity.cooldown / ChampiumForgeBlockEntity.COOLDOWN;
            fill = Mth.lerp(cooldownFill, menu.blockEntity.count - 1, menu.blockEntity.count) / recipe.count;
        } else {
            fill = (float) menu.blockEntity.count / recipe.count;
        }
        int barWidth = Mth.ceil(FILL_BAR_WIDTH * fill);

        graphics.blit(FILL_BAR_TEXTURE,
                baseX + x,
                baseY + y,
                0, 0,
                barWidth, FILL_BAR_HEIGHT,
                FILL_BAR_WIDTH, FILL_BAR_HEIGHT);
    }
}
