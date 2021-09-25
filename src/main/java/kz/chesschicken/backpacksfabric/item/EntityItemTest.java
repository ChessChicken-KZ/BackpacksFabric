package kz.chesschicken.backpacksfabric.item;

import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.item.nbt.ItemEntity;

import java.util.Random;

public class EntityItemTest implements ItemEntity {

    private final int value;

    public EntityItemTest() {
        this.value = (new Random()).nextInt(12345);
    }

    public EntityItemTest(int i) {
        this.value = i;
    }

    public EntityItemTest(CompoundTag tag) {
        this.value = tag.getInt("backpacksfabric.intTT");
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public ItemEntity copy() {
        return new EntityItemTest(value);
    }

    @Override
    public void writeToNBT(CompoundTag tag) {
        tag.put("backpacksfabric.intTT", this.value);
    }
}
