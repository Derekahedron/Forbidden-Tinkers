package derekahedron.forbiddentinkers.loot;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<Codec<AddLootTablesModifier>> ADD_TABLE =
            LOOT_MODIFIER_SERIALIZERS.register("add_loot_tables", () -> AddLootTablesModifier.CODEC);
}
