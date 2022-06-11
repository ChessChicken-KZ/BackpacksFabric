package kz.chesschicken.backpacksfabric;

import kz.chesschicken.backpacksfabric.client.GuiBackpack;
import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.packet.Message;

public class ClientOpener {

    @SuppressWarnings({"deprecation", "unused"})
    public static Minecraft getGame() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    public static ScreenBase openBackpack(PlayerBase playerBase, InventoryBase inventoryBase, Message message) {
        return new GuiBackpack(playerBase.inventory, (EntityBackpack) inventoryBase);
    }

}
