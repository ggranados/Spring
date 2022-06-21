package edu.ggranados.java.algorithms.handlers;

import edu.ggranados.java.algorithms.sort.InsertSortRecursive;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static edu.ggranados.java.algorithms.utils.IntegerUtils.getIntegerList;

public class InsertSortRecursiveHandler implements TypeHandler {
    private static final Logger logger = LogManager.getLogger(InsertSortRecursiveHandler.class);

    @Override
    public void execute() {
        InsertSortRecursive insertSort = new InsertSortRecursive();
        logger.info("Executing Insert Sort Recursive Algorithm...");

        ArrayList<Integer> elements = new ArrayList<>(getIntegerList());
        logger.info(elements);

        var sortedElements = insertSort.sort(elements);
        logger.info(sortedElements);

    }
}
