package kz.chesschicken.backpacksfabric;

import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import kz.chesschicken.backpacksfabric.item.ItemBackpack;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class BackpacksListener {
    public static TemplateItemBase BACKPACK;

    @Entrypoint.ModID
    public static ModID modID = Null.get();

    @Entrypoint.Logger("BackpacksFabric|Mod")
    public static Logger logger = Null.get();

    @SuppressWarnings("unused")
    @EventListener
    public void postInitMessage(PostInitEvent event) {
        logger.info("BackpacksFabric has been initializing...");
    }

    @SuppressWarnings("unused")
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        BACKPACK = new ItemBackpack(Identifier.of(modID, "backpack")).setTranslationKey(modID, "backpack");
    }

    @SuppressWarnings("unused")
    @EventListener
    public void registerGuiHandler(GuiHandlerRegistryEvent event) {
        event.registry.register(Identifier.of(modID, "backpacks_inventory"), BiTuple.of(ClientOpener::openBackpack, EntityBackpack::new));
    }

}
