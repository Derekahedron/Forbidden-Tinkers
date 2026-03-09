package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.item.FTItemTags;
import derekahedron.mythictinkers.tinkers.hooks.LivingDropsModifierHook;
import derekahedron.mythictinkers.tinkers.hooks.MTModifierHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class DisposingModifier extends NoLevelsModifier implements ProcessLootModifierHook, LivingDropsModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.PROCESS_LOOT,
                MTModifierHooks.LIVING_DROPS);
    }

    @Override
    public int getPriority() {
        return 200;
    }

    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
        list.removeIf(stack -> stack.is(FTItemTags.DISPOSABLE));
    }

    @Override
    public void onLivingDrops(
            IToolStackView tool, ModifierEntry modifier, LivingEntity attacker, LivingDropsEvent event) {
        if (event.getEntity() instanceof Player) return;
        event.getDrops().removeIf(itemEntity -> itemEntity.getItem().is(FTItemTags.DISPOSABLE));
    }
}
