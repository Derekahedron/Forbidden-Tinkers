package derekahedron.forbiddentinkers.block.entity;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.ChampiumForgeBlock;
import derekahedron.forbiddentinkers.inventory.ChampiumForgeMenu;
import derekahedron.forbiddentinkers.network.ChampiumSmokePacket;
import derekahedron.forbiddentinkers.network.FTPacketHandler;
import derekahedron.forbiddentinkers.particle.FTParticleTypes;
import derekahedron.forbiddentinkers.recipe.ChampiumForgeRecipe;
import derekahedron.forbiddentinkers.recipe.FTRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ChampiumForgeBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final Component TITLE = Component.translatable("container." + ForbiddenTinkers.MOD_ID + ".champium_forge");
    public static final int COUNT_INDEX = 0;
    public static final int USES_INDEX = 1;
    public static final int COOLDOWN_INDEX = 2;
    public static final int NUM_INPUT_SLOTS = 9;
    public static final String RECIPE_SEED_KEY = "RecipeSeed";
    public static final String RECIPE_KEY = "Recipe";
    public static final String COUNT_KEY = "Count";
    public static final String USES_KEY = "Uses";
    public static final String MAX_USES_KEY = "MaxUses";
    public static final String COOLDOWN_KEY = "Cooldown";
    public static final String BURN_COOLDOWN_KEY = "BurnCooldown";
    public static final String SMOKE_COOLDOWN_KEY = "SmokeCooldown";
    public static final int COUNT_DEFAULT = 0;
    public static final int USES_DEFAULT = 0;
    @Nullable
    public static final Integer MAX_USES_DEFAULT = null;
    public static final int COOLDOWN_DEFAULT = 0;
    public static final int COOLDOWN = 2;
    public static final int BURN_COOLDOWN_DEFAULT = 0;
    public static final int BURN_COOLDOWN = 20;
    public static final int SMOKE_COOLDOWN_DEFAULT = 0;
    public static final int SMOKE_COOLDOWN = 20;
    @Nullable
    public Long recipeSeed = null;
    @Nullable
    public ResourceLocation recipeId = null;
    @Nullable
    public ChampiumForgeRecipe recipe = null;
    public int count = 0;
    public int uses = 0;
    @Nullable
    public Integer maxUses;
    public int cooldown = 0;
    public int burnCooldown = 0;
    public int smokeCooldown = 0;
    public NonNullList<ItemStack> inputs = NonNullList.withSize(NUM_INPUT_SLOTS + 1, ItemStack.EMPTY);
    public ItemStack result = ItemStack.EMPTY;
    public final ContainerData data;
    public LazyOptional<? extends IItemHandler>[] handlers;

    public ChampiumForgeBlockEntity(BlockPos pos, BlockState state) {
        super(FTBlockEntityTypes.CHAMPIUM_FORGE.get(), pos, state);
        data = new Data();
        handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }

    @Override
    protected Component getDefaultName() {
        return TITLE;
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new ChampiumForgeMenu(i, inventory, this);
    }

    @Override
    public int getContainerSize() {
        return inputs.size();
    }

    @Override
    public boolean isEmpty() {
        return inputs.stream().allMatch(ItemStack::isEmpty) ;
    }

    @Override
    public ItemStack getItem(int i) {
        if (i < getContainerSize() && i >= 0) {
            return inputs.get(i);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeItem(int i, int count) {
        if (count <= 0) return ItemStack.EMPTY;

        if (i < getContainerSize() && i >= 0) {
            return ContainerHelper.removeItem(inputs, i, count);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public ItemStack removeItemNoUpdate(int i) {
        if (i < getContainerSize() && i >= 0) {
            return ContainerHelper.takeItem(inputs, i);
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int i, ItemStack stack) {
        if (i < getContainerSize() && i >= 0) {
            inputs.set(i, stack);
        } else {
            return;
        }

        if (!stack.isEmpty() && stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }

        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        inputs.clear();
    }

    public int getRedstoneSignal() {
        int totalSlots = 0;
        int filledSlots = 0;
        float fill = 0;

        ItemStack resultStack = getResultStack();
        if (!resultStack.isEmpty()) {
            fill += (float) resultStack.getCount() / Math.min(getMaxStackSize(), resultStack.getMaxStackSize());
            filledSlots++;
        }
        totalSlots++;

        loadRecipe();
        if (recipe != null) {
            for (int i = 0; i < recipe.ingredients.size(); i++) {
                ItemStack inputStack = getItem(i);
                if (!inputStack.isEmpty()) {
                    fill += (float) inputStack.getCount() / Math.min(getMaxStackSize(), inputStack.getMaxStackSize());
                    filledSlots++;
                }
                totalSlots++;
            }
        }

        fill /= totalSlots;
        return Mth.floor(fill * 14.0F) + (filledSlots > 0 ? 1 : 0);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, ChampiumForgeBlockEntity entity) {
        boolean changed = false;
        ChampiumForgeBlock.State oldState = entity.getState();

        if (entity.cooldown > 0) entity.cooldown--;
        if (entity.burnCooldown > 0) entity.burnCooldown--;
        if (entity.smokeCooldown > 0) entity.smokeCooldown--;

        if (entity.cooldown <= 0) {
            if (entity.hasUsesRemaining()) {
                entity.loadRecipe();
                if (entity.recipe == null) return;

                if (entity.count >= entity.recipe.count) {
                    ItemStack recipeResult = entity.recipe.assemble(entity, level.registryAccess());
                    if (!recipeResult.isEmpty()) {
                        int maxStackSize = entity.getMaxResultStackSize(recipeResult);
                        int uses = (maxStackSize - entity.getResultStack().getCount()) / recipeResult.getCount();

                        if (entity.recipe.count > 0) {
                            uses = Math.min(uses, entity.count / entity.recipe.count);
                        }

                        if (entity.maxUses != null) {
                            uses = Math.min(uses, entity.maxUses - entity.uses);
                        }

                        if (uses > 0) {
                            entity.uses += uses;
                            entity.count -= entity.recipe.count * uses;
                            recipeResult.setCount(recipeResult.getCount() * uses);
                            if (entity.getResultStack().isEmpty()) {
                                entity.setResultStack(recipeResult);
                            } else {
                                entity.getResultStack().grow(recipeResult.getCount());
                            }
                            changed = true;

                            if (!entity.hasUsesRemaining()) {
                                entity.burnCooldown = BURN_COOLDOWN_DEFAULT;
                                entity.smokeCooldown = SMOKE_COOLDOWN_DEFAULT;
                            }
                        }
                    }
                }

                if (entity.hasUsesRemaining() && entity.recipe.matches(entity, level)) {
                    ItemStack recipeResult = entity.recipe.assemble(entity, level.registryAccess());

                    if (entity.canAcceptResult(recipeResult)) {
                        for (int i = 0; i < entity.recipe.getIngredients().size(); i++) {
                            ContainerHelper.removeItem(entity.inputs, i, 1);
                        }
                        entity.cooldown = COOLDOWN;
                        entity.burnCooldown = BURN_COOLDOWN;
                        entity.count++;

                        changed = true;
                    }
                }
            }
        }

        if (entity.burnCooldown > 0 && entity.smokeCooldown <= 0) {
            entity.smokeCooldown = SMOKE_COOLDOWN;
            createChampiumSmoke(level, pos);
        }

        ChampiumForgeBlock.State newState = entity.getState();
        if (oldState != newState) {
            changed = true;
            blockState = blockState.setValue(ChampiumForgeBlock.STATE, newState);
            level.setBlock(pos, blockState, 3);
        }

        if (changed) {
            entity.setChanged();
        }
    }

    public static void createChampiumSmoke(Level level, BlockPos pos) {
        if (level instanceof ServerLevel serverLevel) {
            FTPacketHandler.INSTANCE.send(PacketDistributor.NEAR.with(() -> new PacketDistributor.TargetPoint(
                    null,
                    pos.getX(), pos.getY(), pos.getZ(),
                    64.0D,
                    serverLevel.dimension()
            )), new ChampiumSmokePacket(pos));
        } else {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                int numParticles = 1 + level.random.nextInt(3);
                for (int i = 0; i < numParticles; i++) {
                    ChampiumForgeBlock.addSmokeParticle(
                            level,
                            pos,
                            FTParticleTypes.CHAMPIUM_FORGE_SMOKE.get(),
                            direction);
                }
            }
        }
    }

    public ChampiumForgeBlock.State getState() {
        if (!hasUsesRemaining()) return ChampiumForgeBlock.State.BROKEN;
        else if (burnCooldown > 0) return ChampiumForgeBlock.State.ACTIVE;
        else return ChampiumForgeBlock.State.INACTIVE;
    }

    public boolean hasUsesRemaining() {
        return maxUses == null || uses < maxUses;
    }

    public int getMaxResultStackSize(ItemStack recipeResult) {
        if (!getResultStack().isEmpty() && !ItemStack.isSameItemSameTags(getResultStack(), recipeResult)) return 0;
        return Math.min(getMaxStackSize(), recipeResult.getMaxStackSize());
    }

    public boolean canAcceptResult(ItemStack recipeResult) {
        return getResultStack().getCount() + recipeResult.getCount() <= getMaxResultStackSize(recipeResult);
    }

    public ItemStack getResultStack() {
        return getItem(NUM_INPUT_SLOTS);
    }

    public void setResultStack(ItemStack stack) {
        inputs.set(NUM_INPUT_SLOTS, stack);
    }

    public void load(CompoundTag tag) {
        super.load(tag);

        recipeId = null;
        recipeSeed = null;
        if (tag.contains(RECIPE_KEY)) {
            recipeId = ResourceLocation.tryParse(tag.getString(RECIPE_KEY));
        } else if (tag.contains(RECIPE_SEED_KEY)) {
            recipeSeed = tag.getLong(RECIPE_SEED_KEY);
        }

        if (recipe != null && !recipe.getId().equals(recipeId)) {
            recipe = null;
        }

        count = tag.contains(COUNT_KEY) ? tag.getInt(COUNT_KEY) : COUNT_DEFAULT;
        uses = tag.contains(USES_KEY) ? tag.getInt(USES_KEY) : USES_DEFAULT;
        maxUses = tag.contains(MAX_USES_KEY) ? Integer.valueOf(tag.getInt(MAX_USES_KEY)) : MAX_USES_DEFAULT;
        cooldown = tag.contains(COOLDOWN_KEY) ? tag.getInt(COOLDOWN_KEY) : COOLDOWN_DEFAULT;
        burnCooldown = tag.contains(BURN_COOLDOWN_KEY) ? tag.getInt(BURN_COOLDOWN_KEY) : BURN_COOLDOWN_DEFAULT;
        smokeCooldown = tag.contains(SMOKE_COOLDOWN_KEY) ? tag.getInt(SMOKE_COOLDOWN_KEY) : SMOKE_COOLDOWN_DEFAULT;
        ContainerHelper.loadAllItems(tag, inputs);
    }

    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        if (recipeId != null) {
            tag.putString(RECIPE_KEY, recipeId.toString());
        } else if (recipeSeed != null) {
            tag.putLong(RECIPE_SEED_KEY, recipeSeed);
        }

        if (count != COUNT_DEFAULT) tag.putInt(COUNT_KEY, count);
        if (uses != USES_DEFAULT) tag.putInt(USES_KEY, uses);
        if (maxUses != MAX_USES_DEFAULT) tag.putInt(MAX_USES_KEY, maxUses);
        if (cooldown != COOLDOWN_DEFAULT) tag.putInt(COOLDOWN_KEY, cooldown);
        if (burnCooldown != BURN_COOLDOWN_DEFAULT) tag.putInt(BURN_COOLDOWN_KEY, burnCooldown);
        if (smokeCooldown != SMOKE_COOLDOWN_DEFAULT) tag.putInt(SMOKE_COOLDOWN_KEY, smokeCooldown);

        ContainerHelper.saveAllItems(tag, inputs, false);
    }

    public void loadRecipe() {
        if (recipeId == null) return;
        if (recipe != null) return;
        if (level == null) return;

        recipe = (ChampiumForgeRecipe) level.getRecipeManager().byKey(recipeId)
                .filter(recipe -> recipe instanceof ChampiumForgeRecipe)
                .orElse(null);
    }

    public void refreshRecipe() {
        loadRecipe();
        if (recipe != null) return;
        if (level == null || level.isClientSide()) return;

        // Sort recipes by id to ensure consistent order
        List<ChampiumForgeRecipe> recipes = level.getRecipeManager()
                .getAllRecipesFor(FTRecipeTypes.CHAMPIUM_FORGE.get())
                .stream()
                .filter(recipe -> // Filter out recipes without ingredients
                        !recipe.ingredients.isEmpty() && recipe.ingredients.stream()
                                .allMatch(ChampiumForgeBlockEntity::hasItems))
                .sorted(Comparator.comparing(ChampiumForgeRecipe::getId))
                .toList();

        if (!recipes.isEmpty()) {
            RandomSource random = recipeSeed != null ? RandomSource.create(recipeSeed) : level.getRandom();

            int totalWeight = 0;
            for (ChampiumForgeRecipe recipe : recipes) {
                totalWeight += recipe.weight;
            }

            // Get weighted recipe
            int weight = random.nextInt(totalWeight);
            for (ChampiumForgeRecipe recipe : recipes) {
                if (weight < recipe.weight) {
                    this.recipe = recipe;
                    break;
                } else {
                    weight -= recipe.weight;
                }
            }
            Objects.requireNonNull(recipe);

            recipeId = recipe.getId();
            uses = 0;
            count = 0;
            maxUses = recipe.maxUses == null ? null : recipe.maxUses.sample(random);

            ChampiumForgeBlock.State state = getState();
            BlockState blockState = getBlockState();
            if (state != blockState.getValue(ChampiumForgeBlock.STATE)) {
                blockState = blockState.setValue(ChampiumForgeBlock.STATE, state);
                level.setBlock(getBlockPos(), blockState, 3);
            }
            setChanged();
        } else if (recipeId != null) {
            recipeId = null;
            uses = 0;
            count = 0;
            maxUses = null;
            setChanged();
        }
    }

    @Override
    public int[] getSlotsForFace(Direction direction) {
        refreshRecipe();
        if (recipe == null) return new int[0];

        if (direction == Direction.DOWN) {
            return new int[]{NUM_INPUT_SLOTS};
        } else if (direction == Direction.UP) {
            int[] slots = new int[NUM_INPUT_SLOTS];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
            return slots;
        } else {
            int[] slots = new int[getContainerSize()];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = i;
            }
            return slots;
        }
    }

    @Override
    public boolean canPlaceItem(int slotIndex, ItemStack stack) {
        refreshRecipe();
        if (recipe == null) return false;
        if (!hasUsesRemaining()) return false;
        if (slotIndex < 0 || slotIndex >= getContainerSize()) return false;
        if (slotIndex == NUM_INPUT_SLOTS) return false;
        if (slotIndex >= recipe.ingredients.size()) return false;
        if (!recipe.ingredients.get(slotIndex).test(stack)) return false;

        // Don't accept if there is a valid stack that has a lesser count.
        for (int i = 0; i < recipe.ingredients.size(); i++) {
            if (i ==  slotIndex) continue;
            if (inputs.get(i).getCount() < inputs.get(slotIndex).getCount() && recipe.ingredients.get(i).test(stack)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slotIndex, ItemStack stack, @Nullable Direction direction) {
        return direction != Direction.DOWN && canPlaceItem(slotIndex, stack);
    }

    @Override
    public boolean canTakeItemThroughFace(int i, ItemStack stack, Direction direction) {
        refreshRecipe();
        if (recipe == null) return false;
        if (i < 0 || i >= getContainerSize()) return false;
        if (i == NUM_INPUT_SLOTS) return direction != Direction.UP;
        if (i >= recipe.ingredients.size()) return false;

        return direction != Direction.DOWN;
    }

    public class Data implements ContainerData {

        @Override
        public int get(int index) {
            return switch (index) {
                case COUNT_INDEX -> count;
                case USES_INDEX -> uses;
                case COOLDOWN_INDEX -> cooldown;
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case COUNT_INDEX -> count = value;
                case USES_INDEX -> uses = value;
                case COOLDOWN_INDEX -> cooldown = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == ForgeCapabilities.ITEM_HANDLER && facing != null && !remove) {
            return switch (facing) {
                case UP -> handlers[0].cast();
                case DOWN -> handlers[1].cast();
                default -> handlers[2].cast();
            };
        } else {
            return super.getCapability(capability, facing);
        }
    }

    public void invalidateCaps() {
        super.invalidateCaps();

        for (LazyOptional<? extends IItemHandler> handler : handlers) {
            handler.invalidate();
        }
    }

    public void reviveCaps() {
        super.reviveCaps();
        handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }

    public static boolean hasItems(Ingredient ingredient) {
        ItemStack[] items =  ingredient.getItems();
        if (items.length == 0) return false;
        else if (items.length > 1) return true;
        return items[0].getItem() != Items.BARRIER;
    }
}
