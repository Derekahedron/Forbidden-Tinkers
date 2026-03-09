package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTTiers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import net.minecraft.data.PackOutput;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.*;

public class FTMaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

    public FTMaterialStatsDataProvider(PackOutput output, AbstractMaterialDataProvider materials) {
        super(output, materials);
    }

    @Override
    public String getName() {
        return String.format("%s Material Stats", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void addMaterialStats() {
        FTTiers.registerTiers(); // Register tiers so they can appear in stats definitions
        addMeleeHarvest();
        addRanged();
        addArmor();
    }

    private void addMeleeHarvest() {
        addMaterialStats(FTMaterialIds.CHAMPIUM,
                new HeadMaterialStats(
                        1100,
                        7.0F,
                        FTTiers.CHAMPIUM,
                        3.0F),
                HandleMaterialStats.multipliers()
                        .durability(1.1F)
                        .miningSpeed(1.1F)
                        .attackSpeed(1.1F)
                        .attackDamage(1.1F)
                        .build(),
                StatlessMaterialStats.BINDING);
    }

    private void addRanged() {
        addMaterialStats(FTMaterialIds.CHAMPIUM,
                new LimbMaterialStats(
                        1100,
                        0.1F,
                        0.1F,
                        0.1F),
                new GripMaterialStats(
                        0.1F,
                        0.1F,
                        3.0F));
    }

    private void addArmor() {
        addArmorShieldStats(FTMaterialIds.CHAMPIUM,
                PlatingMaterialStats.builder()
                        .durabilityFactor(41.0F)
                        .armor(2.0F, 5.0F, 7.0F, 2.0F)
                        .toughness(3.0F)
                        .knockbackResistance(0.1F),
                StatlessMaterialStats.MAILLE);
    }
}
