package kz.chesschicken.backpacksfabric;

public class ArrayUtils {
    public static String[] combineTwoArrays(String[] a, String[] b) {
        String[] send = new String[a.length + b.length];
        System.arraycopy(a, 0, send, 0, a.length);
        System.arraycopy(b, 0, send, a.length, b.length);
        return send;
    }
}
