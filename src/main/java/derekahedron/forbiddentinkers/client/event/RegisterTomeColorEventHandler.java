package derekahedron.forbiddentinkers.client.event;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.ForbiddenTomeItem;
import derekahedron.forbiddentinkers.item.FTItems;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.mantle.client.ResourceColorManager;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.utils.Util;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RegisterTomeColorEventHandler {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                ModifierId modifier = ForbiddenTomeItem.getModifier(stack);
                if (modifier != null) {
                    return ResourceColorManager.getColor(Util.makeTranslationKey("modifier", modifier));
                }
            }
            return -1;
        }, FTItems.FORBIDDEN_TOME.get());
    }
}
