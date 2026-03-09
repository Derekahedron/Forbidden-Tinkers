package derekahedron.forbiddentinkers.item;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.recipe.OverloadModifierRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("unused")
public class FTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<CreativeModeTab> FORBIDDEN_TINKERS_TAB =
            CREATIVE_MODE_TABS.register("forbidden_tinkers", () ->
                    CreativeModeTab.builder()
                            .title(Component.translatable("itemGroup.forbiddentinkers.forbidden_tinkers"))
                            .icon(() -> FTItems.CHAMPIUM_INGOT.get().getDefaultInstance())
                            .displayItems((parameters, output) -> {
                                output.accept(FTItems.CLOWN_NOSE.get());


                                OverloadModifierRecipe.RECIPES.forEach(recipe ->
                                        output.accept(FTItems.FORBIDDEN_TOME.get().withModifier(recipe.modifierId)));

                                // Augmentation Gem
                                output.accept(FTItems.TINKERS_DIAMOND_BLOCK.get());
                                output.accept(FTItems.TINKERS_DIAMOND_ORE.get());
                                output.accept(FTItems.DEEPSLATE_TINKERS_DIAMOND_ORE.get());
                                output.accept(FTItems.TINKERS_DIAMOND.get());

                                // Bedrock Bricks
                                output.accept(FTItems.BEDROCK_BRICKS.get());
                                output.accept(FTItems.BEDROCK_BRICK_STAIRS.get());
                                output.accept(FTItems.BEDROCK_BRICK_SLAB.get());
                                output.accept(FTItems.BEDROCK_BRICK_WALL.get());

                                // Champium
                                output.accept(FTItems.CHAMPIUM_FORGE.get());
                                output.accept(FTItems.CHAMPIUM_BLOCK.get());
                                output.accept(FTItems.CHAMPIUM_ORE.get());
                                output.accept(FTItems.DEEPSLATE_CHAMPIUM_ORE.get());
                                output.accept(FTItems.BEDROCK_CHAMPIUM_ORE.get());
                                output.accept(FTItems.RAW_CHAMPIUM_BLOCK.get());
                                output.accept(FTItems.RAW_CHAMPIUM.get());
                                output.accept(FTItems.CHAMPIUM_NUGGET.get());
                                output.accept(FTItems.CHAMPIUM_INGOT.get());

                                // Buckets
                                output.accept(FTItems.MOLTEN_CHAMPIUM_BUCKET.get());
                            }).build());
}
