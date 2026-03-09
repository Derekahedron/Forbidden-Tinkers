package derekahedron.forbiddentinkers.tinkers.modifiers.modules;

import derekahedron.mythictinkers.util.MTUtil;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.client.materials.MaterialTooltipCache;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
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

import javax.annotation.Nullable;
import java.util.List;

public record SwappableModifierMaterialModule(@Nullable ResourceLocation key) implements
        ModifierModule,
        ModifierTraitHook,
        DisplayNameModifierHook,
        ModifierRemovalHook,
        ModuleWithKey {
    public static final List<ModuleHook<?>> DEFAULT_HOOKS =
            HookProvider.defaultHooks(ModifierHooks.MODIFIER_TRAITS, ModifierHooks.DISPLAY_NAME, ModifierHooks.REMOVE);
    public static final RecordLoadable<SwappableModifierMaterialModule> LOADER =
            RecordLoadable.create(ModuleWithKey.FIELD, SwappableModifierMaterialModule::new);

    public SwappableModifierMaterialModule() {
        this(null);
    }

    @Override
    public void addTraits(IToolContext context, ModifierEntry self, ModifierTraitHook.TraitBuilder builder, boolean firstEncounter) {
        MaterialVariantId materialId = getMaterialId(context.getPersistentData(), self);
        if (materialId != null) {
            builder.add(MTUtil.getMaterialModifiers(materialId.getId(), context.getDefinition()));
        }
    }

    @Override
    public RecordLoadable<SwappableModifierMaterialModule> getLoader() {
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
        MaterialVariantId materialId = getMaterialId(tool.getPersistentData(), entry);

        if (materialId != null) {
            Component materialName = MaterialTooltipCache.getDisplayName(materialId);
            TextColor materialColor = MaterialTooltipCache.getColor(materialId);
            return Component.translatable(SwappableSlotModule.FORMAT, name.plainCopy(), materialName)
                    .withStyle(name.getStyle().withColor(materialColor));
        }

        return name;
    }

    @Nullable
    public MaterialVariantId getMaterialId(IModDataView persistentData, ModifierEntry modifierEntry) {
        return MaterialVariantId.tryParse(persistentData.getString(getKey(modifierEntry.getModifier())));
    }


    @Override
    @Nullable
    public ResourceLocation key() {
        return key;
    }
}
