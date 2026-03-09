package derekahedron.forbiddentinkers.network;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class FTPacketHandler {
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            FTUtil.location("main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );
    private static int id = 0;

    public static int getId() {
        return id++;
    }

    public static void initialize(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            INSTANCE.registerMessage(getId(),
                    ChampiumSmokePacket.class,
                    ChampiumSmokePacket::toBytes,
                    ChampiumSmokePacket::new,
                    ChampiumSmokePacket::handle);
        });
    }
}
