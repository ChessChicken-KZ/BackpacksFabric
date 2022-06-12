package kz.chesschicken.backpacksfabric;

import kz.chesschicken.backpacksfabric.client.GuiBackpack;
import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.packet.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ClientOpener {

    @SuppressWarnings({"deprecation", "unused"})
    public static @NotNull Minecraft getGame() {
        return (Minecraft) FabricLoader.getInstance().getGameInstance();
    }

    public static @NotNull ScreenBase openBackpack(@NotNull PlayerBase playerBase, @NotNull InventoryBase inventoryBase, @Nullable Message message) {
        return new GuiBackpack(playerBase.inventory, (EntityBackpack) inventoryBase);
    }

}
