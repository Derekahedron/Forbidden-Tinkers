package derekahedron.forbiddentinkers.tinkers;

import derekahedron.forbiddentinkers.registry.FTRegistryKeys;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DivineSlotsManager {
    public static final Map<ModifierId, List<DivineSlots>> SLOTS_MAP = new HashMap<>();

    public static int getSlotsToBeAdded(ModifierId modifierId, SlotType slotType, int modifierLevel) {
        return getDivineSlots(modifierId, modifierLevel).stream()
                .filter(slots -> modifierLevel <= slots.maxLevel())
                .filter(slots -> slots.slots().isPresent())
                .filter(slots -> slots.slots().get().type() == slotType)
                .mapToInt(slots -> slots.slots().get().count())
                .sum();
    }

    public static List<SlotType.SlotCount> getAddedSlots(ModifierId modifierId, int modifierLevel) {
        return getDivineSlots(modifierId, modifierLevel).stream()
                .filter(slots -> slots.slots().isPresent())
                .map(slots -> new SlotType.SlotCount(
                        slots.slots().get().type(),
                        slots.slots().get().count() * (Math.min(modifierLevel, slots.maxLevel()) - slots.minLevel())))
                .toList();
    }

    public static List<DivineSlots> getDivineSlots(ModifierId modifierId, int modifierLevel) {
        return SLOTS_MAP.getOrDefault(modifierId, List.of()).stream()
                .filter(slots -> slots.slots().isPresent())
                .filter(slots -> modifierLevel >= slots.minLevel())
                .toList();
    }

    public static void loadSlots(RegistryAccess registryAccess) {
        Registry<DivineSlots> slotsRegistry = registryAccess.registryOrThrow(FTRegistryKeys.DIVINE_SLOTS);
        SLOTS_MAP.clear();

        slotsRegistry.forEach(slots -> {
            if (slots.slots().isEmpty()) return;
            if (SLOTS_MAP.containsKey(slots.modifierId())) {
                SLOTS_MAP.get(slots.modifierId()).add(slots);
            } else {
                SLOTS_MAP.put(slots.modifierId(), new ArrayList<>(List.of(slots)));
            }
        });
    }
}
