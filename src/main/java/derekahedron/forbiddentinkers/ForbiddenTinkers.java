package derekahedron.forbiddentinkers;

import derekahedron.forbiddentinkers.block.FTBlocks;
import derekahedron.forbiddentinkers.block.dispenser.FTDispenserBehavior;
import derekahedron.forbiddentinkers.block.entity.FTBlockEntityTypes;
import derekahedron.forbiddentinkers.fluid.FTFluidInteractions;
import derekahedron.forbiddentinkers.fluid.FTFluidTypes;
import derekahedron.forbiddentinkers.fluid.FTFluids;
import derekahedron.forbiddentinkers.inventory.FTMenuTypes;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.item.FTCreativeTabs;
import derekahedron.forbiddentinkers.item.FTTiers;
import derekahedron.forbiddentinkers.loot.FTLootModifiers;
import derekahedron.forbiddentinkers.network.FTPacketHandler;
import derekahedron.forbiddentinkers.particle.FTParticleTypes;
import derekahedron.forbiddentinkers.recipe.FTRecipeSerializers;
import derekahedron.forbiddentinkers.recipe.FTRecipeTypes;
import derekahedron.forbiddentinkers.registry.FTRegistryKeys;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifiers;
import derekahedron.forbiddentinkers.sound.FTSoundEvents;
import derekahedron.forbiddentinkers.worldgen.structure.FTStructurePieceTypes;
import derekahedron.forbiddentinkers.worldgen.structure.FTStructureTypes;
import derekahedron.forbiddentinkers.worldgen.structure.processor.FTStructureProcessorTypes;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(ForbiddenTinkers.MOD_ID)
public class ForbiddenTinkers {
    public static final String MOD_ID = "forbiddentinkers";
    public static final String MOD_NAME = "Forbidden Tinkers";
    public static final Logger LOGGER = LogUtils.getLogger();

    @SuppressWarnings("forremoval")
    public ForbiddenTinkers() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FTBlocks.BLOCKS.register(modEventBus);
        FTItems.ITEMS.register(modEventBus);
        FTBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
        FTFluidTypes.FLUID_TYPES.register(modEventBus);
        FTFluids.FLUIDS.register(modEventBus);
        FTCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
        FTModifiers.MODIFIERS.register(modEventBus);
        FTParticleTypes.PARTICLE_TYPES.register(modEventBus);
        FTRecipeTypes.RECIPE_TYPES.register(modEventBus);
        FTRecipeSerializers.RECIPE_SERIALIZERS.register(modEventBus);
        FTSoundEvents.SOUND_EVENTS.register(modEventBus);
        FTMenuTypes.MENU_TYPES.register(modEventBus);
        FTStructureTypes.STRUCTURE_TYPES.register(modEventBus);
        FTStructurePieceTypes.STRUCTURE_PIECE_TYPES.register(modEventBus);
        FTStructureProcessorTypes.STRUCTURE_PROCESSOR_TYPES.register(modEventBus);
        FTLootModifiers.LOOT_MODIFIER_SERIALIZERS.register(modEventBus);

        modEventBus.addListener(FTFluidInteractions::initialize);
        modEventBus.addListener(FTPacketHandler::initialize);
        modEventBus.addListener(FTDispenserBehavior::initialize);
        modEventBus.addListener(FTTiers::initialize);
        modEventBus.addListener(FTRegistryKeys::initialize);

        MinecraftForge.EVENT_BUS.register(this);
    }
}
