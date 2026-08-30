package derekahedron.forbiddentinkers.network;

import derekahedron.forbiddentinkers.client.network.ChampiumForgeMenuIngredientsPacketHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

import java.util.Optional;
import java.util.function.Supplier;

public record ChampiumForgeMenuIngredientsPacket(
        ResourceKey<Level> dimension,
        BlockPos blockPos,
        Optional<NonNullList<Ingredient>> ingredients) {

    public ChampiumForgeMenuIngredientsPacket(FriendlyByteBuf buffer) {
        this(
                buffer.readResourceKey(Registries.DIMENSION),
                buffer.readBlockPos(),
                buffer.readOptional(buf ->
                        buf.readCollection(
                                NonNullList::createWithCapacity,
                                Ingredient::fromNetwork)));
    }

    public void toBytes(FriendlyByteBuf buffer) {
        buffer.writeResourceKey(dimension);
        buffer.writeBlockPos(blockPos);
        buffer.writeOptional(ingredients, (buf, x) ->
                buf.writeCollection(
                        x,
                        (b, ingredient) -> ingredient.toNetwork(b)));
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() ->
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                        ChampiumForgeMenuIngredientsPacketHandler.handlePacket(this)));
        context.get().setPacketHandled(true);
    }
}
