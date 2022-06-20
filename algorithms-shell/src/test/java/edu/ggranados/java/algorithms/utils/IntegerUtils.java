package edu.ggranados.java.algorithms.utils;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class IntegerUtils {
    public IntegerUtils() {
    }

    public static List<Integer> getIntegerList() {
        Random random = new Random();
        IntStream randStream = random.ints(5);
        return randStream.boxed().collect(Collectors.toList());
    }

    public static boolean isEqualOrGreaterThan(Integer next, Integer prev) {
        return (next >= prev);
    }
}