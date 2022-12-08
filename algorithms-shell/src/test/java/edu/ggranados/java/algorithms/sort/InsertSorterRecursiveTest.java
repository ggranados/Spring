package edu.ggranados.java.algorithms.sort;

import org.junit.jupiter.api.Test;

import java.util.*;

import static edu.ggranados.java.algorithms.utils.IntegerUtils.getIntegerList;
import static edu.ggranados.java.algorithms.utils.IntegerUtils.isEqualOrGreaterThan;
import static org.junit.jupiter.api.Assertions.*;

class InsertSorterRecursiveTest {

    InsertSorterRecursive insertSort = new InsertSorterRecursive();
    ArrayList<Integer> elements = new ArrayList<>();

    @Test
    void testSortRecursive(){
        //Given
        elements.addAll(getIntegerList());
        System.out.println(elements);

        //When
        var sortedElements = insertSort.sort(elements);
        System.out.println(sortedElements);


        //Then
        var lastElement = sortedElements.stream()
                .reduce((a,b) -> {
                    assertTrue(isEqualOrGreaterThan(b, a),
                            b + " element is not equal or greater than previous " + a);
                    return b;
                });

        OptionalInt max = elements.stream()
                .mapToInt(v -> v).max();

        assertTrue(lastElement.isPresent(), "last element must be present");
        assertTrue(max.isPresent(), "max element must be present");

        assertEquals(max.getAsInt(),
                lastElement.get(),
                "Last element should be the max value");
    }


}