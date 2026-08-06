package derekahedron.forbiddentinkers.inventory;

import derekahedron.forbiddentinkers.block.entity.ChampiumForgeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;

import javax.annotation.Nullable;
import java.util.List;

public class ChampiumForgeMenu extends AbstractContainerMenu {

    public static final int WIDTH = 208;
    public static final int HEIGHT = 195;
    public static final int BORDER = 3;
    public static final int PADDING = 4;
    public static final int FONT_MARGIN = 2;
    public static final int FONT_PADDING = 2;
    public static final int FONT_HEIGHT = 9 + FONT_PADDING * 2;
    public static final int INPUTS_GAP = 3;
    public static final int SLOT_BORDER = 1;
    public static final int ITEM_SIZE = 16;
    public static final int FILL_BAR_BORDER = 1;
    public static final int FILL_BAR_WIDTH = 192;
    public static final int FILL_BAR_HEIGHT = 8;
    public static final int SLOT_SIZE = ITEM_SIZE + SLOT_BORDER * 2;
    public static final int RESULT_SLOT_BORDER = 5;
    public static final int RESULT_SLOT_SIZE = ITEM_SIZE + RESULT_SLOT_BORDER * 2;

    public final ChampiumForgeBlockEntity blockEntity;

    public ChampiumForgeMenu(int id, Inventory inventory, BlockEntity blockEntity) {
        super(FTMenuTypes.CHAMPIUM_FORGE.get(), id);

        this.blockEntity = (ChampiumForgeBlockEntity) blockEntity;
        this.blockEntity.loadRecipe();

        addChampiumForgeSlots(inventory);
        addInventorySlots(inventory);
        addDataSlots(this.blockEntity.data);
    }

    public void addChampiumForgeSlots(Inventory inventory) {
        int numIngredients = blockEntity.recipe == null ? 0 : blockEntity.recipe.ingredients.size();
        int x = (WIDTH - SLOT_SIZE * numIngredients - PADDING * Math.max(numIngredients - 1, 0)) / 2;
        int y = BORDER + FONT_MARGIN + FONT_HEIGHT + ITEM_SIZE + INPUTS_GAP;

        for (int slotIndex = 0; slotIndex < ChampiumForgeBlockEntity.NUM_INPUT_SLOTS; slotIndex++) {
            if (slotIndex < numIngredients) {
                addSlot(new IngredientSlot(
                        blockEntity,
                        slotIndex,
                        x + slotIndex * SLOT_SIZE + slotIndex * PADDING + SLOT_BORDER,
                        y + SLOT_BORDER));
            } else {
                addSlot(new IngredientSlot(
                        blockEntity,
                        slotIndex,
                        0,
                        0));
            }
        }

        x = (WIDTH - RESULT_SLOT_SIZE) / 2;
        y += SLOT_SIZE + PADDING + FILL_BAR_BORDER + FILL_BAR_HEIGHT + FILL_BAR_BORDER + PADDING;
        addSlot(new ResultSlot(
                inventory.player,
                blockEntity,
                ChampiumForgeBlockEntity.NUM_INPUT_SLOTS,
                x + RESULT_SLOT_BORDER,
                y + RESULT_SLOT_BORDER));
    }

    public void addInventorySlots(Inventory inventory) {
        int x = (WIDTH - 9 * SLOT_SIZE) / 2;
        int y = HEIGHT - BORDER - PADDING - SLOT_SIZE - PADDING;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                addSlot(new Slot(inventory, col + row * 9 + 9,
                        x + col * SLOT_SIZE + SLOT_BORDER,
                        y - (3 - row) * SLOT_SIZE + SLOT_BORDER));
            }
        }

        y = HEIGHT - BORDER - PADDING - SLOT_SIZE;
        for (int k = 0; k < 9; ++k) {
            addSlot(new Slot(inventory, k,
                    x + k * SLOT_SIZE + SLOT_BORDER,
                    y + SLOT_BORDER));
        }
    }

    @Nullable
    public static ChampiumForgeMenu readBlockEntity(int id, Inventory inventory, FriendlyByteBuf extraData) {
        BlockPos pos = extraData.readBlockPos();
        ResourceLocation recipeId = extraData.readOptional(FriendlyByteBuf::readResourceLocation).orElse(null);
        Integer maxUses = extraData.readOptional(FriendlyByteBuf::readInt).orElse(null);
        NonNullList<Ingredient> ingredients = extraData.readOptional(
                (buf) -> buf.readCollection(
                        NonNullList::createWithCapacity,
                        b -> Ingredient.fromJson(GsonHelper.parse(b.readUtf()))))
                .orElse(null);

        if (inventory.player.level().getBlockEntity(pos) instanceof ChampiumForgeBlockEntity blockEntity) {
            blockEntity.recipeId = recipeId;
            blockEntity.maxUses = maxUses;
            blockEntity.ingredients = ingredients;
            return new ChampiumForgeMenu(id, inventory, blockEntity);
        } else {
            return null;
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        int menuSize = blockEntity.getContainerSize() + 36;

        Slot slot = slots.get(slotIndex);
        if (!slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        if (slotIndex < blockEntity.getContainerSize() && slotIndex >= 0) {
            if (!moveItemStackTo(stack, blockEntity.getContainerSize(), menuSize, true)) {
                return ItemStack.EMPTY;
            }

            slot.onQuickCraft(stack, copy);
        } else if (slotIndex >= blockEntity.getContainerSize()) {
            blockEntity.loadRecipe();
            if (blockEntity.ingredients == null
                    || !blockEntity.hasUsesRemaining()
                    || blockEntity.ingredients.stream().noneMatch(ingredient -> ingredient.test(stack))) {
                if (slotIndex >= menuSize - 9) {
                    if (!moveItemStackTo(stack, blockEntity.getContainerSize(), menuSize - 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!moveItemStackTo(stack, menuSize - 9, menuSize, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(stack, 0, ChampiumForgeBlockEntity.NUM_INPUT_SLOTS, false)) {
                    return ItemStack.EMPTY;
                }
            }
        }

        if (stack.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }

        if (stack.getCount() == copy.getCount()) {
            return ItemStack.EMPTY;
        }

        slot.onTake(player, stack);

        return copy;
    }

    @Override
    public boolean stillValid(Player player) {
        return blockEntity.stillValid(player);
    }

    public class IngredientSlot extends Slot {

        public IngredientSlot(Container container, int index, int x, int y) {
            super(container, index, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            blockEntity.loadRecipe();
            if (blockEntity.recipe == null) return false;
            if (blockEntity.ingredients == null) return false;
            if (!blockEntity.hasUsesRemaining()) return false;
            if (index < 0 || index >= blockEntity.ingredients.size()) return false;
            return blockEntity.ingredients.get(index).test(stack);
        }

        @Override
        public boolean isActive() {
            blockEntity.loadRecipe();
            if (blockEntity.recipe == null) return false;
            if (blockEntity.ingredients == null) return false;
            return (index >= 0 && index < blockEntity.ingredients.size());
        }
    }

    public static class ResultSlot extends Slot {

        public final Player player;
        private int removeCount;

        public ResultSlot(Player player, Container container, int index, int x, int y) {
            super(container, index, x, y);
            this.player = player;
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack remove(int count) {
            if (hasItem()) {
                removeCount += Math.min(count, getItem().getCount());
            }

            return super.remove(count);
        }

        @Override
        public void onTake(Player player, ItemStack stack) {
            stack.onCraftedBy(player.level(), player, removeCount);
            removeCount = 0;

            if (container instanceof ChampiumForgeBlockEntity blockEntity
                    && player.level() instanceof  ServerLevel serverLevel
                    && blockEntity.recipe != null) {
                blockEntity.popExperience(serverLevel, player.position());
                player.awardRecipes(List.of(blockEntity.recipe));
                player.triggerRecipeCrafted(blockEntity.recipe, List.of(stack));
            }

            super.onTake(player, stack);
        }
    }
}
