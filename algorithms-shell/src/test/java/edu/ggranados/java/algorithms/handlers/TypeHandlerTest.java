package edu.ggranados.java.algorithms.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TypeHandlerStrategyTest {

    private TypeHandlerStrategy strategy;

    private InsertSortIterativeHandler insertSortIterative = mock(InsertSortIterativeHandler.class);
    private InsertSortRecursiveHandler insertSortRecursive = mock(InsertSortRecursiveHandler.class);

    @Test
    void testShouldRunDownlineInsertSort(){

        strategy = new TypeHandlerStrategy(insertSortIterative, insertSortRecursive);

        //when
        TypeHandler insertSortIterativeHandler = strategy.getInsertSortIterative();
        TypeHandler insertSortRecursiveHandler = strategy.getInsertSortRecursive();

        //then
        assertTrue(insertSortIterativeHandler instanceof InsertSortIterativeHandler);
        assertTrue(insertSortRecursiveHandler instanceof InsertSortRecursiveHandler);
    }

}