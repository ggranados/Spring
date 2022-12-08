package edu.ggranados.java.algorithms.handlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TypeHandlerStrategy {

    private TypeHandler insertSortIterative;
    private TypeHandler insertSortRecursive;

    @Autowired
    public TypeHandlerStrategy(
            @Qualifier("insertSorterIterative") TypeHandler insertSortIterative,
            @Qualifier("insertSortRecursive") TypeHandler insertSortRecursive) {
        this.insertSortIterative = insertSortIterative;
        this.insertSortRecursive = insertSortRecursive;
    }

    public TypeHandler getInsertSortIterative(){ return insertSortIterative; }

    public TypeHandler getInsertSortRecursive(){ return insertSortRecursive; }
}
