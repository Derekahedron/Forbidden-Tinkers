package derekahedron.forbiddentinkers.jei;

import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.placement.HorizontalAlignment;
import mezz.jei.api.gui.placement.VerticalAlignment;
import mezz.jei.api.gui.widgets.IRecipeWidget;
import mezz.jei.api.gui.widgets.ITextWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.navigation.ScreenPosition;
import net.minecraft.network.chat.Component;

/**
 * Special widget that represents a single line of text with a tooltip that renders
 * when hovered over.
 */
public class TooltipTextWidget implements ITextWidget, IRecipeWidget {

    public static final int DEFAULT_COLOR = 0xFF000000;

    public final Component labelText;
    public final Component tooltipText;
    private ScreenPosition screenPosition;
    private HorizontalAlignment horizontalAlignment;
    private VerticalAlignment verticalAlignment;
    private Font font;
    private int color;
    private boolean shadow;
    public int width;
    public int height;

    public TooltipTextWidget(Component labelText, Component tooltipText) {
        this.labelText = labelText;
        this.tooltipText = tooltipText;
        this.screenPosition = new ScreenPosition(0, 0);
        this.font = Minecraft.getInstance().font;
        this.color = DEFAULT_COLOR;
        this.horizontalAlignment = HorizontalAlignment.LEFT;
        this.verticalAlignment = VerticalAlignment.TOP;
        this.width = font.width(labelText);
        this.height = font.lineHeight;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public TooltipTextWidget setPosition(int xPos, int yPos) {
        this.screenPosition = new ScreenPosition(xPos, yPos);
        return this;
    }

    @Override
    public TooltipTextWidget setTextAlignment(HorizontalAlignment horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
        return this;
    }

    @Override
    public TooltipTextWidget setTextAlignment(VerticalAlignment verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
        return this;
    }

    @Override
    public TooltipTextWidget setFont(Font font) {
        this.font = font;
        this.width = font.width(labelText);
        this.height = font.lineHeight;
        return this;
    }

    @Override
    public TooltipTextWidget setColor(int color) {
        this.color = color;
        return this;
    }

    @Override
    public TooltipTextWidget setLineSpacing(int lineSpacing) {
        return this;
    }

    @Override
    public TooltipTextWidget setShadow(boolean shadow) {
        this.shadow = shadow;
        return this;
    }

    @Override
    public ScreenPosition getPosition() {
        return this.screenPosition;
    }

    @Override
    public void drawWidget(GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.drawString(
                font,
                labelText,
                horizontalAlignment.getXPos(0, width),
                verticalAlignment.getYPos(0, height),
                color,
                shadow);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, double mouseX, double mouseY) {
        mouseX -= horizontalAlignment.getXPos(0, width);
        mouseY -= verticalAlignment.getYPos(0, height);

        if (mouseX >= 0.0F
                && mouseX < this.width
                && mouseY >= 0.0F
                && mouseY < this.height) {
            tooltip.add(tooltipText);
        }
    }
}
