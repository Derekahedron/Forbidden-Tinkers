package derekahedron.forbiddentinkers.worldgen.structure;

import derekahedron.forbiddentinkers.util.FTUtil;
import com.mojang.serialization.Codec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import javax.annotation.Nullable;
import java.util.Optional;

public class ChampiumForgeStructure extends Structure {
    public static final Codec<ChampiumForgeStructure> CODEC = simpleCodec(ChampiumForgeStructure::new);
    private static final ResourceLocation[] RUINS_NBT = new ResourceLocation[] {
            FTUtil.location("champium_forge_1"),
            FTUtil.location("champium_forge_2"),
            FTUtil.location("champium_forge_3"),
            FTUtil.location("champium_forge_4")};
    public static final int MAX_Y_OFFSET = 48;
    public static final int MAX_TRIES = 4;

    public ChampiumForgeStructure(StructureSettings settings) {
        super(settings);
    }

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        // Try to find a location 4 times before giving up
        BlockPos pos = null;
        for (int i = 0; i < MAX_TRIES; i++) {
            pos = findLocation(context);
            if (pos != null) break;
        }
        if (pos == null) return Optional.empty();


        ResourceLocation structureLocation = Util.getRandom(RUINS_NBT, context.random());
        Vec3i templateSize = context.structureTemplateManager().get(structureLocation)
                .map(StructureTemplate::getSize)
                .orElse(Vec3i.ZERO);

        final BlockPos templatePosition = new BlockPos(
                pos.getX() - templateSize.getX() / 2,
                pos.getY(),
                pos.getZ() - templateSize.getZ() / 2);

        return Optional.of(new GenerationStub(templatePosition, (piecesBuilder) ->
                piecesBuilder.addPiece(new ChampiumForgeStructurePiece(
                        context.structureTemplateManager(),
                        structureLocation,
                        templatePosition
                ))));
    }

    @Override
    public StructureType<ChampiumForgeStructure> type() {
        return FTStructureTypes.CHAMPIUM_FORGE.get();
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    @Nullable
    public static BlockPos findLocation(GenerationContext context) {
        int x = context.chunkPos().getBlockX(context.random().nextInt(16));
        int y = context.chunkGenerator().getMinY();
        int z = context.chunkPos().getBlockZ(context.random().nextInt(16));
        int maxY = y + MAX_Y_OFFSET;
        NoiseColumn col = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());

        while (y < maxY) {
            if (col.getBlock(y).getBlock() == Blocks.AIR) {
                return new BlockPos(x, y, z);
            }
            y++;
        }
        return null;
    }
}
