package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.util.FTUtil;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class FTModifierIds {

    public static final ModifierId FUNNY =
            FTModifiers.FUNNY.getId();

    public static final ModifierId DISPOSING =
            FTModifiers.DISPOSING.getId();

    public static final ModifierId HEART_DROPPING =
            FTModifiers.HEART_DROPPING.getId();

    public static final ModifierId WORMHOLE =
            FTModifiers.WORMHOLE.getId();

    public static final ModifierId FEASTING =
            FTModifiers.FEASTING.getId();

    public static final ModifierId DIVINE =
            FTModifiers.DIVINE.getId();

    public static final ModifierId CHAMPIUM_BOON =
            new ModifierId(FTUtil.location("champium_boon"));

    public static final ModifierId AUGMENTATION =
            new ModifierId(FTUtil.location("augmentation"));

    public static final ModifierId OVERLOADED =
            new ModifierId(FTUtil.location("overloaded"));
}
