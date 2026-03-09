package derekahedron.forbiddentinkers.datagen.loot;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public class FTLootTableProvider extends LootTableProvider {

    public FTLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(FTBlockLoot::new, LootContextParamSets.BLOCK)));
    }
}
