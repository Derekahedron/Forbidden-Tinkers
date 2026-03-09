package derekahedron.forbiddentinkers.datagen.models;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.item.FTItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

public class FTItemModelProvider extends ItemModelProvider {

    public FTItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ForbiddenTinkers.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        forbiddenTome(FTItems.FORBIDDEN_TOME.get());
        basicItem(FTItems.CLOWN_NOSE.get());
        basicItem(FTItems.HEART.get());
        basicItem(FTItems.TINKERS_DIAMOND.get());
        basicItem(FTItems.RAW_CHAMPIUM.get());
        basicItem(FTItems.CHAMPIUM_NUGGET.get());
        basicItem(FTItems.CHAMPIUM_INGOT.get());
        basicItem(FTItems.MOLTEN_CHAMPIUM_BUCKET.get());
        basicItem(FTItems.CHAMPIONS_MEDAL.get());
    }

    public void forbiddenTome(Item item) {
        forbiddenTome(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item)));
    }

    public void forbiddenTome(ResourceLocation item) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", item.withPrefix("item/"))
                .texture("layer1", item.withPrefix("item/").withSuffix("_overlay"));
    }
}
