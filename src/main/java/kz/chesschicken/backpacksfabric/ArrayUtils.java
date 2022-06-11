package kz.chesschicken.backpacksfabric;
import net.minecraft.item.ItemInstance;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ArrayUtils {
    public static @NotNull String[] combineTwoArrays(String[] a, String[] b) {
        String[] send = new String[a.length + b.length];
        System.arraycopy(a, 0, send, 0, a.length);
        System.arraycopy(b, 0, send, a.length, b.length);
        return send;
    }

    public static <T> @NotNull T[] buildArray(@NotNull T[] t, @NotNull Function<Integer, T> process) {
        for(int i = 0; i < t.length; i++)
            t[i] = process.apply(i);
        return t;
    }

    public static String[] collectItemInstances(ItemInstance[] backpackContent) {
        List<ItemInstance> tempHolderArrayList = new ArrayList<>(1);
        Arrays.stream(backpackContent).forEach(a -> {
            if(a == null)
                return;
            for(ItemInstance b : tempHolderArrayList) {
                if(b.itemId == a.itemId && b.getDamage() == a.getDamage()) {
                    b.count += a.count;
                    return;
                }
            }
            tempHolderArrayList.add(a);
        });
        return buildArray(new String[tempHolderArrayList.size()], i -> net.minecraft.client.resource.language.TranslationStorage.getInstance().method_995(tempHolderArrayList.get(i).getTranslationKey()) + " - x" + tempHolderArrayList.get(i).count);
    }
}
