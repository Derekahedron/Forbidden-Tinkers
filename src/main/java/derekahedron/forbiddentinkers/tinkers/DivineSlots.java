package derekahedron.forbiddentinkers.tinkers;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import slimeknights.mantle.data.loadable.LoadableCodec;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.SlotType;

public record DivineSlots(ModifierId modifierId, int minLevel, int maxLevel, SlotType.SlotCount slots) {

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
                    new LoadableCodec<>(SlotType.SlotCount.LOADABLE)
                            .fieldOf("slots")
                            .forGetter(DivineSlots::slots)
            ).apply(instance, DivineSlots::new));
}
