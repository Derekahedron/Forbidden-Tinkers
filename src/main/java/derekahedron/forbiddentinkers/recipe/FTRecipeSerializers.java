package derekahedron.forbiddentinkers.recipe;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.recipe.helper.LoadableRecipeSerializer;

public class FTRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<RecipeSerializer<OverloadModifierRecipe>> OVERLOADED_MODIFIER =
            RECIPE_SERIALIZERS.register("overload_modifier", () -> LoadableRecipeSerializer.of(OverloadModifierRecipe.LOADER));
    public static final RegistryObject<RecipeSerializer<AugmentationModifierRecipe>> AUGMENTATION_MODIFIER =
            RECIPE_SERIALIZERS.register("augmentation_modifier", () -> LoadableRecipeSerializer.of(AugmentationModifierRecipe.LOADER));
    public static final RegistryObject<RecipeSerializer<ChampiumForgeRecipe>> CHAMPIUM_FORGE =
            RECIPE_SERIALIZERS.register("champium_forge", ChampiumForgeRecipe.Serializer::new);
}
