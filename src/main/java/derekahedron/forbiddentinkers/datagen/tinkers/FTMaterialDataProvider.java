package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

public class FTMaterialDataProvider extends AbstractMaterialDataProvider {

    public FTMaterialDataProvider(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return String.format("%s Materials", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addMaterials() {
        addMaterial(FTMaterialIds.CHAMPIUM,
                5,
                1000,
                false);
    }
}
