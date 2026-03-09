package derekahedron.forbiddentinkers.event;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.DivineSlotsManager;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DataPackReloadEventHandler {

    @SubscribeEvent
    public static void registerDivineSlots(TagsUpdatedEvent event) {
        if (event.shouldUpdateStaticData()) {
            DivineSlotsManager.loadSlots(event.getRegistryAccess());
        }
    }
}
