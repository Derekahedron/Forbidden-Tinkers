package derekahedron.forbiddentinkers.worldgen.structure.processor;

import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;

import javax.annotation.Nullable;

public class ChampiumForgeProcessor extends StructureProcessor {
    public static final ChampiumForgeProcessor INSTANCE = new ChampiumForgeProcessor();
    public static final Codec<ChampiumForgeProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @Override
    @Nullable
    public StructureTemplate.StructureBlockInfo process(
            LevelReader level,
            BlockPos relativePos, BlockPos pos,
            StructureTemplate.StructureBlockInfo relativeInfo, StructureTemplate.StructureBlockInfo info,
            StructurePlaceSettings settings,
            @Nullable StructureTemplate template) {

        if (info.state().getBlock() == FTBlocks.CHAMPIUM_FORGE.get()
                && info.nbt() != null
                && !info.nbt().contains(ChampiumForgeBlockEntity.RECIPE_KEY)) {
            info.nbt().putLong(ChampiumForgeBlockEntity.RECIPE_SEED_KEY, settings.getRandom(pos).nextLong());
        }

        return info;
    }

    @Override
    protected StructureProcessorType<ChampiumForgeProcessor> getType() {
        return FTStructureProcessorTypes.CHAMPIUM_FORGE.get();
    }
}
