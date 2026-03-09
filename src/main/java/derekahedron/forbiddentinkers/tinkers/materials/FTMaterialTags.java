package derekahedron.forbiddentinkers.tinkers.materials;

import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.tags.TagKey;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.materials.definition.MaterialManager;

public class FTMaterialTags {

    public static final TagKey<IMaterial> CHAMPIUM_TIER =
            MaterialManager.getTag(FTUtil.location("champium_tier"));
}
