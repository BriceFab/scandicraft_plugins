package net.scandicraft.utils;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayUtils {
    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = java.util.Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    //[].filter(x -> x > 2, list);
    public static <T> List<T> filter(Predicate<T> criteria, List<T> list) {
        return list.stream().filter(criteria).collect(Collectors.toList());
    }
}
