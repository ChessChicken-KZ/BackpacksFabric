package kz.chesschicken.backpacksfabric;

import kz.chesschicken.backpacksfabric.item.EntityBackpack;
import kz.chesschicken.backpacksfabric.item.ItemBackpack;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import net.modificationstation.stationapi.api.util.Null;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class BackpacksListener {

    public static TemplateItemBase BACKPACK;

    public static int[] COLOURS;

    @Entrypoint.ModID
    public static ModID modID = Null.get();

    @SuppressWarnings("unused")
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        BACKPACK = new ItemBackpack(Identifier.of(modID, "backpack")).setTranslationKey(modID, "backpack");
    }

    @SuppressWarnings("unused")
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        COLOURS = new int[5];
        COLOURS[0] = Atlases.getStationGuiItems().addTexture(Identifier.of(modID, "backpack_brown")).index;
        COLOURS[1] = Atlases.getStationGuiItems().addTexture(Identifier.of(modID, "backpack_yellow")).index;
        COLOURS[2] = Atlases.getStationGuiItems().addTexture(Identifier.of(modID, "backpack_red")).index;
        COLOURS[3] = Atlases.getStationGuiItems().addTexture(Identifier.of(modID, "backpack_green")).index;
        COLOURS[4] = Atlases.getStationGuiItems().addTexture(Identifier.of(modID, "backpack_blue")).index;
    }

    @SuppressWarnings("unused")
    @EventListener
    public void registerGuiHandler(GuiHandlerRegistryEvent event) {
        event.registry.register(Identifier.of(modID, "backpacks_inventory"), BiTuple.of(ClientOpener::openBackpack, EntityBackpack::new));
    }

}
