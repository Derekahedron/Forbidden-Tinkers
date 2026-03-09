package derekahedron.forbiddentinkers.worldgen.structure;

import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.worldgen.structure.processor.ChampiumForgeProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class ChampiumForgeStructurePiece extends TemplateStructurePiece {

    public ChampiumForgeStructurePiece(
            StructureTemplateManager manager, ResourceLocation resourceLocation,
            BlockPos pos) {
        super(
                FTStructurePieceTypes.CHAMPIUM_FORGE.get(),
                0,
                manager,
                resourceLocation,
                resourceLocation.toString(),
                makeSettings(), pos);
    }

    public ChampiumForgeStructurePiece(
            StructureTemplateManager manager, CompoundTag tag) {
        super(
                FTStructurePieceTypes.CHAMPIUM_FORGE.get(),
                tag,
                manager,
                (location) -> makeSettings());
    }

    public static ChampiumForgeStructurePiece create(StructurePieceSerializationContext context, CompoundTag tag) {
        return new ChampiumForgeStructurePiece(context.structureTemplateManager(), tag);
    }

    private static StructurePlaceSettings makeSettings() {
        return new StructurePlaceSettings()
                .addProcessor(new ChampiumForgeProcessor())
                .setKeepLiquids(true);
    }

    @Override
    public void postProcess(
            WorldGenLevel level, StructureManager structureManager,
            ChunkGenerator chunkGenerator, RandomSource random, BoundingBox boundingBox,
            ChunkPos chunkPos, BlockPos pos) {
        super.postProcess(level, structureManager, chunkGenerator, random, boundingBox, chunkPos, pos);
        fillBedrockBricks(level);
    }

    @Override
    protected void handleDataMarker(
            String dataMarker, BlockPos pos, ServerLevelAccessor level,
            RandomSource random, BoundingBox boundingBox) {
        level.setBlock(pos, Blocks.CAVE_AIR.defaultBlockState(), 0);
    }

    public void fillBedrockBricks(WorldGenLevel level) {
        BlockPos cornerPosition = StructureTemplate.transform(
                new BlockPos(
                        templatePosition.getX() + template.getSize().getX() - 1,
                        templatePosition.getY(),
                        templatePosition.getZ() + template.getSize().getZ() - 1),
                Mirror.NONE,
                placeSettings.getRotation(),
                templatePosition
        );

        for (BlockPos pos : BlockPos.betweenClosed(templatePosition, cornerPosition)) {
            BlockState state = level.getBlockState(pos);
            if (level.getBlockState(pos).getBlock() == FTBlocks.BEDROCK_BRICKS.get()) {
                BlockPos.MutableBlockPos mutablePos = pos.mutable().move(Direction.DOWN);
                while (mutablePos.getY() > level.getMinBuildHeight()) {
                    level.setBlock(mutablePos, state, 2);
                    mutablePos.move(Direction.DOWN);
                }
            }
        }
    }
}
