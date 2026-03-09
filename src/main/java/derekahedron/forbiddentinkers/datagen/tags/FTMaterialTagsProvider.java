package derekahedron.forbiddentinkers.datagen.tags;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialTags;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractMaterialTagProvider;

public class FTMaterialTagsProvider extends AbstractMaterialTagProvider {

    public FTMaterialTagsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    public String getName() {
        return String.format("%s Material Tags", ForbiddenTinkers.MOD_ID);
    }

    protected void addTags() {
        tag(TinkerTags.Materials.EXCLUDE_FROM_LOOT)
                .add(FTMaterialIds.CHAMPIUM);

        tag(FTMaterialTags.CHAMPIUM_TIER)
                .add(FTMaterialIds.CHAMPIUM);
    }
}
