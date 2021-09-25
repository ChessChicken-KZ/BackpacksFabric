package kz.chesschicken.backpacksfabric.item;

import kz.chesschicken.backpacksfabric.ArrayUtils;
import kz.chesschicken.backpacksfabric.BackpacksListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.container.Chest;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.nbt.HasItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemWithEntity;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public class ItemBackpack extends TemplateItemBase implements ItemWithEntity, CustomTooltipProvider {
    public ItemBackpack(Identifier identifier) {
        super(identifier);
        this.setHasSubItems(true);
        this.setMaxStackSize(1);
    }

    @Override
    public int getTexturePosition(int damage) {
        return BackpacksListener.COLOURS[damage];
    }

    @Override
    public String getTranslationKey(ItemInstance item) {
        return item.getType().getTranslationKey() + item.getDamage();
    }

    @Override
    public Supplier<ItemEntity> getItemEntityFactory() {
        return EntityBackpack::new;
    }

    @Override
    public Function<CompoundTag, ItemEntity> getItemEntityNBTFactory() {
        return EntityBackpack::new;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, PlayerBase player) {
        if(!level.isClient) {
            if(item != null && player.inventory.getHeldItem().itemId == BackpacksListener.BACKPACK.id) {
                System.out.println("Opening backpack for " + player.name + " at " + FabricLoader.getInstance().getEnvironmentType().name() + ".");
                EntityBackpack backpack = ((EntityBackpack) HasItemEntity.cast(item).getItemEntity());
                GuiHelper.openGUI(player, Identifier.of("backpacksfabric:backpacks_inventory"), backpack, new Chest(player.inventory, backpack));
            }
        }
        return item;
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        List<String> sending = new ArrayList<>();
        EntityBackpack backpack = ((EntityBackpack) HasItemEntity.cast(itemInstance).getItemEntity());
        for(byte b = 0; b < backpack.getInventorySize(); b++) {
            if(backpack.getInventoryItem(b) != null) {
                sending.add(TranslationStorage.getInstance().method_995((backpack.getInventoryItem(b).getTranslationKey())) + " x" + backpack.getInventoryItem(b).count);
            }
        }

        return ArrayUtils.combineTwoArrays(new String[] {
                originalTooltip
        }, sending.toArray(new String[0]));
    }
}
