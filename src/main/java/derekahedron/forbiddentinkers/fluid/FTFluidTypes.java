package derekahedron.forbiddentinkers.fluid;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.damage.FTDamageTypes;
import derekahedron.forbiddentinkers.item.FTRarity;
import derekahedron.forbiddentinkers.util.FTUtil;
import derekahedron.mythictinkers.fluid.MoltenOreFluidType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.awt.*;

public class FTFluidTypes {
    public static final DeferredRegister<FluidType> FLUID_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, ForbiddenTinkers.MOD_ID);

    // Molten Champium
    public static final RegistryObject<FluidType> MOLTEN_CHAMPIUM =
            FLUID_TYPES.register("molten_champium", () ->
                    new MoltenOreFluidType(
                            FTUtil.location("block/molten_champium_still"),
                            FTUtil.location("block/molten_champium_flow"),
                            new Color(0xFFEB89),
                            0.0F,
                            1.0F,
                            FTDamageTypes.MOLTEN_CHAMPIUM,
                            6.0F,
                            20,
                            FluidType.Properties.create()
                                    .motionScale(0.0023D)
                                    .canSwim(false)
                                    .canDrown(false)
                                    .pathType(BlockPathTypes.LAVA)
                                    .adjacentPathType(BlockPathTypes.DANGER_OTHER)
                                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                                    .lightLevel(15)
                                    .density(1024)
                                    .temperature(2300)
                                    .viscosity(1024)
                                    .rarity(FTRarity.CHAMPIUM)));
}
