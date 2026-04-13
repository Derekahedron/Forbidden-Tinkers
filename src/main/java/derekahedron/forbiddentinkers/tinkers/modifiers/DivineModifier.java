package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.item.FTRarity;
import derekahedron.forbiddentinkers.tinkers.DivineSlotsManager;
import slimeknights.tconstruct.library.modifiers.*;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.modules.build.RarityModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;

public class DivineModifier extends NoLevelsModifier implements VolatileDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.VOLATILE_DATA);
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        RarityModule.setRarity(volatileData, FTRarity.CHAMPIUM);

        for (ModifierEntry entry : context.getUpgrades()) {
            for (SlotType.SlotCount slots : DivineSlotsManager.getAddedSlots(entry.getId(), entry.getLevel())) {
                volatileData.addSlots(slots.type(), slots.count());
            }
        }
    }
}
