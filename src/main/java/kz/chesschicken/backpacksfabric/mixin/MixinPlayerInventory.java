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

/*
 * More like a client-sided check.
 */
@Mixin(PlayerInventory.class)
public abstract class MixinPlayerInventory {
    @Shadow public ItemInstance[] main;

    @Shadow public ItemInstance[] armour;

    @Shadow public abstract ItemInstance getHeldItem();

    @Inject(method = "takeInventoryItem", at = @At("HEAD"), cancellable = true)
    private void cancelIfBackpack(int i, int j, CallbackInfoReturnable<ItemInstance> cir) {
        ItemInstance[] var3 = this.main;
        if (i >= this.main.length) {
            var3 = this.armour;
            i -= this.main.length;
        }

        if (var3[i] != null && getHeldItem() != null && ClientOpener.getGame().currentScreen instanceof GuiBackpack) {
            if(ItemInstance.areStacksIdentical(var3[i], getHeldItem())) {
                cir.setReturnValue(null);
                cir.cancel();
            }
        }
    }
}
