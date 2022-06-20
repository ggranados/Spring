package edu.ggranados.java.algorithms.sort;

import java.util.List;

@FunctionalInterface
public interface Sort <T>{

    List<T> sort(List<T> elements);

}
