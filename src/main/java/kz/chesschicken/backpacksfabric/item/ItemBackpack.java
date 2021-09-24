package kz.chesschicken.backpacksfabric.item;

import kz.chesschicken.backpacksfabric.BackpacksListener;
import net.minecraft.container.Chest;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.item.nbt.HasItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemWithEntity;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.function.Function;
import java.util.function.Supplier;


public class ItemBackpack extends TemplateItemBase implements ItemWithEntity {
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
        if(item != null && player.inventory.getHeldItem().itemId == BackpacksListener.BACKPACK.id)
            GuiHelper.openGUI(player, Identifier.of(BackpacksListener.modID, "backpacks_inventory"), ((EntityBackpack) HasItemEntity.cast(item).getItemEntity()) ,new Chest(player.inventory, ((EntityBackpack) HasItemEntity.cast(item).getItemEntity())) );
        return item;
    }
}
