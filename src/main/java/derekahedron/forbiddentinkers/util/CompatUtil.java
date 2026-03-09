package derekahedron.forbiddentinkers.util;

import net.minecraft.resources.ResourceLocation;

public class CompatUtil {

    public static ResourceLocation forge(String id) {
        return new ResourceLocation("forge", id);
    }

    public static ResourceLocation blueSkiesTcon(String id) {
        return new ResourceLocation("blue_skies_tcon", id);
    }

    public static ResourceLocation create(String id) {
        return new ResourceLocation("create", id);
    }

    public static ResourceLocation enderIO(String id) {
        return new ResourceLocation("enderio", id);
    }

    public static ResourceLocation thermalConstruct(String id) {
        return new ResourceLocation("thermalconstruct", id);
    }

    public static ResourceLocation tinkersThings(String id) {
        return new ResourceLocation("tinkers_things", id);
    }
}
