package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.fluid.FTFluidHolders;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialIds;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import java.util.function.Consumer;

public class FTMaterialRecipeProvider extends RecipeProvider implements IMaterialRecipeHelper {
    public static final String MATERIALS_FOLDER = "tools/materials/";

    public FTMaterialRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return String.format("%s Material Recipes", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    public String getModId() {
        return ForbiddenTinkers.MOD_ID;
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Champium
        metalMaterialRecipe(
                consumer,
                FTMaterialIds.CHAMPIUM,
                MATERIALS_FOLDER,
                "champium",
                false);
        materialMeltingCasting(
                consumer,
                FTMaterialIds.CHAMPIUM,
                FTFluidHolders.MOLTEN_CHAMPIUM.fluidObject,
                MATERIALS_FOLDER);
    }
}
