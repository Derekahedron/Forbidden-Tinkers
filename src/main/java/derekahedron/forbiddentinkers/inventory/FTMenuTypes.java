package derekahedron.forbiddentinkers.inventory;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FTMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, ForbiddenTinkers.MOD_ID);

    public static final RegistryObject<MenuType<ChampiumForgeMenu>> CHAMPIUM_FORGE =
            MENU_TYPES.register("champium_forge", () -> IForgeMenuType.create(ChampiumForgeMenu::readBlockEntity));
}
