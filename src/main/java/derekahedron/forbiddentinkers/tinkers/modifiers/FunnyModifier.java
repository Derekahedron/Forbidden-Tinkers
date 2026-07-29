package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.sound.FTSoundEvents;
import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.RemoveBlockModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;

import javax.annotation.Nullable;

public class FunnyModifier extends Modifier implements MeleeHitModifierHook, OnAttackedModifierHook, RemoveBlockModifierHook, ProjectileHitModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.MELEE_HIT,
                ModifierHooks.ON_ATTACKED,
                ModifierHooks.REMOVE_BLOCK,
                ModifierHooks.PROJECTILE_HIT);
    }

    @Override
    public float beforeMeleeHit(
            IToolStackView tool, ModifierEntry modifier, ToolAttackContext context,
            float damage, float baseKnockback, float knockback) {
        playHonkSound(context.getAttacker(), modifier);
        return knockback;
    }

    @Override
    public void onAttacked(
            IToolStackView tool, ModifierEntry modifier, EquipmentContext context,
            EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (slotType.isArmor() || MTUtil.isBlockingWithSlot(context.getEntity(), slotType)) {
            playHonkSound(context.getEntity(), modifier);
        }
    }

    @Override
    @Nullable
    public Boolean removeBlock(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        playHonkSound(context.getLiving(), modifier);
        return null;
    }

    @Override
    public boolean onProjectileHitEntity(
            ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier,
            Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker,
            @Nullable LivingEntity target) {
        playHonkSound(target, modifier);
        return false;
    }

    @Override
    public void onProjectileHitBlock(
            ModifierNBT modifiers, ModDataNBT persistentData, ModifierEntry modifier,
            Projectile projectile, BlockHitResult hit, @Nullable LivingEntity attacker) {
        playHonkSound(projectile, modifier);
    }

    public static void playHonkSound(@Nullable Entity entity, ModifierEntry modifier) {
        playHonkSound(entity, modifier.getLevel());
    }

    public static void playHonkSound(@Nullable Entity entity, int level) {
        if (entity != null && level > 0) {
            SoundEvent honkSound = switch (level) {
                case 1 -> FTSoundEvents.FUNNY.get();
                case 2 -> FTSoundEvents.FUNNIER.get();
                default -> FTSoundEvents.FUNNIEST.get();
            };
            SoundSource source = entity instanceof Player
                    ? SoundSource.PLAYERS
                    : SoundSource.HOSTILE;
            entity.level().playSound(
                    null,
                    entity.getX(), entity.getY(), entity.getZ(),
                    honkSound, source, 1.0F, 1.0F);
        }
    }

    @Override
    public Component getDisplayName(int level) {
        return level <= 3 ?
                applyStyle(Component.translatable(getTranslationKey() + "." + level)) :
                super.getDisplayName(level);
    }
}
