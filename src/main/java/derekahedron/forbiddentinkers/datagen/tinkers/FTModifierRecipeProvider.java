package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTItemTags;
import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.forbiddentinkers.recipe.AugmentationModifierRecipe;
import derekahedron.forbiddentinkers.recipe.OverloadModifierRecipe;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import derekahedron.forbiddentinkers.tinkers.modifiers.FTModifierIds;
import derekahedron.mythictinkers.datagen.tinkers.MTModifierRecipeProvider;
import derekahedron.mythictinkers.tinkers.materials.MTMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.DifferenceIngredient;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.SwappableModifierRecipe;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.shared.block.SlimeType;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;
import slimeknights.tconstruct.tools.data.material.MaterialIds;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class FTModifierRecipeProvider extends RecipeProvider {
    public static final String MODIFIER_FOLDER = "tools/modifiers/";
    public static final String UPGRADE_FOLDER = MODIFIER_FOLDER + "upgrade/";
    public static final String ABILITY_FOLDER = MODIFIER_FOLDER + "ability/";
    public static final String SLOTLESS_FOLDER = MODIFIER_FOLDER + "slotless/";
    public static final String SALVAGE_FOLDER = MODIFIER_FOLDER + "salvage/";
    public static final String SALVAGE_UPGRADE_FOLDER = SALVAGE_FOLDER + "upgrade/";
    public static final String SALVAGE_ABILITY_FOLDER = SALVAGE_FOLDER + "ability/";

    public FTModifierRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return String.format("%s Modifier Recipes", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {

        ModifierRecipeBuilder.modifier(FTModifierIds.FUNNY)
                .addInput(FTItems.CLOWN_NOSE.get())
                .setMaxLevel(3)
                .setTools(CompoundIngredient.of(
                        Ingredient.of(TinkerTags.Items.MELEE),
                        Ingredient.of(TinkerTags.Items.HARVEST),
                        Ingredient.of(TinkerTags.Items.RANGED),
                        Ingredient.of(TinkerTags.Items.ARMOR),
                        Ingredient.of(TinkerTags.Items.SHIELDS)))
                .save(consumer, FTModifierIds.FUNNY.withPrefix(SLOTLESS_FOLDER));

        Ingredient dropsItems = CompoundIngredient.of(
                Ingredient.of(TinkerTags.Items.MELEE),
                Ingredient.of(TinkerTags.Items.HARVEST),
                Ingredient.of(TinkerTags.Items.RANGED));

        ModifierRecipeBuilder.modifier(FTModifierIds.DISPOSING)
                .allowCrystal()
                .addInput(Items.HOPPER)
                .addInput(Items.CACTUS)
                .addInput(Items.HOPPER)
                .setMaxLevel(1)
                .setSlots(SlotType.UPGRADE, 1)
                .setTools(dropsItems)
                .saveSalvage(consumer, FTModifierIds.DISPOSING.withPrefix(SALVAGE_UPGRADE_FOLDER))
                .save(consumer, FTModifierIds.DISPOSING.withPrefix(UPGRADE_FOLDER));

        ModifierRecipeBuilder.modifier(FTModifierIds.WORMHOLE)
                .allowCrystal()
                .addInput(Items.HOPPER)
                .addInput(Items.ENDER_EYE)
                .addInput(Items.HOPPER)
                .addInput(SlimeType.ENDER.getSlimeballTag())
                .addInput(SlimeType.ENDER.getSlimeballTag())
                .setMaxLevel(1)
                .setSlots(SlotType.ABILITY, 1)
                .setTools(dropsItems)
                .saveSalvage(consumer, FTModifierIds.WORMHOLE.withPrefix(SALVAGE_ABILITY_FOLDER))
                .save(consumer, FTModifierIds.WORMHOLE.withPrefix(ABILITY_FOLDER));

        // Upgrades
        createOverloadRecipe(consumer, ModifierIds.experienced);
        createOverloadRecipe(consumer, TinkerModifiers.magnetic.getId());
        createOverloadRecipe(consumer, ModifierIds.overforced);
        createOverloadRecipe(consumer, ModifierIds.smelting);
        createOverloadRecipe(consumer, ModifierIds.tank);
        createOverloadRecipe(consumer, ModifierIds.blockade);
        createOverloadRecipe(consumer, ModifierIds.fiery);
        createOverloadRecipe(consumer, ModifierIds.freezing);
        createOverloadRecipe(consumer, ModifierIds.lure);
        createOverloadRecipe(consumer, TinkerModifiers.knockback.getId());
        createOverloadRecipe(consumer, TinkerModifiers.necrotic.getId());
        createOverloadRecipe(consumer, TinkerModifiers.padded.getId());
        createOverloadRecipe(consumer, TinkerModifiers.severing.getId());
        createOverloadRecipe(consumer, TinkerModifiers.sweeping.getId());
        createOverloadRecipe(consumer, ModifierIds.antiaquatic);
        createOverloadRecipe(consumer, ModifierIds.baneOfSssss);
        createOverloadRecipe(consumer, ModifierIds.cooling);
        createOverloadRecipe(consumer, ModifierIds.killager);
        createOverloadRecipe(consumer, ModifierIds.pierce);
        createOverloadRecipe(consumer, ModifierIds.sharpness);
        createOverloadRecipe(consumer, ModifierIds.smite);
        createOverloadRecipe(consumer, ModifierIds.swiftstrike);
        createOverloadRecipe(consumer, ModifierIds.blasting);
        createOverloadRecipe(consumer, ModifierIds.haste);
        createOverloadRecipe(consumer, ModifierIds.hydraulic);
        createOverloadRecipe(consumer, ModifierIds.lightspeed);
        createOverloadRecipe(consumer, ModifierIds.arrowPierce);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("tinkers_things:blinding")), false);
        createOverloadRecipe(consumer, ModifierIds.power);
        createOverloadRecipe(consumer, ModifierIds.punch);
        createOverloadRecipe(consumer, ModifierIds.quickCharge);
        createOverloadRecipe(consumer, ModifierIds.trueshot);
        createOverloadRecipe(consumer, ModifierIds.ricochet);
        createOverloadRecipe(consumer, ModifierIds.springy);
        createOverloadRecipe(consumer, ModifierIds.thorns);
        createOverloadRecipe(consumer, TinkerModifiers.itemFrame.getId());
        createOverloadRecipe(consumer, ModifierIds.minimap);
        createOverloadRecipe(consumer, ModifierIds.respiration);
        createOverloadRecipe(consumer, TinkerModifiers.sleeves.getId());
        createOverloadRecipe(consumer, ModifierIds.leaping);
        createOverloadRecipe(consumer, TinkerModifiers.shieldStrap.getId());
        createOverloadRecipe(consumer, ModifierIds.speedy);
        createOverloadRecipe(consumer, ModifierIds.stepUp);
        createOverloadRecipe(consumer, TinkerModifiers.soulspeed.getId());
        createOverloadRecipe(consumer, FTModifierIds.HEART_DROPPING);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:expedient")), false);
        // Abilities
        createOverloadRecipe(consumer, TinkerModifiers.expanded.getId());
        createOverloadRecipe(consumer, ModifierIds.luck);
        createOverloadRecipe(consumer, TinkerModifiers.slurping.getId());
        createOverloadRecipe(consumer, TinkerModifiers.spitting.getId());
        createOverloadRecipe(consumer, TinkerModifiers.splashing.getId());
        createOverloadRecipe(consumer, ModifierIds.spilling);
        createOverloadRecipe(consumer, ModifierIds.bulkQuiver);
        createOverloadRecipe(consumer, ModifierIds.channeling);
        createOverloadRecipe(consumer, TinkerModifiers.multishot.getId());
        createOverloadRecipe(consumer, ModifierIds.returning);
        createOverloadRecipe(consumer, TinkerModifiers.bursting.getId());
        createOverloadRecipe(consumer, TinkerModifiers.wetting.getId());
        createOverloadRecipe(consumer, ModifierIds.reach);
        createOverloadRecipe(consumer, ModifierIds.strength);
        createOverloadRecipe(consumer, ModifierIds.pockets);
        createOverloadRecipe(consumer, ModifierIds.doubleJump);
        // Affinity
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:ice_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:fire_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:lightning_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:ender_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:holy_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:evocation_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:blood_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:nature_upgrade")), false);
        createOverloadRecipe(consumer, new ModifierId(new ResourceLocation("constructs_casting:mana_upgrade")), false);

        MTModifierRecipeProvider.createBoonRecipe(consumer,
                FTModifierIds.CHAMPIUM_BOON,
                FTItems.CHAMPIUM_INGOT.get());

        for (MaterialVariantId materialId : Set.copyOf(List.of(
                // General
                MaterialIds.wood,
                MaterialIds.rock,
                MaterialIds.flint,
                MaterialIds.bone,
                MaterialIds.copper,
                MaterialIds.chorus,
                MaterialIds.iron,
                MaterialIds.searedStone,
                MaterialIds.venombone,
                MaterialIds.slimewood,
                MaterialIds.necroticBone,
                MaterialIds.scorchedStone,
                MaterialIds.endstone,
                MTMaterialIds.SCARLET_NEODYMIUM,
                MTMaterialIds.AZURE_NEODYMIUM,
                MaterialIds.slimesteel,
                MaterialIds.amethystBronze,
                MaterialIds.nahuatl,
                MaterialIds.pigIron,
                MaterialIds.roseGold,
                MaterialIds.cobalt,
                MaterialIds.steel,
                MaterialIds.cinderslime,
                MaterialIds.queensSlime,
                MaterialIds.hepatizon,
                MaterialIds.manyullyn,
                MaterialIds.blazingBone,
                MaterialIds.knightmetal,
                MTMaterialIds.DESOLUM,
                MTMaterialIds.BYZANTIUM_NEODYMIUM,
                MTMaterialIds.TECTELLUS,
                MTMaterialIds.ELEMENT_122,
                MTMaterialIds.AURICHALCUM,
                MTMaterialIds.PROSPRUM,
                MTMaterialIds.GUMBRONZE,
                FTMaterialIds.CHAMPIUM,
                MaterialIds.string,
                MaterialIds.leather,
                MaterialIds.vine,
                MaterialIds.skyslimeVine,
                MaterialIds.twistingVine,
                MaterialIds.weepingVine,
                MaterialIds.darkthread,
                MaterialIds.ancientHide,
                MaterialIds.enderslimeVine,
                // Ranged
                MaterialIds.string,
                MaterialIds.leather,
                MaterialIds.vine,
                MaterialIds.skyslimeVine,
                MaterialIds.slimeskin,
                MaterialIds.twistingVine,
                MaterialIds.weepingVine,
                MTMaterialIds.SHADOW_SILK,
                MaterialIds.darkthread,
                MaterialIds.ancientHide,
                MaterialIds.enderslimeVine,
                MaterialIds.roseGold,
                MaterialIds.wood,
                MaterialIds.bone,
                MaterialIds.copper,
                MaterialIds.bamboo,
                MaterialIds.chorus,
                MaterialIds.iron,
                MaterialIds.venombone,
                MaterialIds.slimewood,
                MaterialIds.necroticBone,
                MTMaterialIds.SCARLET_NEODYMIUM,
                MTMaterialIds.AZURE_NEODYMIUM,
                MaterialIds.slimesteel,
                MaterialIds.amethystBronze,
                MaterialIds.nahuatl,
                MaterialIds.pigIron,
                MaterialIds.cobalt,
                MaterialIds.steel,
                MaterialIds.cinderslime,
                MaterialIds.queensSlime,
                MaterialIds.hepatizon,
                MaterialIds.manyullyn,
                MaterialIds.blazingBone,
                MaterialIds.knightmetal,
                MTMaterialIds.DESOLUM,
                MTMaterialIds.BYZANTIUM_NEODYMIUM,
                MTMaterialIds.TECTELLUS,
                MTMaterialIds.ELEMENT_122,
                MTMaterialIds.AURICHALCUM,
                MTMaterialIds.PROSPRUM,
                MTMaterialIds.GUMBRONZE,
                FTMaterialIds.CHAMPIUM,
                // Ammo
                MaterialIds.flint,
                MaterialIds.wool,
                MaterialIds.amethyst,
                MaterialIds.earthslime,
                MaterialIds.enderPearl,
                MaterialIds.glass,
                MaterialIds.gunpowder,
                MaterialIds.prismarine,
                MaterialIds.skyslime,
                MaterialIds.ice,
                MaterialIds.glowstone,
                MaterialIds.ichor,
                MaterialIds.magnetite,
                MaterialIds.quartz,
                MaterialIds.dragonScale,
                MaterialIds.enderslime,
                MaterialIds.knightly,
                MaterialIds.shulker,
                MaterialIds.wood,
                MaterialIds.bone,
                MaterialIds.bamboo,
                MaterialIds.chorus,
                MaterialIds.venombone,
                MaterialIds.slimewood,
                MaterialIds.necroticBone,
                MaterialIds.blaze,
                MaterialIds.nahuatl,
                MaterialIds.blazingBone,
                MaterialIds.blazewood,
                MaterialIds.endRod,
                MaterialIds.feather,
                MaterialIds.leaves,
                MaterialIds.paper,
                MaterialIds.slimeball,
                MaterialIds.magma,
                // Armor
                MaterialIds.leather,
                MaterialIds.skyslimeVine,
                MaterialIds.slimeskin,
                MaterialIds.ichorskin,
                MaterialIds.ancientHide,
                MaterialIds.enderslimeVine,
                MaterialIds.vine,
                MaterialIds.twistingVine,
                MaterialIds.weepingVine,
                MaterialIds.dragonScale,
                MaterialIds.shulker,
                MaterialIds.copper,
                MaterialIds.iron,
                MaterialIds.searedStone,
                MaterialIds.scorchedStone,
                MaterialIds.gold,
                MTMaterialIds.SCARLET_NEODYMIUM,
                MTMaterialIds.AZURE_NEODYMIUM,
                MaterialIds.slimesteel,
                MaterialIds.amethystBronze,
                MaterialIds.obsidian,
                MaterialIds.pigIron,
                MaterialIds.roseGold,
                MaterialIds.cobalt,
                MaterialIds.steel,
                MaterialIds.cinderslime,
                MaterialIds.queensSlime,
                MaterialIds.hepatizon,
                MaterialIds.manyullyn,
                MaterialIds.knightmetal,
                MTMaterialIds.DESOLUM,
                MTMaterialIds.BYZANTIUM_NEODYMIUM,
                MTMaterialIds.TECTELLUS,
                MTMaterialIds.ELEMENT_122,
                MTMaterialIds.AURICHALCUM,
                MTMaterialIds.PROSPRUM,
                MTMaterialIds.GUMBRONZE,
                FTMaterialIds.CHAMPIUM,
                MaterialIds.wood,
                MaterialIds.bone,
                MaterialIds.bamboo,
                MaterialIds.chorus,
                MaterialIds.cactus,
                MaterialIds.venombone,
                MaterialIds.slimewood,
                MaterialIds.necroticBone,
                MaterialIds.nahuatl,
                MaterialIds.ice,
                MaterialIds.blazingBone,
                MaterialIds.blazewood,
                // Extras
                MaterialIds.treatedWood,
                MaterialIds.osmium,
                MaterialIds.silver,
                MaterialIds.lead,
                MaterialIds.aluminum,
                MaterialIds.bronze,
                MaterialIds.constantan,
                MaterialIds.invar,
                MaterialIds.necronium,
                MaterialIds.electrum,
                MaterialIds.pewter,
                MaterialIds.platedSlimewood,
                MaterialIds.ironwood,
                MaterialIds.steeleaf,
                MaterialIds.fiery,
                MaterialIds.gold
        ))) {
            createAugmentationRecipe(consumer, materialId);
        }

        for (MaterialVariantId materialId : Set.copyOf(List.of(
                new MaterialId(new ResourceLocation("blue_skies_tcon:aquite")),
                new MaterialId(new ResourceLocation("blue_skies_tcon:horizonite")),
                new MaterialId(new ResourceLocation("blue_skies_tcon:pyrope")),
                new MaterialId(new ResourceLocation("blue_skies_tcon:charoite")),
                new MaterialId(new ResourceLocation("blue_skies_tcon:diopside")),
                new MaterialId(new ResourceLocation("constructs_casting:arcanium")),
                new MaterialId(new ResourceLocation("constructs_casting:frozen_bone")),
                new MaterialId(new ResourceLocation("constructs_casting:exilite")),
                new MaterialId(new ResourceLocation("constructs_casting:mithril")),
                new MaterialId(new ResourceLocation("constructs_casting:pyrium")),
                new MaterialId(new ResourceLocation("constructs_casting:frosted_rod")),
                new MaterialId(new ResourceLocation("constructs_casting:arcane_cloth")),
                new MaterialId(new ResourceLocation("thermalconstruct:basalz_bone")),
                new MaterialId(new ResourceLocation("thermalconstruct:blitz_bone")),
                new MaterialId(new ResourceLocation("thermalconstruct:signalum")),
                new MaterialId(new ResourceLocation("thermalconstruct:lumium")),
                new MaterialId(new ResourceLocation("thermalconstruct:enderium")),
                new MaterialId(new ResourceLocation("tinkers_things:hematite")),
                new MaterialId(new ResourceLocation("tinkers_things:magmaskin"))
        ))) {
            createAugmentationRecipe(consumer, materialId, false);
        }
    }

    public static void createAugmentationRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId materialId, boolean required) {
        if (!required) {
            consumer = ConsumerWrapperBuilder.wrap()
                    .addCondition(new ModLoadedCondition(materialId.getId().getNamespace()))
                    .build(consumer);
        }

        AugmentationModifierRecipe.Builder.modifier(FTModifierIds.AUGMENTATION, materialId)
                .variantFormatter(SwappableModifierRecipe.VariantFormatter.MATERIAL)
                .addInput(DifferenceIngredient.of(Ingredient.of(TinkerTags.Items.TOOL_PARTS), Ingredient.of(FTItemTags.AUGMENTATION_PART_BLACKLIST)))
                .addInput(FTItems.TINKERS_DIAMOND.get())
                .setTools(TinkerTags.Items.MODIFIABLE)
                .save(consumer, FTModifierIds.AUGMENTATION
                        .withPrefix(MODIFIER_FOLDER)
                        .withSuffix("/" + materialId.getId().getNamespace() + "/" + materialId.getId().getPath()));
    }

    public static void createAugmentationRecipe(Consumer<FinishedRecipe> consumer, MaterialVariantId materialId) {
        createAugmentationRecipe(consumer, materialId, true);
    }

    public static void createOverloadRecipe(Consumer<FinishedRecipe> consumer, ModifierId modifierId, boolean required) {
        if (!required) {
            consumer = ConsumerWrapperBuilder.wrap()
                    .addCondition(new ModLoadedCondition(modifierId.getNamespace()))
                    .build(consumer);
        }

        OverloadModifierRecipe.Builder.modifier(FTModifierIds.OVERLOADED, modifierId)
                .variantFormatter(OverloadModifierRecipe.MODIFIER_VARIANT_FORMATER)
                .addInput(FTItems.FORBIDDEN_TOME.get())
                .setTools(TinkerTags.Items.MODIFIABLE)
                .save(consumer, FTModifierIds.OVERLOADED
                        .withPrefix(MODIFIER_FOLDER)
                        .withSuffix("/" + modifierId.getNamespace() + "/" + modifierId.getPath()));
    }

    public static void createOverloadRecipe(Consumer<FinishedRecipe> consumer, ModifierId modifierId) {
        createOverloadRecipe(consumer, modifierId, true);
    }
}
