package derekahedron.forbiddentinkers.mixin;

import net.minecraft.world.entity.item.ItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.UUID;

@Mixin(ItemEntity.class)
public interface ItemEntityAccessor {

    @Accessor(value = "pickupDelay")
    int forbiddentinkers_$getPickupDelay();

    @Accessor(value = "target")
    UUID forbiddentinkers_$getTarget();

}
