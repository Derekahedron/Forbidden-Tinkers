package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import derekahedron.mythictinkers.datagen.tinkers.MTModifierProvider;
import derekahedron.forbiddentinkers.tinkers.modifiers.modules.SwappableModifierMaterialModule;
import derekahedron.forbiddentinkers.tinkers.modifiers.modules.SwappableOverloadModifierModule;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;

public class FTModifierProvider extends MTModifierProvider {

    public FTModifierProvider(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return String.format("%s Modifiers", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addModifiers() {
        createBoon(FTModifierIds.CHAMPIUM_BOON, FTMaterialIds.CHAMPIUM);

        buildModifier(FTModifierIds.AUGMENTATION)
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
                .addModule(new SwappableModifierMaterialModule());

        buildModifier(FTModifierIds.OVERLOADED)
                .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
                .addModule(new SwappableOverloadModifierModule());
    }
}
