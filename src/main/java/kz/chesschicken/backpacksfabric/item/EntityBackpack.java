package kz.chesschicken.backpacksfabric.item;

import kz.chesschicken.backpacksfabric.BackpacksListener;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import net.modificationstation.stationapi.api.item.nbt.ItemEntity;

public class EntityBackpack implements ItemEntity, InventoryBase {

    private final ItemInstance[] INVENTORY;

    public EntityBackpack() {
        this.INVENTORY = new ItemInstance[27];
    }

    public EntityBackpack(ItemInstance[] toCopy) {
        this.INVENTORY = new ItemInstance[toCopy.length];

        for(int i = 0; i < toCopy.length; i++) {
            if(toCopy[i] != null)
                this.INVENTORY[i] = toCopy[i].copy();
        }
    }

    public EntityBackpack(CompoundTag compoundTag) {
        ListTag items = compoundTag.getListTag("Items");
        this.INVENTORY = new ItemInstance[36];

        for(int q = 0; q < items.size(); q++) {
            CompoundTag tag = (CompoundTag)items.get(q);
            int i = tag.getByte("Slot") & 255;
            if (i < this.INVENTORY.length)
                this.INVENTORY[i] = new ItemInstance(tag);
        }
    }

    @Override
    public ItemEntity copy() {
        return new EntityBackpack(INVENTORY);
    }

    @Override
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

        tag.put("Items", listTag);
    }

    @Override
    public int getInventorySize() {
        return 36;
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
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerBase player) {
        return player.inventory.getHeldItem().itemId == BackpacksListener.BACKPACK.id;
    }
}
