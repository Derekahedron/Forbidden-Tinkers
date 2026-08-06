package derekahedron.forbiddentinkers.tinkers.modifiers;

import derekahedron.forbiddentinkers.sound.FTSoundEvents;
import derekahedron.mythictinkers.tinkers.hooks.MTModifierHooks;
import derekahedron.mythictinkers.tinkers.hooks.ShieldBlockModifierHook;
import derekahedron.mythictinkers.tinkers.hooks.ThrownToolHitModifierHook;
import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.core.BlockPos;
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
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
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
import slimeknights.tconstruct.tools.entity.ThrownTool;

import javax.annotation.Nullable;
import java.util.Objects;

public class FunnyModifier extends Modifier implements
        MeleeHitModifierHook,
        ModifyDamageModifierHook,
        RemoveBlockModifierHook,
        ProjectileHitModifierHook,
        ThrownToolHitModifierHook,
        ShieldBlockModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this,
                ModifierHooks.MELEE_HIT,
                ModifierHooks.MODIFY_DAMAGE,
                ModifierHooks.REMOVE_BLOCK,
                ModifierHooks.PROJECTILE_HIT,
                MTModifierHooks.THROWN_TOOL_HIT,
                MTModifierHooks.SHIELD_BLOCK);
    }

    @Override
    public float beforeMeleeHit(
            IToolStackView tool,
            ModifierEntry modifier,
            ToolAttackContext context,
            float damage,
            float baseKnockback,
            float knockback) {
        if (context.getProjectile() != null) {
            playHonkSound(context.getProjectile(), modifier);
        } else {
            playHonkSound(context.getAttacker(), modifier);
        }
        return knockback;
    }

    @Override
    public float modifyDamageTaken(
            IToolStackView tool,
            ModifierEntry modifier,
            EquipmentContext context,
            EquipmentSlot slotType,
            DamageSource source,
            float amount,
            boolean isDirectDamage) {
        if (slotType.isArmor()) {
            playHonkSound(context.getEntity(), modifier);
        }
        return amount;
    }

    @Override
    @Nullable
    public Boolean removeBlock(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        if (context.getProjectile() != null) {
            playHonkSound(context.getProjectile(), modifier);
        } else {
            playHonkSound(context.getLiving(), modifier);
        }
        return null;
    }

    @Override
    public boolean onProjectileHitEntity(
            ModifierNBT modifiers,
            ModDataNBT persistentData,
            ModifierEntry modifier,
            Projectile projectile,
            EntityHitResult hit,
            @Nullable LivingEntity attacker,
            @Nullable LivingEntity target,
            boolean notBlocked) {
        if (MTUtil.shouldBlockHitEffect(projectile, hit)) return false;
        playHonkSound(Objects.requireNonNullElse(target, projectile), modifier);
        return false;
    }

    @Override
    public boolean onProjectileHitsBlock(
            ModifierNBT modifiers,
            ModDataNBT persistentData,
            ModifierEntry modifier,
            Projectile projectile,
            BlockHitResult hit,
            @Nullable LivingEntity attacker) {
        playHonkSound(projectile, modifier);
        return false;
    }

    @Override
    public void onThrownToolHitEntity(
            IToolStackView tool,
            ModifierEntry modifier,
            ThrownTool thrownTool,
            LivingEntity attacker,
            Entity target,
            @Nullable LivingEntity livingTarget) {
        playHonkSound(thrownTool, modifier);
    }

    @Override
    public boolean onThrownToolHitsBlock(
            IToolStackView tool,
            ModifierEntry modifier,
            ThrownTool thrownTool,
            LivingEntity owner,
            BlockPos pos) {
        playHonkSound(thrownTool, modifier);
        return false;
    }

    @Override
    public void onShieldBlock(
            IToolStackView tool,
            ModifierEntry modifier,
            LivingEntity entity,
            DamageSource source,
            float damage) {
        playHonkSound(entity, modifier);
    }

    @Override
    public Component getDisplayName(int level) {
        return level <= 3 ?
                applyStyle(Component.translatable(getTranslationKey() + "." + level)) :
                super.getDisplayName(level);
    }

    public static void playHonkSound(Entity entity, ModifierEntry modifier) {
        playHonkSound(entity, modifier.getLevel());
    }

    public static void playHonkSound(Entity entity, int level) {
        if (level > 0) {
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
}
