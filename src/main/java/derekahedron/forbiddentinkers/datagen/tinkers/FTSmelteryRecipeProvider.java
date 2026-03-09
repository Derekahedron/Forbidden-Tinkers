package derekahedron.forbiddentinkers.datagen.tinkers;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.fluid.FTFluidHolders;
import derekahedron.forbiddentinkers.fluid.FTFluids;
import derekahedron.forbiddentinkers.item.FTItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import java.util.function.Consumer;

public class FTSmelteryRecipeProvider extends RecipeProvider implements ISmelteryRecipeHelper {
    public static final String MELTING_FOLDER = "smeltery/melting/";
    public static final String CASTING_FOLDER = "smeltery/casting/";
    public static final String METAL_MELTING_FOLDER = MELTING_FOLDER + "metal/";
    public static final String METAL_CASTING_FOLDER = CASTING_FOLDER + "metal/";

    public FTSmelteryRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    public String getModId() {
        return ForbiddenTinkers.MOD_ID;
    }

    @Override
    public String getName() {
        return String.format("%s Smeltery Recipes", ForbiddenTinkers.MOD_NAME);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // Champium
        molten(
                consumer,
                FTFluidHolders.MOLTEN_CHAMPIUM.fluidObject)
                .castingFolder(METAL_CASTING_FOLDER)
                .meltingFolder(METAL_MELTING_FOLDER)
                .ore()
                .metal();
        MeltingRecipeBuilder.melting(Ingredient.of(FTItems.CHAMPIONS_MEDAL.get()),
                        FTFluids.MOLTEN_CHAMPIUM.get(),
                        FluidValues.INGOT * 2)
                .save(consumer, location(METAL_MELTING_FOLDER + "champium/champions_medal"));
    }
}
