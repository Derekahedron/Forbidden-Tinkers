package derekahedron.forbiddentinkers.particle;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, ForbiddenTinkers.MOD_ID);


    public static final RegistryObject<SimpleParticleType> CHAMPIUM_FORGE_SMOKE =
            PARTICLE_TYPES.register("champium_forge_smoke", () ->
                    new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> BROKEN_CHAMPIUM_FORGE_SMOKE =
            PARTICLE_TYPES.register("broken_champium_forge_smoke", () ->
                    new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> DRIPPING_MOLTEN_CHAMPIUM =
            PARTICLE_TYPES.register("dripping_molten_champium", () ->
                    new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_MOLTEN_CHAMPIUM =
            PARTICLE_TYPES.register("falling_molten_champium", () ->
                    new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LANDING_MOLTEN_CHAMPIUM =
            PARTICLE_TYPES.register("landing_molten_champium", () ->
                    new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> MOLTEN_CHAMPIUM =
            PARTICLE_TYPES.register("molten_champium", () ->
                    new SimpleParticleType(false));
}
