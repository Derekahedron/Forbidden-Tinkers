package derekahedron.forbiddentinkers.tinkers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.GsonHelper;
import slimeknights.mantle.data.JsonCodec;
import slimeknights.mantle.data.loadable.primitive.IntLoadable;
import slimeknights.mantle.util.typed.TypedMap;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;

import java.util.Map;
import java.util.Optional;

public record DivineSlots(ModifierId modifierId, int minLevel, int maxLevel, Optional<SlotType.SlotCount> slots) {

    public static final Codec<DivineSlots> CODEC =
            RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.xmap(
                            ModifierId::tryParse,
                            ModifierId::toString
                    )
                            .fieldOf("modifier")
                            .forGetter(DivineSlots::modifierId),
                    Codec.INT
                            .fieldOf("min_level")
                            .forGetter(DivineSlots::minLevel),
                    Codec.INT
                            .fieldOf("max_level")
                            .forGetter(DivineSlots::maxLevel),
                    new JsonCodec<Optional<SlotType.SlotCount>>() {

                        // Quick inline codec for Slot Types that does not include slot types which do not exist.
                        @Override
                        public Optional<SlotType.SlotCount> deserialize(JsonElement jsonElement, DynamicOps<?> dynamicOps) {
                            JsonObject json = GsonHelper.convertToJsonObject(jsonElement, "codec");
                            if (json.entrySet().isEmpty()) {
                                return Optional.empty();
                            } else if (json.entrySet().size() != 1) {
                                throw new JsonSyntaxException("Cannot set multiple slot types");
                            } else {
                                Map.Entry<String, JsonElement> entry = json.entrySet().iterator().next();
                                String typeString = entry.getKey();
                                if (!SlotType.isValidName(typeString)) {
                                    throw new JsonSyntaxException("Invalid slot type name '" + typeString + "'");
                                } else {
                                    SlotType slotType = SlotType.getIfPresent(typeString);
                                    if (slotType == null) {
                                        return Optional.empty();
                                    }

                                    int slots = IntLoadable.FROM_ONE.convert(entry.getValue(), "count", TypedMap.EMPTY);
                                    return Optional.of(new SlotType.SlotCount(slotType, slots));
                                }
                            }
                        }

                        @Override
                        public JsonElement serialize(Optional<SlotType.SlotCount> slots, DynamicOps<?> dynamicOps) {
                            JsonObject json = new JsonObject();
                            slots.ifPresent(slotCount -> json.addProperty(slotCount.type().getName(), slotCount.count()));
                            return json;
                        }
                    }
                            .fieldOf("slots")
                            .forGetter(DivineSlots::slots)
            ).apply(instance, DivineSlots::new));
}
