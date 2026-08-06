package derekahedron.forbiddentinkers.network;

import derekahedron.forbiddentinkers.item.ClownNoseItem;
import derekahedron.forbiddentinkers.tinkers.modifiers.FunnyModifier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record HonkClownNosePacket(int slotId) {

    public HonkClownNosePacket(FriendlyByteBuf buffer) {
        this(buffer.readInt());
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(slotId);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            ServerPlayer player = context.get().getSender();
            if (player == null) return;

            if (!player.gameMode.isCreative()) {
                if (slotId < 0 || slotId >= player.containerMenu.slots.size()) return;
                if (!(player.containerMenu.getSlot(slotId).getItem().getItem() instanceof ClownNoseItem)) return;
            }
            FunnyModifier.playHonkSound(player, 1);
        });
        context.get().setPacketHandled(true);
    }
}
