package derekahedron.forbiddentinkers.network;

import derekahedron.forbiddentinkers.client.network.ChampiumSmokePacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record ChampiumSmokePacket(BlockPos blockPos) {

    public ChampiumSmokePacket(FriendlyByteBuf buffer) {
        this(new BlockPos(buffer.readInt(), buffer.readInt(), buffer.readInt()));
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeInt(blockPos.getX());
        buffer.writeInt(blockPos.getY());
        buffer.writeInt(blockPos.getZ());
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ChampiumSmokePacketHandler.handlePacket(this)));
        context.get().setPacketHandled(true);
    }
}
