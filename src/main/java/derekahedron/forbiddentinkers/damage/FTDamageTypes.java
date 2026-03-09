package derekahedron.forbiddentinkers.damage;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class FTDamageTypes {

    public static final ResourceKey<DamageType> MOLTEN_CHAMPIUM =
            of("molten_champium");

    public static ResourceKey<DamageType> of(String id) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, FTUtil.location(id));
    }

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(MOLTEN_CHAMPIUM, new DamageType(
                String.format("%s.molten_champium", ForbiddenTinkers.MOD_ID),
                DamageScaling.NEVER,
                0.1F,
                DamageEffects.HURT));
    }
}
