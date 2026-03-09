package derekahedron.forbiddentinkers.client.network;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.network.ChampiumSmokePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;

public class ChampiumSmokePacketHandler {

    public static void handlePacket(ChampiumSmokePacket packet) {
        ClientLevel level = Minecraft.getInstance().level;
        Player player = Minecraft.getInstance().player;
        if (level == null || player == null) return;

        ChampiumForgeBlockEntity.createChampiumSmoke(level, packet.blockPos());
    }
}
