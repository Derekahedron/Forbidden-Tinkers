package derekahedron.forbiddentinkers.block.dispenser;

import derekahedron.forbiddentinkers.item.FTItems;
import derekahedron.mythictinkers.block.dispenser.BucketDispenserBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;

public class FTDispenserBehavior {

    public static void initialize(FMLLoadCompleteEvent event) {
        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(FTItems.MOLTEN_CHAMPIUM_BUCKET.get(), new BucketDispenserBehavior());
        });
    }
}
