package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class FTMaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {

    @Override
    public String getName() {
        return String.format("%s Render Material Info", ForbiddenTinkers.MOD_NAME);
    }

    public FTMaterialRenderInfoProvider(PackOutput output, AbstractMaterialSpriteProvider spriteProvider, ExistingFileHelper existingFileHelper) {
        super(output, spriteProvider, existingFileHelper);
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(FTMaterialIds.CHAMPIUM)
                .color(0xFFFFDB79)
                .fallbacks("metal");
    }
}
