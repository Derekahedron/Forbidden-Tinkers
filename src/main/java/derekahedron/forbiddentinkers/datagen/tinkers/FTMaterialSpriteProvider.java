package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;

public class FTMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

    @Override
    public String getName() {
        return String.format("%s Material Sprites", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addAllMaterials() {
        buildMaterial(FTMaterialIds.CHAMPIUM)
                .meleeHarvest()
                .ranged()
                .armor()
                .fallbacks("metal")
                .colorMapper(GreyToColorMapping.builderFromBlack()
                        .addARGB(0x3F, 0xFF906B18)
                        .addARGB(0x66, 0xFFD8A327)
                        .addARGB(0x8C, 0xFFFAC007)
                        .addARGB(0xB2, 0xFFFFDE86)
                        .addARGB(0xD8, 0xFFFFF6D7)
                        .addARGB(0xFF, 0xFFEFFFFB)
                        .build());
    }
}
