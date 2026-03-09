package derekahedron.forbiddentinkers.client;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.client.inventory.ChampiumForgeScreen;
import derekahedron.forbiddentinkers.client.particle.ChampiumSmokeParticle;
import derekahedron.forbiddentinkers.fluid.FTFluids;
import derekahedron.forbiddentinkers.inventory.FTMenuTypes;
import derekahedron.forbiddentinkers.particle.FTParticleTypes;
import derekahedron.mythictinkers.client.particle.MTDripParticle;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.LavaParticle;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ForbiddenTinkersClient {

    @SubscribeEvent
    static void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(FTMenuTypes.CHAMPIUM_FORGE.get(), ChampiumForgeScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerParticleProvidersEvent(RegisterParticleProvidersEvent event) {

        event.registerSpriteSet(
                FTParticleTypes.MOLTEN_CHAMPIUM.get(),
                LavaParticle.Provider::new);
        event.registerSprite(
                FTParticleTypes.DRIPPING_MOLTEN_CHAMPIUM.get(),
                (type, level, x, y, z, xd, yd, zd) ->
                        new MTDripParticle.CoolingDripHandParticle(
                                level, x, y, z,
                                FTFluids.MOLTEN_CHAMPIUM.get(),
                                FTParticleTypes.FALLING_MOLTEN_CHAMPIUM.get(),
                                new Color(0xFFEB89),
                                new Color(0xEFFFFB)));
        event.registerSprite(
                FTParticleTypes.FALLING_MOLTEN_CHAMPIUM.get(),
                (type, level, x, y, z, xd, yd, zd) ->
                        new MTDripParticle.FallAndLandParticle(
                                level, x, y, z,
                                FTFluids.MOLTEN_CHAMPIUM.get(),
                                FTParticleTypes.LANDING_MOLTEN_CHAMPIUM.get(),
                                new Color(0xFFEB89)));
        event.registerSprite(
                FTParticleTypes.LANDING_MOLTEN_CHAMPIUM.get(),
                (type, level, x, y, z, xd, yd, zd) ->
                        new MTDripParticle.DripLandParticle(
                                level, x, y, z,
                                FTFluids.MOLTEN_CHAMPIUM.get(),
                                new Color(0xFFEB89)));

        event.registerSpriteSet(
                FTParticleTypes.CHAMPIUM_FORGE_SMOKE.get(),
                ChampiumSmokeParticle.Provider::new);
        event.registerSpriteSet(
                FTParticleTypes.BROKEN_CHAMPIUM_FORGE_SMOKE.get(),
                ChampiumSmokeParticle.Provider::new);
    }
}
