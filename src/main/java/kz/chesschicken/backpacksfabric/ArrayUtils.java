package kz.chesschicken.backpacksfabric;

import net.minecraft.item.ItemInstance;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.co.benjiweber.expressions.tuple.BiTuple;

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

    public static @Nullable String @NotNull [] abuseIdentifiers(@Nullable ItemInstance @NotNull [] backpackContent) {
        Map<BiTuple<Integer, Integer>, Integer> temp = new HashMap<>();
        Arrays.stream(backpackContent).filter(Objects::nonNull).forEach(instance -> {
            BiTuple<Integer, Integer> a = BiTuple.of(instance.itemId, instance.getDamage());
            if(temp.containsKey(a))
               temp.replace(a, temp.get(a) + instance.count);
            else
                temp.put(a, instance.count);
        });

        return transformFromMap(new String[temp.size()], temp, entry -> net.minecraft.client.resource.language.TranslationStorage.getInstance().method_995(new ItemInstance(entry.getKey().one(), 1, entry.getKey().two()).getTranslationKey()) + " x" + entry.getValue());
    }
}
