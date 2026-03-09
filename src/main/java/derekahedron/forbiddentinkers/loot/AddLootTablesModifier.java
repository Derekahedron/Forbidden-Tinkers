package derekahedron.forbiddentinkers.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

import java.util.List;

public class AddLootTablesModifier extends LootModifier {
    public static final Codec<AddLootTablesModifier> CODEC = RecordCodecBuilder.create(inst ->
            codecStart(inst).and(
                    Entry.CODEC.listOf().fieldOf("loot_tables").forGetter(m -> m.lootTables)
            ).apply(inst, AddLootTablesModifier::new));

    public final List<Entry> lootTables;

    public AddLootTablesModifier(LootItemCondition[] conditionsIn, List<Entry> lootTables) {
        super(conditionsIn);
        this.lootTables = lootTables;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        for (Entry entry : lootTables) {
            if (entry.chance >= 1 || context.getRandom().nextFloat() < entry.chance) {
                LootTable table = context.getResolver().getLootTable(entry.lootTable);
                table.getRandomItemsRaw(context, generatedLoot::add);
            }
        }
        return generatedLoot;
    }

    @Override
    public Codec<AddLootTablesModifier> codec() {
        return FTLootModifiers.ADD_TABLE.get();
    }

    public record Entry(ResourceLocation lootTable, float chance) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(inst ->
                inst.group(
                        ResourceLocation.CODEC.fieldOf("loot_table").forGetter(Entry::lootTable),
                        Codec.FLOAT.optionalFieldOf("chance", 1.0F).forGetter(Entry::chance)
                ).apply(inst, Entry::new));
    }
}
