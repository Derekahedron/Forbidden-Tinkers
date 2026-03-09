package derekahedron.forbiddentinkers.registry;

import derekahedron.forbiddentinkers.tinkers.DivineSlots;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.registries.DataPackRegistryEvent;

public class FTRegistryKeys {
    public static final ResourceKey<Registry<DivineSlots>> DIVINE_SLOTS =
            ResourceKey.createRegistryKey(FTUtil.location("divine_slots"));

    public static void initialize(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(DIVINE_SLOTS, DivineSlots.CODEC, DivineSlots.CODEC);
    }
}
