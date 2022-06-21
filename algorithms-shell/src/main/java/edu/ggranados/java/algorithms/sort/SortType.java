package edu.ggranados.java.algorithms.sort;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum SortType {
    INSERT,SELECTION,BUBBLE,INSERTION,MERGE,QUICK,HEAP,COUNTING,RADIX;

    public static String showOptions() {
        return Arrays.stream(SortMethod.values())
                .map(Enum::toString)
                .collect(Collectors.joining(",", "{", "}"));
    }
}

