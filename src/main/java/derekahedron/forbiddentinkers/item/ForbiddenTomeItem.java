package derekahedron.forbiddentinkers.item;

import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import slimeknights.mantle.client.ResourceColorManager;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

import javax.annotation.Nullable;
import java.util.List;

public class ForbiddenTomeItem extends Item {
    public static final String MODIFIER_KEY = "Modifier";

    public ForbiddenTomeItem(Properties properties) {
        super(properties);
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag advanced) {
        ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(this);
        if (itemId == null) return;
        String descriptionKey = Util.makeDescriptionId("item", itemId) + ".description";

        ModifierId modifierId = getModifier(stack);
        if (modifierId != null && ModifierManager.INSTANCE.contains(modifierId)) {
            String translationKey = Util.makeDescriptionId("modifier", modifierId);

            MutableComponent modifierName = Component.translatable(translationKey);
            TextColor modifierColor = ResourceColorManager.getTextColor(translationKey);
            tooltip.add(modifierName.withStyle(style -> style.withColor(modifierColor)));

            tooltip.add(Component.translatable(descriptionKey, Component.translatable(translationKey))
                    .withStyle(ChatFormatting.GRAY));

            if (advanced.isAdvanced()) {
                tooltip.add(Component.translatable(descriptionKey + ".modifier_id", modifierId)
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
        } else {
            tooltip.add(Component.translatable(descriptionKey + ".missing")
                    .withStyle(ChatFormatting.GRAY));
        }
    }

    public ItemStack withModifier(ModifierId modifierId) {
        ItemStack stack = new ItemStack(this);
        addModifier(stack, modifierId);
        return stack;
    }

    public void addModifier(ItemStack stack, ModifierId modifierId) {
        stack.getOrCreateTag().putString(MODIFIER_KEY, modifierId.toString());
    }

    @Nullable
    public static ModifierId getModifier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null ? ModifierId.tryParse(tag.getString(MODIFIER_KEY)) : null;
    }
}
