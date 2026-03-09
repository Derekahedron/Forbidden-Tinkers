package derekahedron.forbiddentinkers.util;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import java.util.List;
import java.util.function.UnaryOperator;

public class FTUtil {
    public static long FRAME = 0;

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ForbiddenTinkers.MOD_ID, path);
    }

    public static UnaryOperator<Style> cycleColor(List<TextColor> colors, final int frameTime) {
        if (colors.isEmpty()) return style -> style;
        final int totalFrames = frameTime * colors.size();

        return style -> {
            int frames = (int) FRAME % totalFrames;
            int index = frames / frameTime;
            int nextIndex = (index + 1) % colors.size();
            float amount = (float) (frames % frameTime) / frameTime;
            return style.withColor(lerpColor(amount, colors.get(index), colors.get(nextIndex)));
        };
    }

    public static TextColor lerpColor(float amount, TextColor color1, TextColor color2) {
        int rgb1 = color1.getValue();
        int rgb2 = color2.getValue();
        int alpha = Mth.lerpInt(amount, rgb1 >> 24 & 255, rgb2 >> 24 & 255);
        int red = Mth.lerpInt(amount, rgb1 >> 16 & 255, rgb2 >> 16 & 255);
        int green = Mth.lerpInt(amount, rgb1 >> 8 & 255, rgb2 >> 8 & 255);
        int blue = Mth.lerpInt(amount, rgb1 & 255, rgb2 & 255);
        return TextColor.fromRgb((alpha << 24) | (red << 16) | (green << 8) | blue);
    }
}
