package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

public class FTModifiers {
    public static final ModifierDeferredRegister MODIFIERS =
            ModifierDeferredRegister.create(ForbiddenTinkers.MOD_ID);

    public static final StaticModifier<FunnyModifier> FUNNY =
            MODIFIERS.register("funny", FunnyModifier::new);

    public static final StaticModifier<DisposingModifier> DISPOSING =
            MODIFIERS.register("disposing", DisposingModifier::new);

    public static final StaticModifier<HeartDroppingModifier> HEART_DROPPING =
            MODIFIERS.register("heart_dropping", HeartDroppingModifier::new);

    public static final StaticModifier<WormholeModifier> WORMHOLE =
            MODIFIERS.register("wormhole", WormholeModifier::new);

    public static final StaticModifier<FeastingModifier> FEASTING =
            MODIFIERS.register("feasting", FeastingModifier::new);

    public static final StaticModifier<DivineModifier> DIVINE =
            MODIFIERS.register("divine", DivineModifier::new);
}
