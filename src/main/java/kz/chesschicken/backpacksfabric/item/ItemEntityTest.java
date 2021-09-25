package kz.chesschicken.backpacksfabric.item;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.item.nbt.HasItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemEntity;
import net.modificationstation.stationapi.api.item.nbt.ItemWithEntity;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.function.Function;
import java.util.function.Supplier;

public class ItemEntityTest  extends TemplateItemBase implements ItemWithEntity {
    public ItemEntityTest(Identifier identifier) {
        super(identifier);
    }

    @Override
    public Supplier<ItemEntity> getItemEntityFactory() {
        return EntityItemTest::new;
    }

    @Override
    public Function<CompoundTag, ItemEntity> getItemEntityNBTFactory() {
        return EntityItemTest::new;
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, PlayerBase player) {
        System.out.println("Player: " + player.name + " , " + ((EntityItemTest) HasItemEntity.cast(item).getItemEntity()).getValue());
        return item;
    }
}
