package kz.chesschicken.backpacksfabric.item;

import kz.chesschicken.backpacksfabric.BackpacksListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

import java.util.Arrays;

public class EntityBackpack implements InventoryBase {

    public final ItemInstance[] INVENTORY;

    public EntityBackpack() {
        this.INVENTORY = new ItemInstance[27];
        BackpacksListener.logger.warn("Dangerous constructor has been used in runtime!");
    }

    public EntityBackpack(CompoundTag compoundTag) {
        this.INVENTORY = new ItemInstance[27];
        ListTag items = compoundTag.getListTag("backpacksfabric.Items");

        for(int q = 0; q < items.size(); q++) {
            CompoundTag tag = (CompoundTag)items.get(q);
            int i = tag.getByte("Slot") & 255;
            if (i < INVENTORY.length)
                INVENTORY[i] = new ItemInstance(tag);
        }
    }

    public void writeToNBT(CompoundTag tag) {
        ListTag listTag = new ListTag();
        for(int q = 0; q < INVENTORY.length; ++q) {
            if (INVENTORY[q] != null) {
                CompoundTag item = new CompoundTag();
                item.put("Slot", (byte)q);
                INVENTORY[q].toTag(item);
                listTag.add(item);
            }
        }
        tag.put("backpacksfabric.Items", listTag);
    }

    @Override
    public int getInventorySize() {
        return INVENTORY.length;
    }

    @Override
    public ItemInstance getInventoryItem(int index) {
        return INVENTORY[index];
    }

    @Override
    public ItemInstance takeInventoryItem(int index, int count) {
        if (INVENTORY[index] != null) {
            ItemInstance send;
            if (INVENTORY[index].count <= count) {
                send = INVENTORY[index];
                INVENTORY[index] = null;
            } else {
                send = INVENTORY[index].split(count);
                if (INVENTORY[index].count == 0)
                    INVENTORY[index] = null;
            }
            this.markDirty();
            return send;
        } else return null;
    }

    @Override
    public void setInventoryItem(int slot, ItemInstance stack) {
        INVENTORY[slot] = stack;
        if (INVENTORY[slot] != null && INVENTORY[slot].count > this.getMaxItemCount())
            INVENTORY[slot].count = this.getMaxItemCount();
        this.markDirty();
    }

    @Override
    public String getContainerName() {
        return "Backpack";
    }

    @Override
    public int getMaxItemCount() {
        return 64;
    }

    @Override
    public void markDirty() {}

    @Override
    public boolean canPlayerUse(PlayerBase player) {
        return player.inventory.getHeldItem() != null && player.inventory.getHeldItem().itemId == BackpacksListener.BACKPACK.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        return Arrays.equals(INVENTORY, ((EntityBackpack) o).INVENTORY);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(INVENTORY);
    }
}
