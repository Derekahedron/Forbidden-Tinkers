package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.tinkers.hooks.PickupXpModifierHook;
import derekahedron.forbiddentinkers.tinkers.hooks.FTModifierHooks;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class FeastingModifier extends NoLevelsModifier implements PickupXpModifierHook {
    public static final int EXP_PER_LEVEL = 10;
    public static final float ADDITIONAL_SATURATION = 2.0F;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                FTModifierHooks.PICKUP_XP);
    }

    @Override
    public void onPickupXp(
            IToolStackView tool, ModifierEntry modifier, Player player, EquipmentSlot slot, ExperienceOrb orb) {
        if (orb.value <= 0) return;
        FoodData foodStats = player.getFoodData();
        int foodLevel = foodStats.getFoodLevel();
        if (foodLevel >= 20) {
            if (orb.value > 0 && foodStats.getSaturationLevel() <= 0.0F) {
                foodStats.setSaturation(0.1F);
                orb.value--;
            }
            return;
        }

        int expUsed = Math.min(orb.value, (20 - foodLevel) * EXP_PER_LEVEL);
        foodLevel += expUsed / EXP_PER_LEVEL;
        int expRemaining = expUsed % EXP_PER_LEVEL;
        if (expRemaining >= 0 && player.level().random.nextFloat() < ((float) expRemaining) / EXP_PER_LEVEL) {
            foodLevel++;
        }
        if (foodLevel >= 20 && foodStats.getSaturationLevel() < ADDITIONAL_SATURATION) {
            foodStats.setSaturation(ADDITIONAL_SATURATION);
        }
        foodStats.setFoodLevel(foodLevel);

        orb.value -= expUsed;
    }
}
