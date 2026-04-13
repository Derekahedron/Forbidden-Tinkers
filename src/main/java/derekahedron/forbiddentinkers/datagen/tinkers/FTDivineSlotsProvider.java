package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.registry.FTRegistryKeys;
import derekahedron.forbiddentinkers.tinkers.DivineSlots;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import derekahedron.forbiddentinkers.util.CompatUtil;
import derekahedron.forbiddentinkers.util.FTUtil;
import derekahedron.mythictinkers.tinkers.modifiers.MTModifierIds;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

public class FTDivineSlotsProvider {
    public static void bootstrap(BootstapContext<DivineSlots> context) {
        registerUpgrade(context, ModifierIds.experienced, 1, 5);
        registerUpgrade(context, TinkerModifiers.magnetic.getId(), 1, 5);
        registerUpgrade(context, ModifierIds.overforced, 1, 5);
        registerUpgrade(context, ModifierIds.reinforced, 1, 5);
        registerUpgrade(context, ModifierIds.smelting, 1, 4);
        registerUpgrade(context, MTModifierIds.TECTONIC_SHIELD, 1, 4);
        registerUpgrade(context, ModifierIds.blockade, 1, 3);
        registerUpgrade(context, ModifierIds.fiery, 1, 5);
        registerUpgrade(context, ModifierIds.freezing, 1, 3);
        registerUpgrade(context, FTModifierIds.HEART_DROPPING, 1, 3);
        registerUpgrade(context, ModifierIds.lure, 1, 5);
        registerUpgrade(context, TinkerModifiers.knockback.getId(), 1, 3);
        registerUpgrade(context, TinkerModifiers.necrotic.getId(), 1, 5);
        registerUpgrade(context, TinkerModifiers.padded.getId(), 1, 3);
        registerUpgrade(context, TinkerModifiers.severing.getId(), 1, 3);
        registerUpgrade(context, TinkerModifiers.sweeping.getId(), 1, 3);
        registerUpgrade(context, ModifierIds.antiaquatic, 1, 5);
        registerUpgrade(context, ModifierIds.baneOfSssss, 1, 5);
        registerUpgrade(context, ModifierIds.cooling, 1, 5);
        registerUpgrade(context, ModifierIds.killager, 1, 5);
        registerUpgrade(context, ModifierIds.pierce, 1, 3);
        registerUpgrade(context, ModifierIds.sharpness, 1, 5);
        registerUpgrade(context, ModifierIds.smite, 1, 5);
        registerUpgrade(context, ModifierIds.swiftstrike, 1, 5);
        registerUpgrade(context, ModifierIds.blasting, 1, 5);
        registerUpgrade(context, ModifierIds.haste, 1, 5);
        registerUpgrade(context, ModifierIds.hydraulic, 1, 5);
        registerUpgrade(context, ModifierIds.lightspeed, 1, 3);
        registerUpgrade(context, ModifierIds.arrowPierce, 1, 4);
        registerUpgrade(context, ModifierIds.bounce, 1, 3);
        registerUpgrade(context, new ModifierId(CompatUtil.tinkersThings("blinding")), 1, 5);
        registerUpgrade(context, ModifierIds.punch, 1, 3);
        registerUpgrade(context, ModifierIds.power, 1, 5);
        registerUpgrade(context, ModifierIds.quickCharge, 1, 4);
        registerUpgrade(context, ModifierIds.trueshot, 1, 3);
        registerUpgrade(context, ModifierIds.ricochet, 1, 2);
        registerUpgrade(context, ModifierIds.springy, 1, 3);
        registerUpgrade(context, ModifierIds.thorns, 1, 3);
        registerUpgrade(context, ModifierIds.respiration, 1, 3);
        registerUpgrade(context, TinkerModifiers.sleeves.getId(), 1, 3);
        registerUpgrade(context, ModifierIds.speedy, 1, 3);
        registerUpgrade(context, ModifierIds.stepUp, 1, 2);
        registerUpgrade(context, ModifierIds.swiftSneak, 1, 5);
        registerUpgrade(context, ModifierIds.depthStrider, 1, 3);
        registerUpgrade(context, ModifierIds.featherFalling, 1, 2);
        registerUpgrade(context, TinkerModifiers.soulspeed.getId(), 1, 3);
        registerUpgrade(context, ModifierIds.returning, 1, 4);
        registerUpgrade(context, new ModifierId(CompatUtil.constructsCasting("expedient")), 1, 3);
        registerUpgrade(context, new ModifierId(CompatUtil.constructsCasting("swiftcasting")), 1, 3);
        registerUpgrade(context, new ModifierId(CompatUtil.constructsCasting("slot_improvement")), 1, 6);

        registerAbility(context, ModifierIds.reach, 1, 2);
        registerAbility(context, ModifierIds.strength, 1, 2);
        registerAbility(context, ModifierIds.pockets, 1, 2);
        registerAbility(context, ModifierIds.doubleJump, 1, 2);
        registerAbility(context, ModifierIds.boundless, 1, 5);
        registerAbility(context, ModifierIds.leaping, 1, 1);

        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("ice_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("fire_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("lightning_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("ender_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("holy_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("evocation_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("blood_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("nature_upgrade")));
        registerElementalMagic(context, new ModifierId(CompatUtil.constructsCasting("mana_upgrade")));
    }

    public static void register(
            BootstapContext<DivineSlots> context,
            ModifierId modifierId,
            int minLevel,
            int maxLevel,
            SlotType.SlotCount slots) {
        context.register(
                ResourceKey.create(FTRegistryKeys.DIVINE_SLOTS, FTUtil.location(modifierId.getPath())),
                new DivineSlots(modifierId, minLevel, maxLevel, slots));
    }

    public static void registerUpgrade(
            BootstapContext<DivineSlots> context,
            ModifierId modifierId,
            int minLevel,
            int maxLevel) {
        register(context, modifierId, minLevel, maxLevel, new SlotType.SlotCount(SlotType.UPGRADE, 1));
    }

    public static void registerAbility(
            BootstapContext<DivineSlots> context,
            ModifierId modifierId,
            int minLevel,
            int maxLevel) {
        register(context, modifierId, minLevel, maxLevel, new SlotType.SlotCount(SlotType.ABILITY, 1));
    }

    public static void registerElementalMagic(
            BootstapContext<DivineSlots> context,
            ModifierId modifierId) {
        registerUpgrade(context, modifierId, 1, 3);
        context.register(
                ResourceKey.create(FTRegistryKeys.DIVINE_SLOTS, FTUtil.location(modifierId.getPath() + "_affinity")),
                new DivineSlots(modifierId, 4, 5, new SlotType.SlotCount(SlotType.getOrCreate("affinity"), 1)));
    }
}
