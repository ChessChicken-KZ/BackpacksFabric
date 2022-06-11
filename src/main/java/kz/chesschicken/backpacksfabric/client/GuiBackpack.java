package kz.chesschicken.backpacksfabric.client;

import kz.chesschicken.backpacksfabric.BackpacksListener;
import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import net.minecraft.container.Chest;
import net.minecraft.entity.player.PlayerInventory;
import net.modificationstation.stationapi.api.item.nbt.StationNBT;
import org.lwjgl.opengl.GL11;

public class GuiBackpack extends net.minecraft.client.gui.screen.container.ContainerBase {
    protected final EntityBackpack backpack;

    public GuiBackpack(PlayerInventory arg, EntityBackpack arg1) {
        super(new Chest(arg, arg1));
        this.backpack = arg1;
    }

    /**
     * Should work with client side.
     */
    @Override
    public void onClose() {
        if(minecraft.player.getHeldItem() != null && minecraft.player.getHeldItem().itemId == BackpacksListener.BACKPACK.id) {
            backpack.writeToNBT(StationNBT.cast(minecraft.player.getHeldItem()).getStationNBT());
        }
    }

    @Override
    protected void renderForeground() {
        this.textManager.drawText("Backpack", 8, 6, 4210752);
        this.textManager.drawText("Inventory", 8, this.containerHeight - 94, 4210752);
    }

    @Override
    protected void renderContainerBackground(float tickDelta) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.textureManager.bindTexture(this.minecraft.textureManager.getTextureId("/gui/container.png"));
        this.blit((this.width - this.containerWidth) / 2, (this.height - this.containerHeight) / 2, 0, 0, this.containerWidth, 71);
        this.blit((this.width - this.containerWidth) / 2, (this.height - this.containerHeight) / 2 + 71, 0, 126, this.containerWidth, 96);
    }
}
