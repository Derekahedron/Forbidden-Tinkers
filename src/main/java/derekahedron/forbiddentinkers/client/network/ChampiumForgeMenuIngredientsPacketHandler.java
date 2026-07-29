package derekahedron.forbiddentinkers.client.network;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import derekahedron.forbiddentinkers.network.ChampiumForgeMenuIngredientsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ChampiumForgeMenuIngredientsPacketHandler {

    public static void handlePacket(ChampiumForgeMenuIngredientsPacket packet) {
        Player player = Minecraft.getInstance().player;
        Level level = Minecraft.getInstance().level;
        if (player == null || level == null || !packet.dimension().equals(level.dimension())) return;
        if (!(level.getBlockEntity(packet.blockPos()) instanceof ChampiumForgeBlockEntity blockEntity)) return;
        blockEntity.ingredients = packet.ingredients().orElse(null);
    }
}
