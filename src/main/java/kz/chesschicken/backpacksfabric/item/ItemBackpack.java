package kz.chesschicken.backpacksfabric.item;

import kz.chesschicken.backpacksfabric.ArrayUtils;
import kz.chesschicken.backpacksfabric.BackpacksListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.container.Chest;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.nbt.StationNBT;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class ItemBackpack extends TemplateItemBase implements CustomTooltipProvider {

    public ItemBackpack(Identifier identifier) {
        super(identifier);
        this.setHasSubItems(true);
        this.setMaxStackSize(1);
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        return item.getType().getTranslationKey() + item.getDamage();
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, PlayerBase player) {
        if(!level.isServerSide) {
            if(item != null && player.inventory.getHeldItem().itemId == BackpacksListener.BACKPACK.id) {
                BackpacksListener.logger.info("Opening backpack for " + player.name + " at " + FabricLoader.getInstance().getEnvironmentType().name() + ".");
                EntityBackpack backpack = new EntityBackpack(StationNBT.cast(item).getStationNBT());
                GuiHelper.openGUI(player, Identifier.of("backpacksfabric:backpacks_inventory"), backpack, new Chest(player.inventory, backpack));
            }
        }
        return item;
    }

    int __prevHash;
    String[] __cached;

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        EntityBackpack backpack = new EntityBackpack(StationNBT.cast(itemInstance).getStationNBT());
        if(__prevHash != backpack.hashCode()) {
            __prevHash = backpack.hashCode();
            __cached = ArrayUtils.combineTwoArrays(new String[] { originalTooltip, "Content:" }, ArrayUtils.abuseIdentifiers(backpack.INVENTORY));
        }
        return __cached;
    }
}
