package kz.chesschicken.backpacksfabric;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class ArrayUtils {
    public static @NotNull String[] combineTwoArrays(String[] a, String[] b) {
        String[] send = new String[a.length + b.length];
        System.arraycopy(a, 0, send, 0, a.length);
        System.arraycopy(b, 0, send, a.length, b.length);
        return send;
    }

    public static <T, A, B> @Nullable T @NotNull [] transformFromMap(@Nullable T @NotNull [] a, @NotNull Map<A, B> map, @NotNull Function<Map.@NotNull Entry<A, B>, @Nullable T> transformer) {
        int q = 0;
        for(Map.Entry<A, B> e : map.entrySet()) {
            a[q] = transformer.apply(e);
            q++;
        }
        return a;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static @Nullable Identifier parseId(int i) {
        final BlockRegistry blockData = (BlockRegistry) BlockRegistry.REGISTRIES.get(Identifier.of("stationapi:blocks")).get();
        if(blockData.get(i).isPresent())
            return blockData.getIdentifier(blockData.get(i).get());
        final ItemRegistry itemData = (ItemRegistry) ItemRegistry.REGISTRIES.get(Identifier.of("stationapi:items")).get();
        if(itemData.get(i).isPresent())
            return itemData.getIdentifier(itemData.get(i).get());
        return null;
    }

    public static @Nullable String @NotNull [] abuseIdentifiers(@Nullable ItemInstance @NotNull [] backpackContent) {
        Map<Identifier, Integer> temp = new HashMap<>();
        Arrays.stream(backpackContent).filter(Objects::nonNull).forEach(instance -> {
            Identifier aValue = parseId(instance.itemId);
            //if(aValue == null)
            //    return; //Impossible...?
            if (temp.containsKey(aValue))
                temp.replace(aValue, temp.get(aValue) + instance.count);
            else
                temp.put(aValue, instance.count);
        });
        return transformFromMap(new String[temp.size()], temp, entry -> entry.getKey() + " x" + entry.getValue());
    }
}
