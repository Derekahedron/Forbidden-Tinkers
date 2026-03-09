package derekahedron.forbiddentinkers.tinkers.modifiers.modules;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegisterEvent;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FTModifierModules {

    @SubscribeEvent
    static void registerSerializers(RegisterEvent event) {
        if (event.getRegistryKey() == Registries.RECIPE_SERIALIZER) {
            ModifierModule.LOADER.register(FTUtil.location("swappable_modifier_material"), SwappableModifierMaterialModule.LOADER);
            ModifierModule.LOADER.register(FTUtil.location("swappable_overload_modifier"), SwappableOverloadModifierModule.LOADER);
        }
    }
}
