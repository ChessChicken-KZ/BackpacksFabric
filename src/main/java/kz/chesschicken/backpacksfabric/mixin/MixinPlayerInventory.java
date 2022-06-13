package kz.chesschicken.backpacksfabric.mixin;

import kz.chesschicken.backpacksfabric.ClientOpener;
import kz.chesschicken.backpacksfabric.client.GuiBackpack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory {

    @Shadow public ItemInstance[] main;

    @Shadow public abstract ItemInstance getHeldItem();

    @Inject(method = "takeInventoryItem", at = @At("HEAD"), cancellable = true)
    private void cancelIfBackpack(int i, int j, CallbackInfoReturnable<ItemInstance> cir) {
        if (i <= this.main.length && i >= 0 && ClientOpener.getGame().currentScreen instanceof GuiBackpack) {
            if (this.main[i] != null && getHeldItem() != null) {
                if(ItemInstance.areStacksIdentical(this.main[i], getHeldItem())) {
                    cir.setReturnValue(null);
                    cir.cancel();
                }
            }
        }

    }
}
