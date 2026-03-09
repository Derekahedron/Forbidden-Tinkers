package derekahedron.forbiddentinkers.sound;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTSoundEvents {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<SoundEvent> FUNNY =
            register("modifier.funny.funny");
    public static final RegistryObject<SoundEvent> FUNNIER =
            register("modifier.funny.funnier");
    public static final RegistryObject<SoundEvent> FUNNIEST =
            register("modifier.funny.funniest");

    public static final RegistryObject<SoundEvent> MOLTEN_CHAMPIUM_AMBIENT =
            register("block.molten_champium.ambient");
    public static final RegistryObject<SoundEvent> MOLTEN_CHAMPIUM_POP =
            register("block.molten_champium.pop");


    public static RegistryObject<SoundEvent> register(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(FTUtil.location(name)));
    }
}
