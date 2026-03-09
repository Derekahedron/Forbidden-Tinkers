package derekahedron.forbiddentinkers.tinkers.hooks;

import derekahedron.forbiddentinkers.util.FTUtil;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;

public class FTModifierHooks {

    public static final ModuleHook<PickupXpModifierHook> PICKUP_XP =
            ModifierHooks.register(
                    FTUtil.location("pickup_xp"),
                    PickupXpModifierHook.class,
                    PickupXpModifierHook.AllMerger::new,
                    (tool, modifier, player, slot, orb) -> {});
}
