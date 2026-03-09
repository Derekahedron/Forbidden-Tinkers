package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Rarity;

import java.util.List;


public class FTRarity {

    public static final Rarity TINKERS_DIAMOND =
            Rarity.create("tinkers_diamond", FTUtil.cycleColor(List.of(
                            TextColor.fromRgb(0xFFFF0000),  // Red
                            TextColor.fromRgb(0xFFFF7F00),  // Orange
                            TextColor.fromRgb(0xFFFFFF00),  // Yellow
                            TextColor.fromRgb(0xFF00FF00),  // Green
                            TextColor.fromRgb(0xFF003FFF),  // Blue
                            TextColor.fromRgb(0xFFBF00FF)), // Purple
                    16));

    public static final Rarity CHAMPIUM =
            Rarity.create("champium", style -> style.withColor(0xFFFFC13E));
}
