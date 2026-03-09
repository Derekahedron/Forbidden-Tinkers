package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.mythictinkers.tinkers.hooks.LivingDropsModifierHook;
import derekahedron.mythictinkers.tinkers.hooks.MTModifierHooks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HeartDroppingModifier extends Modifier implements LivingDropsModifierHook {
    public static final float CHANCE_PER_LEVEL = 0.1F;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                MTModifierHooks.LIVING_DROPS);
    }

    @Override
    public int getPriority() {
        return -100; // Must be below Wormhole
    }

    @Override
    public void onLivingDrops(
            IToolStackView tool, ModifierEntry modifier, LivingEntity attacker, LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().random.nextFloat() < CHANCE_PER_LEVEL * modifier.getLevel()) {
            ItemEntity itemEntity = new ItemEntity(
                    entity.level(),
                    entity.getX(), entity.getY(), entity.getZ(),
                    new ItemStack(FTItems.HEART.get()));
            event.getDrops().add(itemEntity);
        }
    }
}
