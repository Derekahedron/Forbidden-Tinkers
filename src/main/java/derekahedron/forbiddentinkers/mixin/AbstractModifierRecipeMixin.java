package derekahedron.forbiddentinkers.mixin;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.DivineSlotsManager;
import derekahedron.forbiddentinkers.tinkers.modifiers.DivineModifier;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slimeknights.tconstruct.library.modifiers.ModifierManager;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.AbstractModifierRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import javax.annotation.Nullable;

@Mixin(AbstractModifierRecipe.class)
public abstract class AbstractModifierRecipeMixin {

    @Shadow(remap = false) @Final protected LazyModifier result;

    @Shadow(remap = false) @Final @Nullable private SlotType.SlotCount slots;

    @Shadow(remap = false)
    @Nullable
    protected static Component checkSlots(IToolStackView tool, @Nullable SlotType.SlotCount slots) {
        return null;
    }

    @Inject(
            method = "validatePrerequisites(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;I)Lnet/minecraft/network/chat/Component;",
            at = @At(
                    value = "INVOKE",
                    target = "Lslimeknights/tconstruct/library/recipe/modifiers/adding/AbstractModifierRecipe;checkSlots(Lslimeknights/tconstruct/library/tools/nbt/IToolStackView;Lslimeknights/tconstruct/library/tools/SlotType$SlotCount;)Lnet/minecraft/network/chat/Component;"
            ),
            cancellable = true,
            remap = false)
    private void champiumSlots(IToolStackView tool, int resultLevel, CallbackInfoReturnable<Component> cir) {
        try {
            if (slots != null
                    && slots.count() > 0
                    && tool.getModifierLevel(FTModifierIds.DIVINE) > 0) {
                int returnedSlots = DivineSlotsManager.getSlotsToBeAdded2(result.getId(), slots.type(), resultLevel);

                if (returnedSlots > 0) {
                    cir.setReturnValue(checkSlots(tool, new SlotType.SlotCount(slots.type(), slots.count() - returnedSlots)));
                }
            }
        } catch (Exception e) {
            ForbiddenTinkers.LOGGER.error("Error while mixing in to AbstractModifierRecipe. This is a Forbidden Tinkers error, not a Tinkers Construct Error");
            e.printStackTrace();
        }
    }
}
