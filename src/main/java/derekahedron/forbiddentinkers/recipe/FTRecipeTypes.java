package derekahedron.forbiddentinkers.recipe;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.util.FTUtil;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES =
            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<RecipeType<ChampiumForgeRecipe>> CHAMPIUM_FORGE =
            RECIPE_TYPES.register("champium_forge", () -> RecipeType.simple(FTUtil.location("champium_forge")));
}
