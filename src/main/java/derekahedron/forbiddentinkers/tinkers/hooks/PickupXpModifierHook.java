package derekahedron.forbiddentinkers.tinkers.hooks;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.Collection;
import java.util.List;

public interface PickupXpModifierHook {
    List<EquipmentSlot> SLOT_ORDER = List.of(
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND,
            EquipmentSlot.HEAD,
            EquipmentSlot.CHEST,
            EquipmentSlot.LEGS,
            EquipmentSlot.FEET);

    void onPickupXp(
            IToolStackView tool, ModifierEntry modifier,
            Player player, EquipmentSlot slot,
            ExperienceOrb orb);

    record AllMerger(Collection<PickupXpModifierHook> modules) implements PickupXpModifierHook {

        public void onPickupXp(
                IToolStackView tool, ModifierEntry modifier,
                Player player, EquipmentSlot slot,
                ExperienceOrb orb) {
            for (PickupXpModifierHook module : modules) {
                module.onPickupXp(tool, modifier, player, slot, orb);
            }
        }
    }

    static void pickup(ExperienceOrb orb, Player player) {
        for (EquipmentSlot slot : SLOT_ORDER) {
            ItemStack equipmentStack = player.getItemBySlot(slot);

            if (equipmentStack.getItem() instanceof IModifiable) {
                ToolStack tool = ToolStack.from(equipmentStack);

                for (ModifierEntry modifier : tool.getModifiers()) {
                    modifier.getHook(FTModifierHooks.PICKUP_XP)
                            .onPickupXp(tool, modifier, player, slot, orb);
                }
            }
        }
    }
}
