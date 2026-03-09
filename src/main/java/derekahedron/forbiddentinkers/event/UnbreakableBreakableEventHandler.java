package derekahedron.forbiddentinkers.event;

import derekahedron.forbiddentinkers.ForbiddenTinkers;
import derekahedron.forbiddentinkers.block.FTBlockTags;
import derekahedron.forbiddentinkers.item.FTTiers;
import derekahedron.forbiddentinkers.tinkers.materials.FTMaterialTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TieredItem;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.MaterialRegistry;
import slimeknights.tconstruct.library.materials.definition.MaterialId;
import slimeknights.tconstruct.library.tools.definition.ToolDefinition;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IToolPart;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import java.util.List;

@Mod.EventBusSubscriber(modid = ForbiddenTinkers.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class UnbreakableBreakableEventHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    static void canHarvestUnbreakable(PlayerEvent.HarvestCheck event) {
        if (event.canHarvest() && event.getTargetBlock().is(FTBlockTags.NEEDS_CHAMPIUM_TOOL)) {
            ItemStack stack = event.getEntity().getItemInHand(InteractionHand.MAIN_HAND);

            if (stack.is(TinkerTags.Items.MODIFIABLE)) {
                ToolStack tool = ToolStack.from(stack);
                ToolDefinition toolDefinition = tool.getDefinition();
                List<IToolPart> parts = toolDefinition.getData().getHook(ToolHooks.TOOL_PARTS).getParts(toolDefinition);
                for (int i = 0; i < parts.size(); i++) {
                    MaterialId material = tool.getMaterial(i).getId();
                    if (parts.get(i).getStatType() == HeadMaterialStats.ID
                            && MaterialRegistry.getInstance().isInTag(material, FTMaterialTags.CHAMPIUM_TIER)) {
                        return;
                    }
                }

            } else if (stack.getItem() instanceof TieredItem tieredItem) {
                if (tieredItem.getTier() == FTTiers.CHAMPIUM) {
                    return;
                }
            }
            event.setCanHarvest(false);
        }
    }
}
