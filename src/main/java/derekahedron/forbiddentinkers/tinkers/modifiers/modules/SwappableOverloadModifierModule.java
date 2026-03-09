package derekahedron.forbiddentinkers.tinkers.modifiers.modules;

import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.client.ResourceColorManager;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierTraitHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DisplayNameModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.build.SwappableSlotModule;
import slimeknights.tconstruct.library.modifiers.util.ModuleWithKey;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IModDataView;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.Util;

import javax.annotation.Nullable;
import java.util.List;

public record SwappableOverloadModifierModule(@Nullable ResourceLocation key) implements
        ModifierModule,
        ModifierTraitHook,
        DisplayNameModifierHook,
        ModifierRemovalHook,
        ModuleWithKey {
    public static final List<ModuleHook<?>> DEFAULT_HOOKS =
            HookProvider.defaultHooks(ModifierHooks.MODIFIER_TRAITS, ModifierHooks.DISPLAY_NAME, ModifierHooks.REMOVE);
    public static final RecordLoadable<SwappableOverloadModifierModule> LOADER =
            RecordLoadable.create(ModuleWithKey.FIELD, SwappableOverloadModifierModule::new);

    public SwappableOverloadModifierModule() {
        this(null);
    }

    @Override
    public void addTraits(IToolContext context, ModifierEntry self, ModifierTraitHook.TraitBuilder builder, boolean firstEncounter) {
        ModifierId modifierId = getModifierId(context.getPersistentData(), self);
        for (ModifierEntry upgrade : context.getUpgrades().getModifiers()) {
            if (upgrade.getId().equals(modifierId)) {
                builder.add(upgrade);
            }
        }
    }

    @Override
    public RecordLoadable<SwappableOverloadModifierModule> getLoader() {
        return LOADER;
    }

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    @Override
    @Nullable
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        tool.getPersistentData().remove(getKey(modifier));
        return null;
    }

    @Override
    public Component getDisplayName(IToolStackView tool, ModifierEntry entry, Component name, @Nullable RegistryAccess access) {
        ModifierId modifierId = getModifierId(tool.getPersistentData(), entry);

        if (modifierId != null) {
            String translationKey = Util.makeTranslationKey("modifier", modifierId);

            Component modifierName = Component.translatable(translationKey);
            TextColor modifierColor = ResourceColorManager.getTextColor(translationKey);
            return Component.translatable(SwappableSlotModule.FORMAT, name.plainCopy(), modifierName)
                    .withStyle(name.getStyle().withColor(modifierColor));
        }

        return name;
    }

    @Nullable
    public ModifierId getModifierId(IModDataView persistentData, ModifierEntry modifierEntry) {
        return ModifierId.tryParse(persistentData.getString(getKey(modifierEntry.getModifier())));
    }


    @Override
    @Nullable
    public ResourceLocation key() {
        return key;
    }
}
