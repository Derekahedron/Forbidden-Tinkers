package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;

public class FTMaterialTraitDataProvider extends AbstractMaterialTraitDataProvider {

    public FTMaterialTraitDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
    }

    @Override
    public String getName() {
        return String.format("%s Material Traits", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addMaterialTraits() {
        addDefaultTraits(FTMaterialIds.CHAMPIUM,
                FTModifierIds.DIVINE);
    }
}
