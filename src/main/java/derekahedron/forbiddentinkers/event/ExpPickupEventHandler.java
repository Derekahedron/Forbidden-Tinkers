package derekahedron.forbiddentinkers.event;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.hooks.PickupXpModifierHook;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ExpPickupEventHandler {

    @SubscribeEvent
    public static void pickupXp(PlayerXpEvent.PickupXp event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) {
            PickupXpModifierHook.pickup(event.getOrb(), player);
        }
    }
}
