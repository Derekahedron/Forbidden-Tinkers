package derekahedron.forbiddentinkers.tinkers.modifiers;

import com.google.common.collect.ImmutableList;
import derekahedron.mythictinkers.tinkers.hooks.LivingDropsModifierHook;
import derekahedron.mythictinkers.tinkers.hooks.MTModifierHooks;
import derekahedron.mythictinkers.tinkers.hooks.PickupItemModifierHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.List;

public class WormholeModifier extends NoLevelsModifier implements ProcessLootModifierHook, LivingDropsModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.PROCESS_LOOT,
                MTModifierHooks.LIVING_DROPS);
    }

    @Override
    public int getPriority() {
        return -50; // Must be below Tesseracted
    }

    @Override
    public void processLoot(IToolStackView iToolStackView, ModifierEntry modifierEntry, List<ItemStack> list, LootContext lootContext) {
        Entity entity;
        if (lootContext.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            entity = lootContext.getParamOrNull(LootContextParams.KILLER_ENTITY);
        } else {
            entity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
        }

        if (entity instanceof Player player) {
            PickupItemModifierHook.pickup(ImmutableList.copyOf(list), player);
            list.removeIf(stack -> {
               player.getInventory().add(stack);
               return stack.isEmpty();
            });
        }
    }

    @Override
    public void onLivingDrops(
            IToolStackView tool, ModifierEntry modifier, LivingEntity attacker, LivingDropsEvent event) {
        if (attacker instanceof Player player) {
            PickupItemModifierHook.pickup(ImmutableList.copyOf(event.getDrops().stream().map(ItemEntity::getItem).toList()), player);
            event.getDrops().removeIf(itemEntity -> {
                ItemStack stack = itemEntity.getItem();
                player.getInventory().add(stack);
                return stack.isEmpty();
            });
        }
    }
}
