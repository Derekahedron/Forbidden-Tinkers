package derekahedron.forbiddentinkers.datagen.advancements;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import derekahedron.forbiddentinkers.util.FTUtil;
import derekahedron.mythictinkers.datagen.advancements.MTAdvancements;
import net.minecraft.Util;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import slimeknights.tconstruct.library.json.predicate.tool.HasMaterialPredicate;
import slimeknights.tconstruct.library.json.predicate.tool.ToolStackItemPredicate;

import java.util.function.Consumer;

public class FTAdvancements implements ForgeAdvancementProvider.AdvancementGenerator {
    public static final ResourceLocation FOLDER = FTUtil.location(ForbiddenTinkers.MOD_ID + "/");

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        // Dummy root
        Advancement root = Advancement.Builder.advancement()
                .build(MTAdvancements.FOLDER.withSuffix("root"));

        Advancement findChampiumForge = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        FTItems.CHAMPIUM_FORGE.get(),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("find_champium_forge")) + ".title"),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("find_champium_forge")) + ".description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion(
                        "champium_forge",
                        PlayerTrigger.TriggerInstance.located(LocationPredicate.inStructure(ResourceKey.create(Registries.STRUCTURE, FTUtil.location("champium_forge")))))
                .save(saver, FOLDER.withSuffix("find_champium_forge").toString());

        Advancement forgeChampiumNugget = Advancement.Builder.advancement()
                .parent(findChampiumForge)
                .display(
                        FTItems.CHAMPIUM_NUGGET.get(),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("forge_champium_nugget")) + ".title"),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("forge_champium_nugget")) + ".description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false)
                .addCriterion(
                        "has_champium_nugget",
                        InventoryChangeTrigger.TriggerInstance.hasItems(FTItems.CHAMPIUM_NUGGET.get()))
                .save(saver, FOLDER.withSuffix("forge_champium_nugget").toString());

        Advancement.Builder.advancement()
                .parent(forgeChampiumNugget)
                .display(
                        FTItems.CHAMPIUM_INGOT.get(),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("champium_tool")) + ".title"),
                        Component.translatable(Util.makeDescriptionId("advancements", FTUtil.location("champium_tool")) + ".description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false)
                .addCriterion(
                        "craft_champium_tool",
                        InventoryChangeTrigger.TriggerInstance.hasItems(
                                ToolStackItemPredicate.ofContext(new HasMaterialPredicate(FTMaterialIds.CHAMPIUM))))
                .save(saver, FOLDER.withSuffix("champium_tool").toString());
    }
}
