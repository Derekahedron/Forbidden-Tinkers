package derekahedron.forbiddentinkers.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HeartItem extends Item {

    public HeartItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        if (entity instanceof LivingEntity livingEntity) {
            healFromStack(livingEntity, stack);
        }
    }

    public static void healFromStack(LivingEntity entity, ItemStack heartStack) {
        entity.heal(heartStack.getCount() * 2.0F);
        heartStack.setCount(0);
    }
}
