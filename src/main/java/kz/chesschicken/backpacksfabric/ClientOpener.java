package kz.chesschicken.backpacksfabric;

import kz.chesschicken.backpacksfabric.client.GuiBackpack;
import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.item.nbt.HasItemEntity;
import net.modificationstation.stationapi.api.packet.Message;

public class ClientOpener {

    public static void openBackpackInventory(PlayerBase playerBase, ItemInstance itemInstance) {
        ((Minecraft) FabricLoader.getInstance().getGameInstance()).openScreen(new GuiBackpack(playerBase.inventory, ((EntityBackpack) HasItemEntity.cast(itemInstance).getItemEntity())));
    }

    public static ScreenBase openBackpack(PlayerBase playerBase, InventoryBase inventoryBase, Message message) {
        return new GuiBackpack(playerBase.inventory, (EntityBackpack) inventoryBase);
    }

}
