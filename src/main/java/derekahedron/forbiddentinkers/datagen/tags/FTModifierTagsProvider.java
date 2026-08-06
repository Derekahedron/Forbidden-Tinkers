package derekahedron.forbiddentinkers.datagen.tags;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import derekahedron.mythictinkers.tinkers.modifiers.MTModifierTags;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

public class FTModifierTagsProvider extends AbstractModifierTagProvider {

    public FTModifierTagsProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    @Override
    public String getName() {
        return String.format("%s Modifier Tags", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addTags() {
        tag(TinkerTags.Modifiers.BONUS_SLOTLESS)
                .add(FTModifierIds.OVERLOADED)
                .add(FTModifierIds.AUGMENTATION);

        tag(TinkerTags.Modifiers.COSMETIC_SLOTLESS)
                .add(FTModifierIds.FUNNY);

        tag(TinkerTags.Modifiers.EXTRACT_MODIFIER_BLACKLIST)
                .add(FTModifierIds.OVERLOADED)
                .add(FTModifierIds.AUGMENTATION);

        tag(TinkerTags.Modifiers.GENERAL_ABILITIES)
                .add(FTModifierIds.WORMHOLE);

        tag(TinkerTags.Modifiers.GENERAL_UPGRADES)
                .add(FTModifierIds.DISPOSING);

        tag(MTModifierTags.BOONS)
                .add(FTModifierIds.CHAMPIUM_BOON);
    }
}
