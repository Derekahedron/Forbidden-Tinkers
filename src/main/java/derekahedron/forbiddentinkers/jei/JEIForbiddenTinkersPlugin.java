package derekahedron.forbiddentinkers.jei;

import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.recipe.FTRecipeTypes;
import derekahedron.forbiddentinkers.util.FTUtil;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIForbiddenTinkersPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return FTUtil.location("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new ChampiumForgeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (Minecraft.getInstance().level == null) return;

        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<ChampiumForgeRecipe> recipes = recipeManager.getAllRecipesFor(FTRecipeTypes.CHAMPIUM_FORGE.get());
        registration.addRecipes(ChampiumForgeCategory.CHAMPIUM_FORGE_TYPE, recipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        ChampiumForgeCategory.registerGuiHandlers(registration);
    }
}
