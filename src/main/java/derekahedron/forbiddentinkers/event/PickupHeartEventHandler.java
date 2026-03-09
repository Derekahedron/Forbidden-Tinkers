package derekahedron.forbiddentinkers.event;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.HeartItem;
import derekahedron.forbiddentinkers.mixin.ItemEntityAccessor;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class PickupHeartEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    static void pickupItemCheck(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.DENY) return;
        Player player = event.getEntity();
        ItemEntity itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItem();
        UUID target = ((ItemEntityAccessor) itemEntity).forbiddentinkers_$getTarget();

        if (((ItemEntityAccessor) (itemEntity)).forbiddentinkers_$getPickupDelay() == 0
                && !stack.isEmpty()
                && stack.getItem() instanceof HeartItem
                && (target == null || target.equals(player.getUUID()))) {
            ItemStack copy = stack.copy();

            HeartItem.healFromStack(player, stack);
            ForgeEventFactory.firePlayerItemPickupEvent(player, itemEntity, copy);
            player.take(itemEntity, copy.getCount());
            player.awardStat(Stats.ITEM_PICKED_UP.get(copy.getItem()), copy.getCount());
            event.setResult(Event.Result.ALLOW);
        }
    }
}
