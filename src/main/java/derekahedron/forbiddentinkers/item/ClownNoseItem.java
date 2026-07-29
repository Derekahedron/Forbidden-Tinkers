package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.network.FTPacketHandler;
import derekahedron.forbiddentinkers.network.HonkClownNosePacket;
import derekahedron.forbiddentinkers.tinkers.modifiers.FunnyModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClownNoseItem extends Item {

    public ClownNoseItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!player.level().isClientSide) {
            FunnyModifier.playHonkSound(player, 1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public boolean overrideOtherStackedOnMe(
            ItemStack stack,
            ItemStack otherStack,
            Slot slot,
            ClickAction clickAction,
            Player player,
            SlotAccess slotAccess) {
        if (clickAction != ClickAction.SECONDARY
                || !otherStack.isEmpty()
                || stack.getCount() > 1) return false;

        if (player.level().isClientSide) {
            FTPacketHandler.INSTANCE.sendToServer(new HonkClownNosePacket(slot.index));
        }

        return true;
    }
}
